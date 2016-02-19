package org.openur.remoting.resource.security;

import static org.junit.Assert.*;
import static org.openur.remoting.resource.security.RdbmsRealmResource.*;

import java.net.URI;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.Realm;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Before;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.remoting.resource.AbstractResourceTest;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwTokenProvider;

public class RdbmsRealmResourceTest
	extends AbstractResourceTest
{
	private WebTarget service;
	
	@Override
	protected Application configure()
	{
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bindFactory(MockRdbmsRealmFactory.class).to(Realm.class);
			}
		};

		ResourceConfig config = new ResourceConfig(RdbmsRealmResource.class).register(UsernamePwTokenProvider.class).register(binder);

		return config;
	}

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();

		getMyClient().register(UsernamePwTokenProvider.class);
		service = getMyClient().target(getBaseURI());		
	}

	@Test
	public void testGetName()
	{
		String result = service
					.path(GET_NAME_RESOURCE_PATH)
					.request()
					.get(String.class);

		assertEquals(MockRdbmsRealmFactory.REALM_NAME, result);
	}

	@Test
	public void testSupports()
	{
		AuthenticationToken token = TestObjectContainer.USERNAME_PW_TOKEN.getDelegate();

		Boolean b = service
					.path(SUPPORTS_RESOURCE_PATH)
					.request()
					.put(Entity.entity(token, MediaType.APPLICATION_JSON), Boolean.class);

		assertTrue(b);
	}

	@Test
	public void testGetAuthenticationInfo()
	{

		assertTrue(true);
	}

	@Override
	protected URI getBaseURI()
	{
		String baseUri = super.getBaseURI().toString();
		
		return UriBuilder.fromUri(baseUri + RdbmsRealmResource.RDBMS_REALM_RESOURCE_PATH).build();
	}
}
