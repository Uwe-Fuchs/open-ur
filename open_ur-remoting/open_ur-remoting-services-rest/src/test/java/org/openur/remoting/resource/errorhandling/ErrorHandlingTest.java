package org.openur.remoting.resource.errorhandling;

import static org.junit.Assert.*;
import static org.openur.remoting.resource.security.RdbmsRealmResource.*;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Before;
import org.junit.Test;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;
import org.openur.module.integration.security.shiro.UsernamePwAuthenticationInfo;
import org.openur.module.util.exception.OpenURRuntimeException;
import org.openur.remoting.resource.AbstractResourceTest;
import org.openur.remoting.resource.client.AbstractResourceClient;
import org.openur.remoting.resource.security.RdbmsRealmResource;
import org.openur.remoting.xchange.rest.providers.json.ErrorMessageProvider;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwAuthenticationInfoProvider;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwTokenProvider;

public class ErrorHandlingTest
	extends AbstractResourceTest
{
	private OpenUrRdbmsRealm realmMock;
	
	@Override
	protected Application configure()
	{
		realmMock = new ErrorHandlingTestRealmMock(); 
			
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
				.register(GenericExceptionMapper.class)
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
	public void testAuthenticationException()
	{
		try
		{
			AuthenticationToken token = OpenUrRdbmsRealmMock.TOKEN_WITH_WRONG_PW;
			getResourceClient().performRestCall(
						AUTHENTICATE_RESOURCE_PATH, HttpMethod.PUT, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, AuthenticationInfo.class, token);
			fail("expected WebApplicationException not thrown!");
		} catch (WebApplicationException e)
		{
			assertNotNull(e);
			assertEquals(404, e.getResponse().getStatus());
			assertEquals(OpenUrRdbmsRealmMock.ERROR_MSG, e.getMessage());
			assertEquals(AuthenticationException.class, e.getCause().getClass());	
		}
	}
	
	@Test
	public void testRuntimeException()
	{
		try
		{
			AuthenticationToken token = OpenUrRdbmsRealmMock.TOKEN_WITH_UNKNOWN_USERNAME;
			getResourceClient().performRestCall(
						AUTHENTICATE_RESOURCE_PATH, HttpMethod.PUT, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, AuthenticationInfo.class, token);
			fail("expected WebApplicationException not thrown!");
		} catch (WebApplicationException e)
		{
			assertNotNull(e);
			assertEquals(500, e.getResponse().getStatus());
			assertEquals(ErrorHandlingTest.ErrorHandlingTestRealmMock.RUNTIME_ERROR_MSG, e.getMessage());
			assertEquals(OpenURRuntimeException.class, e.getCause().getClass());	
		}
	}
	
	@Test
	public void testNullResponseException()
	{
		ErrorHandlingTest.MyNullResponseResourceClientMock resourceClient = new ErrorHandlingTest.MyNullResponseResourceClientMock(getBaseURI());
		resourceClient.addProvider(UsernamePwTokenProvider.class);
		resourceClient.addProvider(UsernamePwAuthenticationInfoProvider.class);
		resourceClient.addProvider(ErrorMessageProvider.class);
		
		try
		{
			AuthenticationToken token = OpenUrRdbmsRealmMock.TOKEN_WITH_WRONG_PW;
			resourceClient.performRestCall(
						AUTHENTICATE_RESOURCE_PATH, HttpMethod.PUT, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, AuthenticationInfo.class, token);
			fail("expected WebApplicationException not thrown!");
		} catch (WebApplicationException e)
		{
			assertNotNull(e);
			assertEquals("Server-Response was null!", e.getMessage());
		}
	}

	@Override
	protected String getBaseURI()
	{
		return super.getBaseURI() + RdbmsRealmResource.RDBMS_REALM_RESOURCE_PATH;
	}
	
	private static class ErrorHandlingTestRealmMock
		extends OpenUrRdbmsRealm
	{
		public static final String RUNTIME_ERROR_MSG = "Common OpenURRuntimeException!";
		
		@Override
		protected UsernamePwAuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException
		{
			if (EqualsBuilder.reflectionEquals(OpenUrRdbmsRealmMock.TOKEN_WITH_WRONG_PW, token))
			{
				throw new AuthenticationException(OpenUrRdbmsRealmMock.ERROR_MSG);
			}
			
			if (EqualsBuilder.reflectionEquals(OpenUrRdbmsRealmMock.TOKEN_WITH_UNKNOWN_USERNAME, token))
			{
				throw new OpenURRuntimeException(RUNTIME_ERROR_MSG);
			}
	
			return null;
		}		
	}

	private static class MyNullResponseResourceClientMock
		extends AbstractResourceClient
	{
		@Override
		public <E, R> R performRestCall(String url, String httpMethod, String acceptMediaType, String contentMediaType, Class<R> resultType, E object)
		{
			return super.performRestCall(url, httpMethod, acceptMediaType, contentMediaType, resultType, object);
		}
	
		@Override
		protected <E, R> R internalRestCall(String url, String httpMethod, String acceptMediaType, String contentMediaType, Class<R> resultClassType, GenericType<R> genericResultType, E object)
		{
			return handleResponse(null, null, null);
		}
	
		public MyNullResponseResourceClientMock(String baseUrl)
		{
			super(baseUrl);
		}	
	}
}
