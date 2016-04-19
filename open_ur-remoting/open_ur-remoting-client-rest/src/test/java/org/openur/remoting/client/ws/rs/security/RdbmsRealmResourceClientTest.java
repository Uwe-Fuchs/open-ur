package org.openur.remoting.client.ws.rs.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Application;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.remoting.resource.errorhandling.AuthenticationExceptionMapper;
import org.openur.remoting.resource.security.MockRdbmsRealmFactory;
import org.openur.remoting.resource.security.OpenUrRdbmsRealmMock;
import org.openur.remoting.resource.security.RdbmsRealmResource;

public class RdbmsRealmResourceClientTest
	extends JerseyTest
{
	private static final UsernamePasswordToken TOKEN_WITH_WRONG_PW = new UsernamePasswordToken(TestObjectContainer.USER_NAME_1, "someWrongPw");
	
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
				.register(binder);
		
		// Client:
		realm = new RdbmsRealmResourceClient("http://localhost:9998/");	
		
		for (Class<?> provider : ((RdbmsRealmResourceClient) realm).getProviders())
		{
			config.register(provider);
		}
		
		config.register(AuthenticationExceptionMapper.class);
		
		return config;
	}

	@Test
	public void testGetName()
	{
		String result = realm.getName();
		System.out.println("Result: " + result);
		
		assertEquals(OpenUrRdbmsRealmMock.REALM_NAME, result);
	}

	@Test
	public void testSupports()
	{
		boolean b = realm.supports(OpenUrRdbmsRealmMock.USERNAME_PW_TOKEN);
		System.out.println("Result: " + b);
		assertTrue(b);
		
		b = realm.supports(TOKEN_WITH_WRONG_PW);
		System.out.println("Result: " + b);
		assertFalse(b);
	}

	@Test
	public void testGetAuthenticationInfo()
	{
		AuthenticationInfo info = realm.getAuthenticationInfo(OpenUrRdbmsRealmMock.USERNAME_PW_TOKEN);

		assertNotNull(info);
		System.out.println("Result: " + info);
		
		String passWord = new String((char[]) info.getCredentials());
		assertEquals(TestObjectContainer.PASSWORD_1, passWord);
		
		String userName = info.getPrincipals().getPrimaryPrincipal().toString();
		assertEquals(TestObjectContainer.USER_NAME_1, userName);
		
		byte[] saltBytes = ((SimpleAuthenticationInfo) info).getCredentialsSalt().getBytes();
		char[] chars = new char[saltBytes.length];
		for (int i = 0; i < saltBytes.length; i++)
		{
			chars[i] = (char) saltBytes[i];
		}
		assertEquals(TestObjectContainer.SALT_BASE, new String(chars));
	}

	@Test(expected=AuthenticationException.class)
	public void testDoGetAuthenticationInfo_Wrong_PW()
	{
		realm.getAuthenticationInfo(TOKEN_WITH_WRONG_PW);
	}
}
