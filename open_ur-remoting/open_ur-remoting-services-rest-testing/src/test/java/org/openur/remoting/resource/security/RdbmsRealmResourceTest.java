package org.openur.remoting.resource.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.openur.remoting.resource.security.RdbmsRealmResource.AUTHENTICATE_RESOURCE_PATH;
import static org.openur.remoting.resource.security.RdbmsRealmResource.GET_NAME_RESOURCE_PATH;
import static org.openur.remoting.resource.security.RdbmsRealmResource.SUPPORTS_RESOURCE_PATH;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Before;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.remoting.resource.AbstractResourceTest;
import org.openur.remoting.xchange.rest.providers.json.SimpleAuthenticationInfoProvider;
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

		ResourceConfig config = new ResourceConfig(RdbmsRealmResource.class)
					.register(UsernamePwTokenProvider.class)
					.register(SimpleAuthenticationInfoProvider.class)
					.register(binder);

		return config;
	}

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();

		getMyClient().register(UsernamePwTokenProvider.class);
		getMyClient().register(SimpleAuthenticationInfoProvider.class);
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
		
		b = service
					.path(SUPPORTS_RESOURCE_PATH)
					.request()
					.put(Entity.entity(MockRdbmsRealmFactory.TOKEN_WITH_WRONG_PW, MediaType.APPLICATION_JSON), Boolean.class);
		
		assertFalse(b);
		
		b = service
					.path(SUPPORTS_RESOURCE_PATH)
					.request()
					.put(Entity.entity(MockRdbmsRealmFactory.TOKEN_WITH_UNKNOWN_USERNAME, MediaType.APPLICATION_JSON), Boolean.class);
		
		assertFalse(b);
	}

	@Test
	public void testGetAuthenticationInfo()
	{
		AuthenticationToken token = TestObjectContainer.USERNAME_PW_TOKEN.getDelegate();

		AuthenticationInfo info = service
					.path(AUTHENTICATE_RESOURCE_PATH)
					.request()
					.accept(MediaType.APPLICATION_JSON_TYPE)
					.put(Entity.entity(token, MediaType.APPLICATION_JSON_TYPE), AuthenticationInfo.class);

		assertNotNull(info);
		String passWord = new String((char[]) info.getCredentials());
		assertEquals(TestObjectContainer.PASSWORD_1, passWord);
		
		PrincipalCollection principalCollection = info.getPrincipals();
		Collection<?> coll = principalCollection.fromRealm(MockRdbmsRealmFactory.REALM_NAME);
		assertEquals(1, coll.size());
		List<?> principals = principalCollection.asList();
		assertEquals(1, principals.size());
		assertTrue(principals.contains(TestObjectContainer.USER_NAME_1));
		String userName = principalCollection.getPrimaryPrincipal().toString();
		assertEquals(TestObjectContainer.USER_NAME_1, userName);
		
		byte[] saltBytes = ((SimpleAuthenticationInfo) info).getCredentialsSalt().getBytes();
		char[] chars = new char[saltBytes.length];
		for (int i = 0; i < saltBytes.length; i++)
		{
			chars[i] = (char) saltBytes[i];
		}
		assertEquals(MockRdbmsRealmFactory.SALT_VALUE, new String(chars));
	}

//	@Test(expected=AuthenticationException.class)
//	public void testDoGetAuthenticationInfo_Wrong_PW()
//	{
//		service
//				.path(AUTHENTICATE_RESOURCE_PATH)
//				.request()
//				.accept(MediaType.APPLICATION_JSON_TYPE)
//				.put(Entity.entity(MockRdbmsRealmFactory.TOKEN_WITH_WRONG_PW, MediaType.APPLICATION_JSON_TYPE), AuthenticationInfo.class);
//	}

	@Override
	protected URI getBaseURI()
	{
		String baseUri = super.getBaseURI().toString();
		
		return UriBuilder.fromUri(baseUri + RdbmsRealmResource.RDBMS_REALM_RESOURCE_PATH).build();
	}
}
