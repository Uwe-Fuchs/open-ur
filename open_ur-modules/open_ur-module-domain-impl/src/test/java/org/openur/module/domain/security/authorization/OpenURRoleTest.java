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

		OpenURRoleBuilder roleBuilder = new OpenURRoleBuilder("role1");
		OpenURRole role = roleBuilder
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm1, perm2)), roleBuilder)
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

		OpenURRoleBuilder roleBuilder = new OpenURRoleBuilder("role1");
		OpenURRole role = roleBuilder
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm1, perm2)), roleBuilder)
			.build();
		
		Map<OpenURApplication, Set<OpenURPermission>> perms = role.getAllPermissions();
		assertEquals(perms.size(), 2);
		assertEquals(perms.get(app1).size(), 1);
		assertEquals(perms.get(app1).iterator().next(), perm1);
		assertEquals(perms.get(app2).size(), 1);
		assertEquals(perms.get(app2).iterator().next(), perm2);
	}

	@Test
	public void testCompareTo()
	{
		OpenURApplication app1 = new OpenURApplicationBuilder("app1")
			.build();		
		OpenURPermission perm1 = new OpenURPermissionBuilder("perm1", app1)
			.build();	
		OpenURRoleBuilder roleBuilder = new OpenURRoleBuilder("role1");
		OpenURRole role1 = roleBuilder
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm1)), roleBuilder)
			.build();
		
		OpenURApplication app2 = new OpenURApplicationBuilder("app2")
			.build();		
		OpenURPermission perm2 = new OpenURPermissionBuilder("perm2", app2)
			.build();
		roleBuilder = new OpenURRoleBuilder("role2");
		OpenURRole role2 = roleBuilder
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm2)), roleBuilder)
			.build();
		
		assertTrue("role1 should be before role2", role1.compareTo(role2) < 1);

		roleBuilder = new OpenURRoleBuilder("role1");
		OpenURRole role1_1 = roleBuilder
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm2)), roleBuilder)
			.build();
		
		assertTrue(role1.compareTo(role1_1) == role1.getIdentifier().compareTo(role1_1.getIdentifier()));
	}
}
