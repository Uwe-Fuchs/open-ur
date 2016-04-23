package org.openur.remoting.resource.security;

import static org.junit.Assert.*;
import static org.openur.remoting.resource.security.AuthorizationResource.*;

import java.util.UUID;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.remoting.resource.AbstractResourceTest;

public class AuthorizationResourceTest
	extends AbstractResourceTest
{
	public static final String APP_NAME = "appName";
	public static final String PERMISSION_TEXT = "permissionText";
	public static final String OTHER_PERMISSION_TEXT = "otherPermissionText";
	public static final String PERSON_ID = UUID.randomUUID().toString();
	public static final String OU_ID = UUID.randomUUID().toString();
	
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
			.register(binder);

		return config;
	}
	
	@Test
	public void testHasPermissionInOrgUnit()
	{
		Mockito.when(authorizationServicesMock.hasPermission(PERSON_ID, OU_ID, PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.TRUE);		
		
		Boolean b = getResourceClient().performRestCall_GET(HAS_OU_PERMISSION_RESOURCE_PATH + "?personId=" + PERSON_ID 
				+ "&ouId=" + OU_ID + "&text=" + PERMISSION_TEXT	+ "&appName=" + APP_NAME, MediaType.TEXT_PLAIN, Boolean.class);
		
		assertTrue(b);
	}
	
	@Test
	public void testHasNoPermissionInOrgUnit()
	{		
		Mockito.when(authorizationServicesMock.hasPermission(PERSON_ID, OU_ID, OTHER_PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.FALSE);
		
		Boolean b = getResourceClient().performRestCall_GET(HAS_OU_PERMISSION_RESOURCE_PATH + "?personId=" + PERSON_ID 
				+ "&ouId=" + OU_ID + "&text=" + PERMISSION_TEXT	+ "&appName=" + APP_NAME, MediaType.TEXT_PLAIN, Boolean.class);
		
		assertFalse(b);
	}

	@Test
	public void testHasPermissionInSystem()
	{
		Mockito.when(authorizationServicesMock.hasPermission(PERSON_ID, PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.TRUE);		
		
		Boolean b = getResourceClient().performRestCall_GET(HAS_SYSTEM_PERMISSION_RESOURCE_PATH + "?personId=" + PERSON_ID 
			+ "&text=" + PERMISSION_TEXT 	+ "&appName=" + APP_NAME, MediaType.TEXT_PLAIN, Boolean.class);
		
		assertTrue(b);
	}

	@Test
	public void testHasNotPermissionInSystem()
	{	
		Mockito.when(authorizationServicesMock.hasPermission(PERSON_ID, OTHER_PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.FALSE);
		
		Boolean b = getResourceClient().performRestCall_GET(HAS_SYSTEM_PERMISSION_RESOURCE_PATH + "?personId=" + PERSON_ID 
			+ "&text=" + OTHER_PERMISSION_TEXT 	+ "&appName=" + APP_NAME, MediaType.TEXT_PLAIN, Boolean.class);
		
		assertFalse(b);
	}

	@Override
	protected String getBaseURI()
	{
		return super.getBaseURI() + AUTHORIZATION_RESOURCE_PATH;
	}
}
