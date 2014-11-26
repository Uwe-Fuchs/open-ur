package org.openur.module.domain.security.authorization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;

public class OpenURRoleTest
{
	@Test
	public void testGetPermissions()
	{
		OpenURApplication app1 = new OpenURApplicationBuilder("app1")
			.build();		
		OpenURPermission perm1 = new OpenURPermissionBuilder("perm1", app1)
			.build();
		
		OpenURApplication app2 = new OpenURApplicationBuilder("app2")
			.build();
		OpenURPermission perm2 = new OpenURPermissionBuilder("perm2", app2)
			.build();

		OpenURRole role = new OpenURRoleBuilder("role1")
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm1, perm2)))
			.build();
		
		Set<OpenURPermission> perms = role.getPermissions(app1);
		assertEquals(perms.size(), 1);
		assertEquals("single element in the returned set should be perm1", perms.iterator().next(), perm1);
		
		perms = role.getPermissions(app2);
		assertEquals(perms.size(), 1);
		assertEquals("single element in the returned set should be perm2", perms.iterator().next(), perm2);
	}
	
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
	
		OpenURRole role = new OpenURRoleBuilder("role")
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm1, perm2)))
			.build();
		
		Map<OpenURApplication, Set<? extends IPermission>> perms = role.getAllPermissions();
		assertEquals(perms.size(), 2);
		assertEquals(perms.get(app1).size(), 1);
		assertEquals(perms.get(app1).iterator().next(), perm1);
		assertEquals(perms.get(app2).size(), 1);
		assertEquals(perms.get(app2).iterator().next(), perm2);
	}

	@Test
	public void testCompareTo()
	{
		OpenURApplication app1 = new OpenURApplicationBuilder("app1").build();		
		OpenURPermission perm1 = new OpenURPermissionBuilder("perm1", app1).build();		
		OpenURRole role1 = new OpenURRoleBuilder("role1")
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm1)))
			.build();
		
		OpenURApplication app2 = new OpenURApplicationBuilder("app2")
			.build();		
		OpenURPermission perm2 = new OpenURPermissionBuilder("perm2", app2)
			.build();		
		OpenURRole role2 = new OpenURRoleBuilder("role2")
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm2)))
			.build();
		
		assertTrue("role1 should be before role2", role1.compareTo(role2) < 1);
		
		OpenURRole role1_1 = new OpenURRoleBuilder("role1")
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm2)))
			.build();
		
		assertTrue(role1.compareTo(role1_1) == role1.getIdentifier().compareTo(role1_1.getIdentifier()));
	}
}
