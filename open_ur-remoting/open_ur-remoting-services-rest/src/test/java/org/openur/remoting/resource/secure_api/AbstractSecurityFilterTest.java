package org.openur.remoting.resource.secure_api;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;

import org.apache.shiro.realm.Realm;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.BeforeClass;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.remoting.resource.errorhandling.EntityNotFoundExceptionMapper;
import org.openur.remoting.resource.userstructure.UserResource;
import org.openur.remoting.xchange.rest.providers.json.PersonProvider;

public class AbstractSecurityFilterTest
	extends JerseyTest
{
	protected static DummyPreparePreAuthFilter dummyPreparePreAuthFilter;
	
	protected String applicationName = "Demo-Application";
	protected Boolean hashCredentials;
	
	protected OpenUrRdbmsRealmMock realmMock;
	protected IAuthorizationServices authorizationServicesMock;
	protected IUserServices userServicesMock;
	
	@BeforeClass
	public static void init()
	{
		dummyPreparePreAuthFilter = new DummyPreparePreAuthFilter(TestObjectContainer.TECH_USER_UUID_2);
	}
	
	@Override
	protected Application configure()
	{
		realmMock = new OpenUrRdbmsRealmMock();
		authorizationServicesMock = Mockito.mock(IAuthorizationServices.class);
		userServicesMock = Mockito.mock(IUserServices.class);
		hashCredentials = (hashCredentials == null ? Boolean.TRUE : hashCredentials);
		
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
				.register(EntityNotFoundExceptionMapper.class)
				.register(binder);

		return config;
	}
	
	protected Invocation.Builder buildInvocationTargetBuilder()
	{
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(PersonProvider.class);
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget webTarget = client
				.target("http://localhost:9998/")
				.path(UserResource.USER_RESOURCE_PATH)
				.path(UserResource.PERSON_PER_ID_RESOURCE_PATH)
				.path(TestObjectContainer.PERSON_UUID_1);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		invocationBuilder.header(AbstractSecurityFilterBase.APPLICATION_NAME_PROPERTY, applicationName);
		
		return invocationBuilder;
	}
	
	protected String buildUnhashedAuthString()
	{
		return AuthenticationFilter_BasicAuth.AUTHENTICATION_SCHEME + " " + OpenUrRdbmsRealmMock.USER_NAME_2 + ":" + OpenUrRdbmsRealmMock.PASSWORD_2;
	}
	
	protected String buildHashedAuthString()
		throws UnsupportedEncodingException
	{
		return AuthenticationFilter_BasicAuth.AUTHENTICATION_SCHEME + " " + DatatypeConverter.printBase64Binary((OpenUrRdbmsRealmMock.USER_NAME_2 + ":" + OpenUrRdbmsRealmMock.PASSWORD_2).getBytes("UTF-8"));
	}
}
