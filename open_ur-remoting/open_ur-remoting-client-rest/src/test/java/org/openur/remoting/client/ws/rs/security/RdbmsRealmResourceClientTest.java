package org.openur.remoting.client.ws.rs.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Application;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;
import org.openur.remoting.resource.errorhandling.AuthenticationExceptionMapper;
import org.openur.remoting.resource.security.RdbmsRealmResource;

public class RdbmsRealmResourceClientTest
	extends JerseyTest
{
	private RdbmsRealmResourceClient realmClient;
	private OpenUrRdbmsRealm realmMock;

	@Override
	protected Application configure()
	{
		// mocked service:
		realmMock = new OpenUrRdbmsRealmMock();
		
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
		
		// Client:
		realmClient = new RdbmsRealmResourceClient("http://localhost:9998/");	
		
		for (Class<?> provider : ((RdbmsRealmResourceClient) realmClient).getProviders())
		{
			config.register(provider);
		}
		
		config.register(AuthenticationExceptionMapper.class);
		
		return config;
	}

	@Test
	public void testGetName()
	{
		String result = realmClient.getName();
		System.out.println("Result: " + result);
		
		assertEquals(OpenUrRdbmsRealmMock.REALM_NAME, result);
	}

	@Test
	public void testSupports()
	{
		boolean b = realmClient.supports(OpenUrRdbmsRealmMock.USERNAME_PW_TOKEN);
		System.out.println("Result: " + b);
		assertTrue(b);
		
		b = realmClient.supports(OpenUrRdbmsRealmMock.TOKEN_WITH_WRONG_PW);
		System.out.println("Result: " + b);
		assertFalse(b);
	}

	@Test
	public void testGetAuthenticationInfo()
	{
		AuthenticationInfo info = realmClient.getAuthenticationInfo(OpenUrRdbmsRealmMock.USERNAME_PW_TOKEN);

		assertNotNull(info);
		System.out.println("Result: " + info);
		
		String passWord = new String((char[]) info.getCredentials());
		assertEquals(OpenUrRdbmsRealmMock.PASSWORD_1, passWord);
		
		String userName = info.getPrincipals().getPrimaryPrincipal().toString();
		assertEquals(OpenUrRdbmsRealmMock.USER_NAME_1, userName);
		
		byte[] saltBytes = ((SimpleAuthenticationInfo) info).getCredentialsSalt().getBytes();
		char[] chars = new char[saltBytes.length];
		for (int i = 0; i < saltBytes.length; i++)
		{
			chars[i] = (char) saltBytes[i];
		}
		assertEquals(OpenUrRdbmsRealmMock.SALT_BASE, new String(chars));
	}

	@Test(expected=AuthenticationException.class)
	public void testDoGetAuthenticationInfo_Wrong_PW()
	{
		realmClient.getAuthenticationInfo(OpenUrRdbmsRealmMock.TOKEN_WITH_WRONG_PW);
	}
}
