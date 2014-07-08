package org.openur.module.domain.security.application;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.OpenURPermissionBuilder;
import org.openur.module.domain.security.authorization.PermissionScope;

public class OpenURPermissionTest
{
	@Test
	public void testCompareTo()
	{
		IApplication app = new OpenURApplicationBuilder("app", "user1", "pw").build();		
		
		IPermission perm1 = new OpenURPermissionBuilder("perm1", PermissionScope.SELECTED, app).build();		
		IPermission perm2 = new OpenURPermissionBuilder("perm2", PermissionScope.SELECTED, app).build();
		
		assertTrue(perm1.compareTo(perm2) < 0);
	}
	
//	@Test(expected=IllegalArgumentException.class)
//	public void testExceptionCreateWithEmptyNameAndEmptyApp()
//	{
//		new OpenURPermissionBuilder("", null);
//	}
	
	@Test(expected=NullPointerException.class)
	public void testExceptionCreateWithNameAndEmptyApp()
	{
		new OpenURPermissionBuilder("perm", PermissionScope.SELECTED, null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testExceptionCreateWithNameAndAppAndEmptyScope()
	{
		IApplication app = new OpenURApplicationBuilder("app", "user1", "pw").build();
		new OpenURPermissionBuilder("perm", null, app);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionCreateWithEmptyNameAndApp()
	{
		IApplication app = new OpenURApplicationBuilder("app", "user1", "pw").build();
		new OpenURPermissionBuilder("", PermissionScope.SELECTED, app);
	}
}
