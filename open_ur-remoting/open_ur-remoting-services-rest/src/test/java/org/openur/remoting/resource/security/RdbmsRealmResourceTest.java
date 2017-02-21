package org.openur.remoting.resource.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.openur.remoting.resource.security.RdbmsRealmResource.AUTHENTICATE_RESOURCE_PATH;
import static org.openur.remoting.resource.security.RdbmsRealmResource.GET_NAME_RESOURCE_PATH;
import static org.openur.remoting.resource.security.RdbmsRealmResource.SUPPORTS_RESOURCE_PATH;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Before;
import org.junit.Test;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.module.integration.security.shiro.testing.OpenUrRdbmsRealmMock;
import org.openur.remoting.resource.AbstractResourceTest;
import org.openur.remoting.resource.errorhandling.AuthenticationExceptionMapper;
import org.openur.remoting.xchange.rest.providers.json.ErrorMessageProvider;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwAuthenticationInfoProvider;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwTokenProvider;

public class RdbmsRealmResourceTest
	extends AbstractResourceTest
{
	private OpenUrRdbmsRealm realmMock;
	
	@Override
	protected Application configure()
	{
		realmMock = new OpenUrRdbmsRealmMock();
		
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bind(realmMock).to(OpenUrRdbmsRealm.class);
			}
		};

		ResourceConfig config = new ResourceConfig(RdbmsRealmResource.class)
					.register(UsernamePwTokenProvider.class)
					.register(UsernamePwAuthenticationInfoProvider.class)
					.register(AuthenticationExceptionMapper.class)
					.register(ErrorMessageProvider.class)
					.register(binder);

		return config;
	}

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();

		getResourceClient().addProvider(UsernamePwTokenProvider.class);
		getResourceClient().addProvider(UsernamePwAuthenticationInfoProvider.class);
		getResourceClient().addProvider(ErrorMessageProvider.class);
	}

	@Test
	public void testGetName()
	{
		String result = getResourceClient().performRestCall_GET(GET_NAME_RESOURCE_PATH, MediaType.TEXT_PLAIN, String.class);
		
		assertEquals(OpenUrRdbmsRealmMock.REALM_NAME, result);
	}

	@Test
	public void testSupports()
	{
		Boolean b = getResourceClient().performRestCall(
				SUPPORTS_RESOURCE_PATH, HttpMethod.PUT, MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, Boolean.class, 
				OpenUrRdbmsRealmMock.USERNAME_PW_TOKEN);
		assertTrue(b);

		b = getResourceClient().performRestCall(
				SUPPORTS_RESOURCE_PATH, HttpMethod.PUT, MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, Boolean.class, 
				OpenUrRdbmsRealmMock.TOKEN_WITH_WRONG_PW);
		assertFalse(b);

		b = getResourceClient().performRestCall(
				SUPPORTS_RESOURCE_PATH, HttpMethod.PUT, MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, Boolean.class, 
				OpenUrRdbmsRealmMock.TOKEN_WITH_UNKNOWN_USERNAME);
		assertFalse(b);
	}

	@Test
	public void testSupports_OwnService()
	{
		Client client = ClientBuilder.newClient();
		client.register(UsernamePwTokenProvider.class);
		WebTarget service = client.target(getBaseURI());

		Boolean b = service
					.path(SUPPORTS_RESOURCE_PATH)
					.request()
					.put(Entity.entity(OpenUrRdbmsRealmMock.USERNAME_PW_TOKEN, MediaType.APPLICATION_JSON), Boolean.class);
		
		assertTrue(b);
	}

	@Test
	public void testGetAuthenticationInfo()
	{
		AuthenticationToken token = OpenUrRdbmsRealmMock.USERNAME_PW_TOKEN;

		AuthenticationInfo info = getResourceClient().performRestCall(
					AUTHENTICATE_RESOURCE_PATH, HttpMethod.PUT, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, AuthenticationInfo.class, token);

		assertNotNull(info);
		String passWord = new String((char[]) info.getCredentials());
		assertEquals(OpenUrRdbmsRealmMock.PASSWORD_1, passWord);
		
		PrincipalCollection principalCollection = info.getPrincipals();
		Collection<?> coll = principalCollection.fromRealm(OpenUrRdbmsRealmMock.REALM_NAME);
		assertEquals(1, coll.size());
		List<?> principals = principalCollection.asList();
		assertEquals(1, principals.size());
		assertTrue(principals.contains(OpenUrRdbmsRealmMock.USER_NAME_1));
		String userName = principalCollection.getPrimaryPrincipal().toString();
		assertEquals(OpenUrRdbmsRealmMock.USER_NAME_1, userName);
		
		byte[] saltBytes = ((SimpleAuthenticationInfo) info).getCredentialsSalt().getBytes();
		char[] chars = new char[saltBytes.length];
		for (int i = 0; i < saltBytes.length; i++)
		{
			chars[i] = (char) saltBytes[i];
		}
		assertEquals(OpenUrRdbmsRealmMock.SALT_BASE, new String(chars));
	}

	@Test
	public void testGetAuthenticationInfo_Response()
	{		
		AuthenticationToken token = OpenUrRdbmsRealmMock.USERNAME_PW_TOKEN;
		Response response = getResourceClient().performRestCall(
						AUTHENTICATE_RESOURCE_PATH, HttpMethod.PUT, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, Response.class, token);
		
		assertNotNull(response);
		AuthenticationInfo info = response.readEntity(AuthenticationInfo.class);
		assertNotNull(info);		
	}

	@Test
	public void testDoGetAuthenticationInfo_Wrong_PW()
	{
		try
		{
			getResourceClient().performRestCall(
						AUTHENTICATE_RESOURCE_PATH, HttpMethod.PUT, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, Response.class, 
						OpenUrRdbmsRealmMock.TOKEN_WITH_WRONG_PW);	
			
			fail("expected exception not thrown!");
		} catch (WebApplicationException e)
		{
			assertEquals(Response.Status.FORBIDDEN.getStatusCode(), e.getResponse().getStatus());
			assertEquals(OpenUrRdbmsRealmMock.AUTH_ERROR_MSG, e.getMessage());
			assertEquals(AuthenticationException.class, e.getCause().getClass());
		}
	}

	@Override
	protected String getBaseURI()
	{
		return super.getBaseURI() + RdbmsRealmResource.RDBMS_REALM_RESOURCE_PATH;
	}
}
