package org.openur.module.domain.security;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class OpenURRoleTest
{
	@Test
	public void testGetPermissions()
	{
		OpenURApplicationBuilder ab = new OpenURApplicationBuilder("app1", "user1", "pw1");
		OpenURApplication app1 = new OpenURApplication(ab);		
		OpenURPermissionBuilder pb = new OpenURPermissionBuilder("perm1", PermissionScope.SELECTED, app1);
		OpenURPermission perm1 = new OpenURPermission(pb);
		
		ab = new OpenURApplicationBuilder("app2", "user2", "pw2");
		OpenURApplication app2 = new OpenURApplication(ab);		
		pb = new OpenURPermissionBuilder("perm2", PermissionScope.SUB, app2);
		OpenURPermission perm2 = new OpenURPermission(pb);
		
		OpenURRoleBuilder rb = new OpenURRoleBuilder("role1");
		rb.permissions(new HashSet<IPermission>(Arrays.asList(perm1, perm2)));
		OpenURRole role = new OpenURRole(rb);
		
		Set<IPermission> perms = role.getPermissions(app1);
		assertEquals(perms.size(), 1);
		assertEquals("single element in the returned set should be perm1", perms.iterator().next(), perm1);
		
		perms = role.getPermissions(app2);
		assertEquals(perms.size(), 1);
		assertEquals("single element in the returned set should be perm2", perms.iterator().next(), perm2);
	}

	@Test
	public void testCompareTo()
	{
		OpenURApplicationBuilder ab = new OpenURApplicationBuilder("app1", "user1", "pw1");
		OpenURApplication app1 = new OpenURApplication(ab);		
		OpenURPermissionBuilder pb = new OpenURPermissionBuilder("perm1", PermissionScope.SELECTED, app1);
		OpenURPermission perm1 = new OpenURPermission(pb);		
		OpenURRoleBuilder rb = new OpenURRoleBuilder("role1");
		rb.permissions(new HashSet<IPermission>(Arrays.asList(perm1)));
		OpenURRole role1 = new OpenURRole(rb);
		
		ab = new OpenURApplicationBuilder("app2", "user2", "pw2");
		OpenURApplication app2 = new OpenURApplication(ab);		
		pb = new OpenURPermissionBuilder("perm2", PermissionScope.SELECTED_SUB, app2);
		OpenURPermission perm2 = new OpenURPermission(pb);		
		rb = new OpenURRoleBuilder("role2");
		rb.permissions(new HashSet<IPermission>(Arrays.asList(perm2)));
		OpenURRole role2 = new OpenURRole(rb);
		
		assertTrue("role1 should be before role2", role1.compareTo(role2) < 1);
		
		rb = new OpenURRoleBuilder("role1");
		rb.permissions(new HashSet<IPermission>(Arrays.asList(perm2)));
		OpenURRole role1_1 = new OpenURRole(rb);
		
		assertTrue(role1.compareTo(role1_1) == role1.getIdentifier().compareTo(role1_1.getIdentifier()));
	}
}
