package org.openur.module.domain.security.authorization;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;

public class OpenURPermissionTest
{
	@Test
	public void testCompareTo()
	{
		OpenURApplication app = new OpenURApplicationBuilder("app").build();		
		
		OpenURPermission perm1 = new OpenURPermissionBuilder("perm1", PermissionScope.SELECTED, app).build();		
		OpenURPermission perm2 = new OpenURPermissionBuilder("perm2", PermissionScope.SELECTED, app).build();
		
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
		OpenURApplication app = new OpenURApplicationBuilder("app").build();
		new OpenURPermissionBuilder("perm", null, app);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionCreateWithEmptyNameAndApp()
	{
		OpenURApplication app = new OpenURApplicationBuilder("app").build();
		new OpenURPermissionBuilder("", PermissionScope.SELECTED, app);
	}
}
