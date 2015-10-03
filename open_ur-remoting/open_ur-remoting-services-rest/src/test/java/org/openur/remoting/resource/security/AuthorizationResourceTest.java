package org.openur.remoting.resource.security;

import static org.junit.Assert.*;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.remoting.resource.AbstractResourceTest;

public class AuthorizationResourceTest
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
		String result = performRestCall("authorization/ou" + "?personId=" + MockAuthorizationServicesFactory.PERSON_ID + "&ouId="
			+ MockAuthorizationServicesFactory.OU_ID + "&text=" + MockAuthorizationServicesFactory.PERMISSION_TEXT
			+ "&appName=" + MockAuthorizationServicesFactory.APP_NAME, MediaType.TEXT_PLAIN, String.class);
		
		Boolean b = Boolean.valueOf(result);
		
		assertTrue(b);
	}
	
	@Test
	public void testHasNoPermissionInOrgUnit()
	{
		String result = performRestCall("authorization/ou" + "?personId=" + MockAuthorizationServicesFactory.PERSON_ID + "&ouId="
			+ MockAuthorizationServicesFactory.OU_ID + "&text=" + MockAuthorizationServicesFactory.OTHER_PERMISSION_TEXT
			+ "&appName=" + MockAuthorizationServicesFactory.APP_NAME, MediaType.TEXT_PLAIN, String.class);
		
		Boolean b = Boolean.valueOf(result);
		
		assertFalse(b);
	}

	@Test
	public void testHasPermissionInSystem()
	{
		String result = performRestCall("authorization/system" + "?personId=" + MockAuthorizationServicesFactory.PERSON_ID 
			+ "&text=" + MockAuthorizationServicesFactory.PERMISSION_TEXT
			+ "&appName=" + MockAuthorizationServicesFactory.APP_NAME, MediaType.TEXT_PLAIN, String.class);
		
		Boolean b = Boolean.valueOf(result);
		
		assertTrue(b);
	}

	@Test
	public void testHasNotPermissionInSystem()
	{
		String result = performRestCall("authorization/system" + "?personId=" + MockAuthorizationServicesFactory.PERSON_ID 
			+ "&text=" + MockAuthorizationServicesFactory.OTHER_PERMISSION_TEXT
			+ "&appName=" + MockAuthorizationServicesFactory.APP_NAME, MediaType.TEXT_PLAIN, String.class);
		
		Boolean b = Boolean.valueOf(result);
		
		assertFalse(b);
	}
}
