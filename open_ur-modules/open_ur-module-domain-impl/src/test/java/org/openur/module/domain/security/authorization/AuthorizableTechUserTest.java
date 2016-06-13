package org.openur.module.domain.security.authorization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;

public class AuthorizableTechUserTest
{

	@Test
	public void testGetAllPermissions()
	{
		OpenURApplication app1 = new OpenURApplicationBuilder("app1")
			.build();		
		OpenURPermission perm1 = new OpenURPermissionBuilder("perm1", app1)
			.build();
		
		OpenURApplication app2 = new OpenURApplicationBuilder("app2")
			.build();
		OpenURPermission perm2 = new OpenURPermissionBuilder("perm2", app2)
			.build();
		
		AuthorizableTechUser techUser = new AuthorizableTechUserBuilder("abc")
			.identifier("someId")
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm1, perm2)))
			.build();
		
		Set<OpenURPermission> perms = techUser.getPermissions(app1);
		assertEquals(perms.size(), 1);
		assertEquals(perms.iterator().next(), perm1);
		
		perms = techUser.getPermissions(app2);
		assertEquals(perms.size(), 1);
		assertEquals(perms.iterator().next(), perm2);
	}

	@Test
	public void testContainsPermission()
	{
		OpenURApplication app1 = new OpenURApplicationBuilder("app1")
			.build();		
		OpenURPermission perm1 = new OpenURPermissionBuilder("perm1", app1)
			.build();
		
		AuthorizableTechUser techUser = new AuthorizableTechUserBuilder("abc")
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm1)))
			.build();
		
		assertTrue(techUser.containsPermission(app1, perm1));
		
		OpenURApplication app2 = new OpenURApplicationBuilder("app2")
			.build();
		OpenURPermission perm2 = new OpenURPermissionBuilder("perm2", app2)
			.build();
		
		assertFalse(techUser.containsPermission(app2, perm2));
	}

	@Test
	public void testHasPermission()
	{
		OpenURApplication app1 = new OpenURApplicationBuilder("app1")
			.build();		
		OpenURPermission perm11 = new OpenURPermissionBuilder("perm11", app1)
			.build();
		OpenURPermission perm12 = new OpenURPermissionBuilder("perm12", app1)
			.build();	
		
		AuthorizableTechUser techUser = new AuthorizableTechUserBuilder("techUser_1")
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm11, perm12)))
			.build();
		
		assertTrue(techUser.hasPermission(perm11));
		assertTrue(techUser.hasPermission(perm12));
		
		OpenURApplication app2 = new OpenURApplicationBuilder("app2")
			.build();		
		OpenURPermission perm21 = new OpenURPermissionBuilder("perm21", app2)
			.build();
		OpenURPermission perm22 = new OpenURPermissionBuilder("perm22", app2)
			.build();	
		
		AuthorizableTechUser techUser2 = new AuthorizableTechUserBuilder("techUser_2")
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm21, perm22)))
			.build();
		
		assertTrue(techUser2.hasPermission(perm21));
		assertTrue(techUser2.hasPermission(perm22));
		
		assertFalse(techUser2.hasPermission(perm11));
		assertFalse(techUser2.hasPermission(perm12));
	}
}
