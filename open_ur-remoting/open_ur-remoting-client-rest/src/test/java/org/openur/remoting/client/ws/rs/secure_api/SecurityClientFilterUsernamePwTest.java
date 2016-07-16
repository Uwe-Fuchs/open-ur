package org.openur.remoting.client.ws.rs.secure_api;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.utils.compare.PersonComparer;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.module.util.exception.EntityNotFoundException;
import org.openur.remoting.resource.errorhandling.EntityNotFoundExceptionMapper;
import org.openur.remoting.resource.secure_api.SecureApiSettings;
import org.openur.remoting.resource.secure_api.SecurityFilter_UsernamePw;
import org.openur.remoting.resource.userstructure.UserResource;
import org.openur.remoting.xchange.rest.providers.json.PersonProvider;

public class SecurityClientFilterUsernamePwTest
	extends JerseyTest
{
	private String remoteAuthenticationPermissionName = "Hallo!!";
	protected String applicationName = "Demo-Application";
	private SecureApiSettings settings = SecureApiSettings.PLAIN_CREDENTIALS;
	
	private OpenUrRdbmsRealmMock realmMock;
	private IAuthorizationServices authorizationServicesMock;
	private IUserServices userServicesMock;
	
	@Override
	protected Application configure()
	{
		realmMock = new OpenUrRdbmsRealmMock();
		authorizationServicesMock = Mockito.mock(IAuthorizationServices.class);
		userServicesMock = Mockito.mock(IUserServices.class);
		
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bind(realmMock).to(OpenUrRdbmsRealm.class);
				bind(remoteAuthenticationPermissionName).to(String.class).named("remoteAuthenticationPermissionName");
				bind(settings).to(SecureApiSettings.class);
				bind(authorizationServicesMock).to(IAuthorizationServices.class);
				bind(userServicesMock).to(IUserServices.class);
			}
		};
		
		ResourceConfig config = new ResourceConfig(UserResource.class)
				.register(PersonProvider.class)
				.register(SecurityFilter_UsernamePw.class)
				.register(EntityNotFoundExceptionMapper.class)
				.register(binder);

		return config;
	}
	
	private Invocation.Builder buildInvocationTargetBuilder()
	{
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(PersonProvider.class);
		clientConfig.register(SecurityClientFilter_UsernamePw.class);
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget webTarget = client
				.target("http://localhost:9998/")
				.path(UserResource.USER_RESOURCE_PATH)
				.path(UserResource.PERSON_PER_ID_RESOURCE_PATH)
				.path(TestObjectContainer.PERSON_UUID_1);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		invocationBuilder.header(SecurityFilter_UsernamePw.APPLICATION_NAME_PROPERTY, applicationName);
		
		return invocationBuilder;
	}

	@Test
	public void testFilterValidCredentials()
		throws EntityNotFoundException
	{
		Mockito.when(authorizationServicesMock.hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, remoteAuthenticationPermissionName, applicationName))
				.thenReturn(Boolean.TRUE);
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);

		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		invocationBuilder.header(SecurityFilter_UsernamePw.AUTHENTICATION_PROPERTY, OpenUrRdbmsRealmMock.USER_NAME_2 + ":" + OpenUrRdbmsRealmMock.PASSWORD_2);
		Response response = invocationBuilder.get();
		assertEquals(200, response.getStatus());
		System.out.println(response.getStatus());

		Person p = response.readEntity(Person.class);
		assertNotNull(p);
		System.out.println(p);		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, p));

		assertEquals(1, realmMock.getAuthCounter());
		verify(authorizationServicesMock, times(1)).hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, remoteAuthenticationPermissionName, applicationName);
	}
}
