package org.openur.remoting.client.ws.rs.secure_api;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.openur.module.domain.security.secure_api.PermissionConstraints.REMOTE_READ;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.realm.Realm;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.utils.compare.PersonComparer;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.module.util.exception.EntityNotFoundException;
import org.openur.remoting.resource.errorhandling.EntityNotFoundExceptionMapper;
import org.openur.remoting.resource.secure_api.AuthenticationFilter_BasicAuth;
import org.openur.remoting.resource.secure_api.AuthorizationFilter;
import org.openur.remoting.resource.userstructure.UserResource;
import org.openur.remoting.xchange.rest.providers.json.PersonProvider;

public class SecurityClientFilterBasicAuthTest
	extends JerseyTest
{
	private String applicationName = "Demo-Application";
	private Boolean hashCredentials = Boolean.TRUE;

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
				bind(realmMock).to(Realm.class);
				bind(hashCredentials).to(Boolean.class);
				bind(authorizationServicesMock).to(IAuthorizationServices.class);
				bind(userServicesMock).to(IUserServices.class);
			}
		};

		ResourceConfig config = new ResourceConfig(UserResource.class)
				.register(PersonProvider.class)
				.register(AuthenticationFilter_BasicAuth.class)
				.register(AuthorizationFilter.class)
				.register(EntityNotFoundExceptionMapper.class)
				.register(binder);

		return config;
	}

	protected Invocation.Builder buildInvocationTargetBuilder(String user, String password)
	{
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(PersonProvider.class);
		clientConfig.register(new SecurityClientFilter_BasicAuth(user, password, applicationName));
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget webTarget = client
					.target("http://localhost:9998/")
					.path(UserResource.USER_RESOURCE_PATH)
					.path(UserResource.PERSON_PER_ID_RESOURCE_PATH)
					.path(TestObjectContainer.PERSON_UUID_1);

		return webTarget.request(MediaType.APPLICATION_JSON);
	}

	@Test
	public void testFilterValidCredentials()
		throws EntityNotFoundException
	{
		Mockito.when(authorizationServicesMock.hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, REMOTE_READ, applicationName)).thenReturn(Boolean.TRUE);
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);

		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder(OpenUrRdbmsRealmMock.USER_NAME_2, OpenUrRdbmsRealmMock.PASSWORD_2);
		Response response = invocationBuilder.get();
		assertEquals(200, response.getStatus());
		assertEquals(1, realmMock.getAuthCounter());
		verify(authorizationServicesMock, times(1)).hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, REMOTE_READ, applicationName);
		verify(userServicesMock, times(1)).findPersonById(TestObjectContainer.PERSON_UUID_1);

		Person p = response.readEntity(Person.class);
		assertNotNull(p);
		System.out.println("Result: " + p);		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, (Person) p));
	}

	@Test
	public void testFilterInvalidCredentials()
		throws EntityNotFoundException
	{
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder(OpenUrRdbmsRealmMock.USER_NAME_2, "someWrongPassword");
		Response response = invocationBuilder.get();
		assertEquals(401, response.getStatus());
		assertEquals(1, realmMock.getAuthCounter());
		verify(authorizationServicesMock, times(0)).hasPermissionTechUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());		
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFilterEmptyUserName()
	{
		new SecurityClientFilter_BasicAuth("", "");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFilterEmptyPassword()
	{
		new SecurityClientFilter_BasicAuth("someUsername", "");
	}

	@Test(expected=NullPointerException.class)
	public void testFilterEmptyApplicationName()
	{
		new SecurityClientFilter_BasicAuth("someUsername", "somePassword", null);
	}
}
