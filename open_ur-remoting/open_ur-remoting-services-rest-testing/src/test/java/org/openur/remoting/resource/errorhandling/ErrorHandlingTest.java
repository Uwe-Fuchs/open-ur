package org.openur.remoting.resource.errorhandling;

import static org.junit.Assert.*;
import static org.openur.remoting.resource.security.RdbmsRealmResource.*;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Before;
import org.junit.Test;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.module.util.exception.OpenURRuntimeException;
import org.openur.remoting.resource.AbstractResourceTest;
import org.openur.remoting.resource.errorhandling.MockErrorHandlingFactory.ErrorHandlingTestRealmMock;
import org.openur.remoting.resource.security.MockRdbmsRealmFactory.OpenUrRdbmsRealmMock;
import org.openur.remoting.resource.security.RdbmsRealmResource;
import org.openur.remoting.xchange.rest.providers.json.ErrorMessageProvider;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwAuthenticationInfoProvider;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwTokenProvider;

public class ErrorHandlingTest
	extends AbstractResourceTest
{
	@Override
	protected Application configure()
	{
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bindFactory(MockErrorHandlingFactory.class).to(OpenUrRdbmsRealm.class);
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
			assertEquals(ErrorHandlingTestRealmMock.RUNTIME_ERROR_MSG, e.getMessage());
			assertEquals(OpenURRuntimeException.class, e.getCause().getClass());	
		}
	}

	@Override
	protected String getBaseURI()
	{
		return super.getBaseURI() + RdbmsRealmResource.RDBMS_REALM_RESOURCE_PATH;
	}
}
