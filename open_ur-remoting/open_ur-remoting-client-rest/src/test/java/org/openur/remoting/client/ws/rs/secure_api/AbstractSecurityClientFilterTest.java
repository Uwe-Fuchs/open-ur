package org.openur.remoting.client.ws.rs.secure_api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestFilter;
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
import org.openur.module.integration.security.shiro.testing.OpenUrRdbmsRealmMock;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.remoting.resource.userstructure.UserResource;
import org.openur.remoting.xchange.rest.providers.json.PersonProvider;

public abstract class AbstractSecurityClientFilterTest
	extends JerseyTest
{
	public static final String TEST_USER_NAME = OpenUrRdbmsRealmMock.USER_NAME_2;
	public static final String TEST_PASSWORD = OpenUrRdbmsRealmMock.PASSWORD_2;
	public static final String TEST_APPLICATION_NAME = "Demo-Application";

	protected Boolean hashCredentials;
	protected IUserServices userServicesMock;

	@Override
	protected Application configure()
	{
		hashCredentials = Boolean.TRUE;
		userServicesMock = Mockito.mock(IUserServices.class);

		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bind(hashCredentials).to(Boolean.class);
				bind(userServicesMock).to(IUserServices.class);		
			}
		};

		ResourceConfig config = new ResourceConfig(UserResource.class)
				.register(binder);

		return config;
	}

	protected Invocation.Builder buildInvocationTargetBuilder(ClientRequestFilter... testFilters)
	{
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(PersonProvider.class);
		
		for (ClientRequestFilter filter : testFilters)
		{
			clientConfig.register(filter);			
		}
		
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget webTarget = client
					.target("http://localhost:9998/")
					.path(UserResource.USER_RESOURCE_PATH)
					.path(UserResource.PERSON_PER_ID_RESOURCE_PATH)
					.path(TestObjectContainer.PERSON_UUID_1);

		return webTarget.request(MediaType.APPLICATION_JSON);
	}
}
