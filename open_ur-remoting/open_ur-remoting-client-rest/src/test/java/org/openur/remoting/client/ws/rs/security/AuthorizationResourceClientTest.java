package org.openur.remoting.client.ws.rs.security;

import static org.junit.Assert.*;

import java.util.UUID;

import javax.ws.rs.core.Application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.module.util.exception.EntityNotFoundException;
import org.openur.remoting.resource.security.AuthorizationResource;

public class AuthorizationResourceClientTest
	extends JerseyTest
{
	private static final String APP_NAME = "appName";
	private static final String PERMISSION_TEXT = "permissionText";
	private static final String OTHER_PERMISSION_TEXT = "otherPermissionText";
	private static final String PERSON_ID = UUID.randomUUID().toString();
	private static final String OU_ID = UUID.randomUUID().toString();
	private static final String TECH_USER_ID = UUID.randomUUID().toString();

	private IAuthorizationServices authorizationServicesMock;
	private AuthorizationResourceClient authorizationResourceClient;

	@Override
	protected Application configure()
	{		
		// mocked service:
		authorizationServicesMock = Mockito.mock(IAuthorizationServices.class);
		
		// Client:
		authorizationResourceClient = new AuthorizationResourceClient("http://localhost:9998/");
		
		// Http-Testserver:
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
		throws EntityNotFoundException
	{
		Mockito.when(authorizationServicesMock.hasPermission(PERSON_ID, OU_ID, PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.TRUE);		
		
		Boolean b = authorizationResourceClient.hasPermission(PERSON_ID, OU_ID, PERMISSION_TEXT, APP_NAME);

		System.out.println("Result: " + b);
		assertTrue(b);		
	}
	
	@Test
	public void testHasNoPermissionInOrgUnit()
		throws EntityNotFoundException
	{
		Mockito.when(authorizationServicesMock.hasPermission(PERSON_ID, OU_ID, OTHER_PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.FALSE);
		
		Boolean b = authorizationResourceClient.hasPermission(PERSON_ID, OU_ID, OTHER_PERMISSION_TEXT, APP_NAME);
	
		System.out.println("Result: " + b);
		assertFalse(b);	
	}

	@Test
	public void testHasPermissionInSystem()
		throws EntityNotFoundException
	{
		Mockito.when(authorizationServicesMock.hasPermission(PERSON_ID, PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.TRUE);		
		
		Boolean b = authorizationResourceClient.hasPermission(PERSON_ID, PERMISSION_TEXT, APP_NAME);
	
		System.out.println("Result: " + b);
		assertTrue(b);	
	}

	@Test
	public void testHasNotPermissionInSystem()
		throws EntityNotFoundException
	{
		Mockito.when(authorizationServicesMock.hasPermission(PERSON_ID, OTHER_PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.FALSE);
		
		Boolean b = authorizationResourceClient.hasPermission(PERSON_ID, OTHER_PERMISSION_TEXT, APP_NAME);
	
		System.out.println("Result: " + b);
		assertFalse(b);
	}

	@Test
	public void testHasPermissionTechUser()
		throws EntityNotFoundException
	{
		Mockito.when(authorizationServicesMock.hasPermissionTechUser(TECH_USER_ID, PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.TRUE);
		
		Boolean b = authorizationResourceClient.hasPermissionTechUser(TECH_USER_ID, PERMISSION_TEXT, APP_NAME);
	
		System.out.println("Result: " + b);
		assertTrue(b);
	}

	@Test
	public void testHasNotPermissionTechUser()
		throws EntityNotFoundException
	{		
		Mockito.when(authorizationServicesMock.hasPermissionTechUser(TECH_USER_ID, OTHER_PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.FALSE);	
		
		Boolean b = authorizationResourceClient.hasPermission(TECH_USER_ID, OTHER_PERMISSION_TEXT, APP_NAME);
	
		System.out.println("Result: " + b);
		assertFalse(b);
	}
}
