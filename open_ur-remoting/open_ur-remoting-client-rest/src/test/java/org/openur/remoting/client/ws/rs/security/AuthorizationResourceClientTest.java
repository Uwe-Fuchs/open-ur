package org.openur.remoting.client.ws.rs.security;

import static org.junit.Assert.*;

import javax.ws.rs.core.Application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.remoting.resource.security.AuthorizationResource;
import org.openur.remoting.resource.security.MockAuthorizationServicesFactory;

public class AuthorizationResourceClientTest
	extends JerseyTest
{
	private IAuthorizationServices authorizationServices;

	@Override
	protected Application configure()
	{		
		// Client:
		authorizationServices = new AuthorizationResourceClient("http://localhost:9998/");
		
		// Http-Testserver:
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bindFactory(MockAuthorizationServicesFactory.class).to(IAuthorizationServices.class);
			}
		};
		
		ResourceConfig config = new ResourceConfig(AuthorizationResource.class)
			.register(binder);

		return config;
	}
	
	@Test
	public void testHasPermissionInOrgUnit()
	{
		Boolean b = authorizationServices.hasPermission(
				MockAuthorizationServicesFactory.PERSON_ID, MockAuthorizationServicesFactory.OU_ID, 
				MockAuthorizationServicesFactory.PERMISSION_TEXT, MockAuthorizationServicesFactory.APP_NAME);

		System.out.println("Result: " + b);
		assertTrue(b);		
	}
	
	@Test
	public void testHasNoPermissionInOrgUnit()
	{
		Boolean b = authorizationServices.hasPermission(
				MockAuthorizationServicesFactory.PERSON_ID, MockAuthorizationServicesFactory.OU_ID, 
				MockAuthorizationServicesFactory.OTHER_PERMISSION_TEXT, MockAuthorizationServicesFactory.APP_NAME);
	
		System.out.println("Result: " + b);
		assertFalse(b);	
	}

	@Test
	public void testHasPermissionInSystem()
	{
		Boolean b = authorizationServices.hasPermission(
				MockAuthorizationServicesFactory.PERSON_ID, MockAuthorizationServicesFactory.PERMISSION_TEXT, MockAuthorizationServicesFactory.APP_NAME);
	
		System.out.println("Result: " + b);
		assertTrue(b);	
	}

	@Test
	public void testHasNotPermissionInSystem()
	{
		Boolean b = authorizationServices.hasPermission(
				MockAuthorizationServicesFactory.PERSON_ID, MockAuthorizationServicesFactory.OTHER_PERMISSION_TEXT, MockAuthorizationServicesFactory.APP_NAME);
	
		System.out.println("Result: " + b);
		assertFalse(b);
	}
}
