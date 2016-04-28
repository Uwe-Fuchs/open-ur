package org.openur.remoting.resource.errorhandling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.openur.remoting.resource.security.RdbmsRealmResource.AUTHENTICATE_RESOURCE_PATH;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;
import org.openur.module.util.exception.OpenURRuntimeException;
import org.openur.remoting.resource.AbstractResourceTest;
import org.openur.remoting.resource.client.AbstractResourceClient;
import org.openur.remoting.resource.security.RdbmsRealmResource;
import org.openur.remoting.xchange.rest.errorhandling.ErrorMessage;
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
			assertEquals(OpenUrRdbmsRealmMock.AUTH_ERROR_MSG, e.getMessage());
			assertEquals(AuthenticationException.class, e.getCause().getClass());	
		}
	}
	
	@Test
	public void testRuntimeException()
	{
		try
		{
			AuthenticationToken token = OpenUrRdbmsRealmMock.TOKEN_CAUSING_RUNTIME_EXCEPTION;
			getResourceClient().performRestCall(
						AUTHENTICATE_RESOURCE_PATH, HttpMethod.PUT, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, AuthenticationInfo.class, token);
			fail("expected WebApplicationException not thrown!");
		} catch (WebApplicationException e)
		{
			assertNotNull(e);
			assertEquals(500, e.getResponse().getStatus());
			assertEquals(OpenUrRdbmsRealmMock.RUNTIME_ERROR_MSG, e.getMessage());
			assertEquals(OpenURRuntimeException.class, e.getCause().getClass());	
		}
	}
	
	@Test
	public void testNullResponseException()
	{
		MyResourceClientMock resourceClient = new MyResourceClientMock(getBaseURI());
		resourceClient.addProvider(UsernamePwTokenProvider.class);
		resourceClient.addProvider(UsernamePwAuthenticationInfoProvider.class);
		resourceClient.addProvider(ErrorMessageProvider.class);
		
		// set response null explicitely:
		resourceClient.response = null;
		
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
	
	@Test
	public void testUnrecognizedError()
	{
		MyResourceClientMock resourceClient = new MyResourceClientMock(getBaseURI());
		resourceClient.addProvider(UsernamePwTokenProvider.class);
		resourceClient.addProvider(UsernamePwAuthenticationInfoProvider.class);
		resourceClient.addProvider(ErrorMessageProvider.class);
		
		resourceClient.response = Mockito.mock(Response.class);
		Mockito.when(resourceClient.response.readEntity(ErrorMessage.class)).thenThrow(new RuntimeException());
		Mockito.when(resourceClient.response.getStatus()).thenReturn(400);
		
		try
		{
			AuthenticationToken token = OpenUrRdbmsRealmMock.TOKEN_WITH_WRONG_PW;
			resourceClient.performRestCall(
						AUTHENTICATE_RESOURCE_PATH, HttpMethod.PUT, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, AuthenticationInfo.class, token);
			fail("expected WebApplicationException not thrown!");
		} catch (WebApplicationException e)
		{
			assertNotNull(e);
			assertEquals("Unrecognized error!", e.getMessage());
		}
	}

	@Override
	protected String getBaseURI()
	{
		return super.getBaseURI() + RdbmsRealmResource.RDBMS_REALM_RESOURCE_PATH;
	}

	private static class MyResourceClientMock
		extends AbstractResourceClient
	{
		private Response response;
		
		@Override
		public <E, R> R performRestCall(String url, String httpMethod, String acceptMediaType, String contentMediaType, Class<R> resultType, E object)
		{
			return super.performRestCall(url, httpMethod, acceptMediaType, contentMediaType, resultType, object);
		}
	
		@Override
		protected <E, R> R internalRestCall(String url, String httpMethod, String acceptMediaType, String contentMediaType, Class<R> resultClassType, GenericType<R> genericResultType, E object)
		{
			return handleResponse(null, null, response);
		}
	
		public MyResourceClientMock(String baseUrl)
		{
			super(baseUrl);
		}	
	}
}
