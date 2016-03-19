package org.openur.remoting.client.ws.rs.security;

import static org.junit.Assert.*;

import javax.ws.rs.core.Application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.remoting.resource.security.MockRdbmsRealmFactory;
import org.openur.remoting.resource.security.RdbmsRealmResource;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwAuthenticationInfoProvider;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwTokenProvider;

public class RdbmsRealmResourceClientTest
	extends JerseyTest
{
	private RdbmsRealmResourceClient realm;

	@Override
	protected Application configure()
	{
		// Http-Testserver:
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bindFactory(MockRdbmsRealmFactory.class).to(OpenUrRdbmsRealm.class);
			}
		};
		
		ResourceConfig config = new ResourceConfig(RdbmsRealmResource.class)
				.register(UsernamePwTokenProvider.class)
				.register(UsernamePwAuthenticationInfoProvider.class)
				.register(binder);
		
		// Client:
		realm = new RdbmsRealmResourceClient("http://localhost:9998/");	
		
		for (Class<?> provider : ((RdbmsRealmResourceClient) realm).getProviders())
		{
			config.register(provider);
		}
		
		return config;
	}

	@Test
	public void testGetName()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testSupports()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetAuthenticationInfo()
	{
		fail("Not yet implemented");
	}
}
