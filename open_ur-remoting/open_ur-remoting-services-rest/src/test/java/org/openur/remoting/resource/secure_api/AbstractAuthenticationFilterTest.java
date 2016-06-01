package org.openur.remoting.resource.secure_api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.remoting.resource.userstructure.UserResource;
import org.openur.remoting.xchange.rest.providers.json.PersonProvider;

public class AbstractAuthenticationFilterTest
	extends JerseyTest
{
	protected String remoteAuthenticationPermissionName = "Hallo!!";
	protected SecureApiSettings settings;
	
	protected OpenUrRdbmsRealmMock realmMock;
	protected IAuthorizationServices authorizationServicesMock;
	protected IUserServices userServicesMock;
	
	protected String applicationName = "Demo-Application";
	
	@Override
	protected Application configure()
	{
		realmMock = new OpenUrRdbmsRealmMock();
		authorizationServicesMock = Mockito.mock(IAuthorizationServices.class);
		userServicesMock = Mockito.mock(IUserServices.class);
		settings = settings == null ? SecureApiSettings.NO_SECURITY : settings;
		
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
				.register(AuthenticationFilter.class)
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
		invocationBuilder.header(AuthenticationFilter.APPLICATION_NAME_PROPERTY, applicationName);
		
		return invocationBuilder;
	}
}
