package org.openur.module.domain.userstructure.technicaluser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURPermissionBuilder;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser.TechnicalUserBuilder;
import org.openur.module.util.data.Status;

public class TechnicalUserTest
{
	@Test
	public void testCompareTo()
	{
		TechnicalUserBuilder tub = new TechnicalUserBuilder("abc");
		tub.status(Status.ACTIVE);
		TechnicalUser tu1 = tub.build();
		
		tub = new TechnicalUserBuilder("xyz");
		TechnicalUser tu2 = tub.build();
		
		assertTrue(tu1.compareTo(tu2) < 0);
	}
	
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

		TechnicalUser techUser = new TechnicalUserBuilder("abc")
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
	public void testGetApplications()
	{
		OpenURApplication app1 = new OpenURApplicationBuilder("app1")
			.build();		
		OpenURPermission perm1 = new OpenURPermissionBuilder("perm1", app1)
			.build();
		
		OpenURApplication app2 = new OpenURApplicationBuilder("app2")
			.build();
		OpenURPermission perm2 = new OpenURPermissionBuilder("perm2", app2)
			.build();		
	
		TechnicalUser techUser = new TechnicalUserBuilder("abc")
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm1, perm2)))
			.build();
		
		Set<OpenURApplication> applications = techUser.getApplications();
		assertEquals(applications.size(), 2);
		assertTrue(applications.contains(app1));
		assertTrue(applications.contains(app2));	
	}
}