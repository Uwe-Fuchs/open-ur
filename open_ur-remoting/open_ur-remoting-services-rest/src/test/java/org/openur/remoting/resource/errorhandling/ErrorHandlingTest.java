package org.openur.remoting.resource.errorhandling;

import static org.junit.Assert.*;
import static org.openur.remoting.resource.security.RdbmsRealmResource.*;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;
import org.openur.module.util.exception.OpenURRuntimeException;
import org.openur.remoting.resource.client.AbstractResourceClient;
import org.openur.remoting.resource.security.RdbmsRealmResource;
import org.openur.remoting.xchange.rest.errorhandling.ErrorMessage;
import org.openur.remoting.xchange.rest.providers.json.ErrorMessageProvider;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwTokenProvider;

public class ErrorHandlingTest
	extends JerseyTest
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
	}
	
	@Test
	public void testServerSideNotFoundException()
	{
		MyResourceClient resourceClient = new MyResourceClient(getBaseURI());
		resourceClient.addProvider(ErrorMessageProvider.class);
		
		try
		{
			resourceClient.performRestCall_GET("someUnKnownResource", MediaType.APPLICATION_JSON, Person.class);
			fail("expected WebApplicationException not thrown!");
		} catch (WebApplicationException e)
		{
			assertNotNull(e);
			assertEquals(404, e.getResponse().getStatus());
			assertEquals("HTTP 404 " + Status.fromStatusCode(404).getReasonPhrase(), e.getMessage());
			assertTrue(WebApplicationException.class.isAssignableFrom(e.getClass()));	
		}
	}
	
	@Test
	public void testClientSideNotFoundException()
	{
		MyResourceClient resourceClient = new MyResourceClient("http://localhost:12345/");
		resourceClient.addProvider(ErrorMessageProvider.class);
		
		try
		{
			resourceClient.performRestCall_GET("someUnKnownPath", MediaType.APPLICATION_JSON, Person.class);
			fail("expected Exception not thrown!");
		} catch (Exception e)
		{
			assertNotNull(e);
			assertTrue(ProcessingException.class.isAssignableFrom(e.getClass()));	
		}
	}
	
	@Test
	public void testRuntimeException()
	{
		MyResourceClient resourceClient = new MyResourceClient(getBaseURI() + RdbmsRealmResource.RDBMS_REALM_RESOURCE_PATH);
		resourceClient.addProvider(UsernamePwTokenProvider.class);
		resourceClient.addProvider(ErrorMessageProvider.class);
		
		try
		{
			AuthenticationToken token = OpenUrRdbmsRealmMock.TOKEN_CAUSING_RUNTIME_EXCEPTION;
			resourceClient.performRestCall(
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
		MyResourceClientMock resourceClient = new MyResourceClientMock(getBaseURI() + RdbmsRealmResource.RDBMS_REALM_RESOURCE_PATH);
		resourceClient.addProvider(ErrorMessageProvider.class);
		
		// set response null explicitely:
		resourceClient.response = null;
		
		try
		{
			resourceClient.performRestCall_GET(GET_NAME_RESOURCE_PATH, MediaType.TEXT_PLAIN, String.class);
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
		MyResourceClientMock resourceClient = new MyResourceClientMock(getBaseURI() + RdbmsRealmResource.RDBMS_REALM_RESOURCE_PATH);
		resourceClient.addProvider(ErrorMessageProvider.class);

		Mockito.when(resourceClient.response.readEntity(ErrorMessage.class)).thenThrow(new RuntimeException());
		Mockito.when(resourceClient.response.getStatus()).thenReturn(400);
		
		try
		{
			resourceClient.performRestCall_GET(GET_NAME_RESOURCE_PATH, MediaType.TEXT_PLAIN, String.class);
			fail("expected WebApplicationException not thrown!");
		} catch (WebApplicationException e)
		{
			assertNotNull(e);
			assertEquals(Status.fromStatusCode(400).getReasonPhrase(), e.getMessage());
		}
	}

	protected String getBaseURI()
	{
		return "http://localhost:9998/";
	}

	private static class MyResourceClient
		extends AbstractResourceClient
	{
		public MyResourceClient(String baseUrl)
		{
			super(baseUrl);
		}
		
		@Override
		protected <E, R> R performRestCall(String url, String httpMethod, String acceptMediaType, String contentMediaType, Class<R> resultType, E object)
		{
			return super.performRestCall(url, httpMethod, acceptMediaType, contentMediaType, resultType, object);
		}

		@Override
		protected <T> T performRestCall_GET(String url, String acceptMediaType, Class<T> resultType)
		{
			return super.performRestCall_GET(url, acceptMediaType, resultType);
		}	
	}

	private static class MyResourceClientMock
		extends AbstractResourceClient
	{
		private Response response = Mockito.mock(Response.class);
		
		@Override
		protected <E, R> R performRestCall(String url, String httpMethod, String acceptMediaType, String contentMediaType, Class<R> resultType, E object)
		{
			return super.performRestCall(url, httpMethod, acceptMediaType, contentMediaType, resultType, object);
		}
	
		@Override
		protected <T> T performRestCall_GET(String url, String acceptMediaType, Class<T> resultType)
		{
			return super.performRestCall_GET(url, acceptMediaType, resultType);
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
