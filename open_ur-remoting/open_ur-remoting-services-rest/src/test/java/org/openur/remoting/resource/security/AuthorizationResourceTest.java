package org.openur.remoting.resource.security;

import static org.junit.Assert.*;
import static org.openur.remoting.resource.security.AuthorizationResource.*;

import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.module.util.exception.EntityNotFoundException;
import org.openur.remoting.resource.AbstractResourceTest;
import org.openur.remoting.resource.errorhandling.EntityNotFoundExceptionMapper;
import org.openur.remoting.xchange.rest.providers.json.ErrorMessageProvider;

public class AuthorizationResourceTest
	extends AbstractResourceTest
{
	private static final String APP_NAME = "appName";
	private static final String PERMISSION_TEXT = "permissionText";
	private static final String OTHER_PERMISSION_TEXT = "otherPermissionText";
	private static final String PERSON_ID = UUID.randomUUID().toString();
	private static final String OU_ID = UUID.randomUUID().toString();
	private static final String TECH_USER_ID = UUID.randomUUID().toString();
	
	private IAuthorizationServices authorizationServicesMock;
	
	@Override
	protected Application configure()
	{
		authorizationServicesMock = Mockito.mock(IAuthorizationServices.class);
		
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bind(authorizationServicesMock).to(IAuthorizationServices.class);
			}
		};
		
		ResourceConfig config = new ResourceConfig(AuthorizationResource.class)
			.register(EntityNotFoundExceptionMapper.class)
			.register(ErrorMessageProvider.class)
			.register(binder);

		return config;
	}

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
		
		getResourceClient().addProvider(ErrorMessageProvider.class);		
	}
	
	@Test
	public void testHasPermissionInOrgUnit()
		throws EntityNotFoundException
	{
		Mockito.when(authorizationServicesMock.hasPermission(PERSON_ID, OU_ID, PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.TRUE);		
		
		Boolean b = getResourceClient().performRestCall_GET(HAS_OU_PERMISSION_RESOURCE_PATH + "?personId=" + PERSON_ID 
				+ "&ouId=" + OU_ID + "&text=" + PERMISSION_TEXT	+ "&appName=" + APP_NAME, MediaType.TEXT_PLAIN, Boolean.class);
		
		assertTrue(b);
	}
	
	@Test
	public void testHasNoPermissionInOrgUnit()
		throws EntityNotFoundException
	{		
		Mockito.when(authorizationServicesMock.hasPermission(PERSON_ID, OU_ID, OTHER_PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.FALSE);
		
		Boolean b = getResourceClient().performRestCall_GET(HAS_OU_PERMISSION_RESOURCE_PATH + "?personId=" + PERSON_ID 
				+ "&ouId=" + OU_ID + "&text=" + PERMISSION_TEXT	+ "&appName=" + APP_NAME, MediaType.TEXT_PLAIN, Boolean.class);
		
		assertFalse(b);
	}

	@Test
	public void testHasPermissionInOrgUnit_ThrowException()
		throws EntityNotFoundException
	{
		final String errorMsg = String.format("No org-unit found for orgUnitId '%s'!", OU_ID);
		Mockito.when(authorizationServicesMock.hasPermission(PERSON_ID, OU_ID, PERMISSION_TEXT, APP_NAME)).thenThrow(new EntityNotFoundException(errorMsg));
		
		try
		{
			getResourceClient().performRestCall_GET(HAS_OU_PERMISSION_RESOURCE_PATH + "?personId=" + PERSON_ID 
					+ "&ouId=" + OU_ID + "&text=" + PERMISSION_TEXT	+ "&appName=" + APP_NAME, MediaType.TEXT_PLAIN, Boolean.class);	
			
			fail("expected exception not thrown!");
		} catch (WebApplicationException e)
		{
			assertEquals(404, e.getResponse().getStatus());
			assertEquals(errorMsg, e.getMessage());
		}
	}

	@Test
	public void testHasPermissionInSystem()
		throws EntityNotFoundException
	{
		Mockito.when(authorizationServicesMock.hasPermission(PERSON_ID, PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.TRUE);		
		
		Boolean b = getResourceClient().performRestCall_GET(HAS_SYSTEM_PERMISSION_RESOURCE_PATH + "?personId=" + PERSON_ID 
			+ "&text=" + PERMISSION_TEXT 	+ "&appName=" + APP_NAME, MediaType.TEXT_PLAIN, Boolean.class);
		
		assertTrue(b);
	}

	@Test
	public void testHasNotPermissionInSystem()
		throws EntityNotFoundException
	{	
		Mockito.when(authorizationServicesMock.hasPermission(PERSON_ID, OTHER_PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.FALSE);
		
		Boolean b = getResourceClient().performRestCall_GET(HAS_SYSTEM_PERMISSION_RESOURCE_PATH + "?personId=" + PERSON_ID 
			+ "&text=" + OTHER_PERMISSION_TEXT 	+ "&appName=" + APP_NAME, MediaType.TEXT_PLAIN, Boolean.class);
		
		assertFalse(b);
	}

	@Test
	public void testHasPermissionTechUser()
		throws EntityNotFoundException
	{		
		Mockito.when(authorizationServicesMock.hasPermissionTechUser(TECH_USER_ID, PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.TRUE);		
		
		Boolean b = getResourceClient().performRestCall_GET(HAS_TECH_USER_PERMISSION_RESOURCE_PATH + "?techUserId=" + TECH_USER_ID 
			+ "&text=" + PERMISSION_TEXT 	+ "&appName=" + APP_NAME, MediaType.TEXT_PLAIN, Boolean.class);
		
		assertTrue(b);		
	}

	@Test
	public void testHasNotPermissionTechUser()
		throws EntityNotFoundException
	{		
		Mockito.when(authorizationServicesMock.hasPermissionTechUser(TECH_USER_ID, OTHER_PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.FALSE);		
		
		Boolean b = getResourceClient().performRestCall_GET(HAS_TECH_USER_PERMISSION_RESOURCE_PATH + "?techUserId=" + TECH_USER_ID 
			+ "&text=" + OTHER_PERMISSION_TEXT 	+ "&appName=" + APP_NAME, MediaType.TEXT_PLAIN, Boolean.class);
		
		assertFalse(b);	
	}

	@Override
	protected String getBaseURI()
	{
		return super.getBaseURI() + AUTHORIZATION_RESOURCE_PATH;
	}
}
