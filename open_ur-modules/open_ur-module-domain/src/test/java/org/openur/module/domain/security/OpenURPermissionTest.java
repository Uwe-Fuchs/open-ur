package org.openur.module.domain.security;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OpenURPermissionTest
{
	@Test
	public void testCompareTo()
	{
		OpenURApplicationBuilder ab = new OpenURApplicationBuilder("app", "user1", "pw");
		OpenURApplication app = new OpenURApplication(ab);
		
		OpenURPermissionBuilder pb = new OpenURPermissionBuilder("perm1", app);
		OpenURPermission perm1 = new OpenURPermission(pb);
		
		pb = new OpenURPermissionBuilder("perm2", app);
		OpenURPermission perm2 = new OpenURPermission(pb);
		
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
		new OpenURPermissionBuilder("perm", null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testExceptionCreateWithEmptyNameAndApp()
	{
		OpenURApplicationBuilder ab = new OpenURApplicationBuilder("app", "user1", "pw");
		OpenURApplication app = new OpenURApplication(ab);
		
		new OpenURPermissionBuilder("", app);
	}
}
