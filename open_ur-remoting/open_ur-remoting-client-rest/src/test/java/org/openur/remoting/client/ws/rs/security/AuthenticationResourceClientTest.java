package org.openur.remoting.client.ws.rs.security;

import javax.ws.rs.core.Application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.module.integration.security.shiro.testing.OpenUrRdbmsRealmMock;
import org.openur.remoting.resource.errorhandling.AuthenticationExceptionMapper;
import org.openur.remoting.resource.security.RdbmsRealmResource;

public class AuthenticationResourceClientTest
	extends JerseyTest
{
	private AuthenticationResourceClient authenticationResourceClient;
	
	@Override
	protected Application configure()
	{
		String baseUrl = "http://localhost:9998/";
		
		// mocked service:
		OpenUrRdbmsRealm realmMock = new OpenUrRdbmsRealmMock();
		
		// Http-Testserver:
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bind(realmMock).to(OpenUrRdbmsRealm.class);
			}
		};
		
		ResourceConfig config = new ResourceConfig(RdbmsRealmResource.class)
				.register(binder);
		
		// this is only a dummy-client for getting the providers which are used to initialize the (server-side) RdbmsRealmResource properly:
		RdbmsRealmResourceClient realmClient = new RdbmsRealmResourceClient(baseUrl);	
		
		for (Class<?> provider : ((RdbmsRealmResourceClient) realmClient).getProviders())
		{
			config.register(provider);
		}
		
		config.register(AuthenticationExceptionMapper.class);
		
		// Client:
		authenticationResourceClient = new AuthenticationResourceClient(baseUrl);
		
		return config;
	}
	
	@Test
	public void testAuthenticate()
	{
		authenticationResourceClient.authenticate(OpenUrRdbmsRealmMock.USER_NAME_1, OpenUrRdbmsRealmMock.PASSWORD_1);
	}
	
	@Test(expected=org.openur.module.util.exception.AuthenticationException.class)
	public void testAuthenticate_Failure()
	{
		authenticationResourceClient.authenticate(OpenUrRdbmsRealmMock.USER_NAME_1, "someWrongPw");
	}
}
