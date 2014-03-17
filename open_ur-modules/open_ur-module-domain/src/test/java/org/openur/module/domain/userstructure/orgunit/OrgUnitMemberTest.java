package org.openur.module.domain.userstructure.orgunit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;
import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.security.IRole;
import org.openur.module.domain.security.OpenURApplication;
import org.openur.module.domain.security.OpenURApplicationBuilder;
import org.openur.module.domain.security.OpenURPermission;
import org.openur.module.domain.security.OpenURPermissionBuilder;
import org.openur.module.domain.security.OpenURRole;
import org.openur.module.domain.security.OpenURRoleBuilder;
import org.openur.module.domain.security.PermissionScope;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.user.person.IPerson;
import org.openur.module.domain.userstructure.user.person.Person;
import org.openur.module.domain.userstructure.user.person.PersonBuilder;

public class OrgUnitMemberTest
{
	@Test
	public void testEqualsObject()
	{
		PersonBuilder persBuilder = new PersonBuilder("username1", "password1")
			.number("123abc")
			.status(Status.ACTIVE);
		IPerson pers1 = new Person(persBuilder);
		
		persBuilder = new PersonBuilder("username2", "password2")
			.number("456xyz")
			.status(Status.ACTIVE);
		IPerson pers2 = new Person(persBuilder);
	
		IOrgUnitMember m1 = new OrgUnitMember(new OrgUnitMemberBuilder(pers1));
		assertTrue(m1.equals(m1));
		
		IOrgUnitMember m2 = new OrgUnitMember(new OrgUnitMemberBuilder(pers2));		
		assertFalse(m1.equals(m2));
		assertFalse(m2.equals(m1));
		
		m2 = new OrgUnitMember(new OrgUnitMemberBuilder(pers1));
		assertTrue(m1.equals(m2));
	}

	@Test
	public void testHasRole()
	{
		OpenURApplicationBuilder ab = new OpenURApplicationBuilder("app1", "user1", "pw1");
		IApplication app1 = new OpenURApplication(ab);		
		OpenURPermissionBuilder pb = new OpenURPermissionBuilder("perm1", PermissionScope.SELECTED, app1);
		IPermission perm1 = new OpenURPermission(pb);		
		OpenURRoleBuilder rb = new OpenURRoleBuilder("role1");
		rb.permissions(new HashSet<IPermission>(Arrays.asList(perm1)));
		IRole role1 = new OpenURRole(rb);		
		PersonBuilder persBuilder = new PersonBuilder("username1", "password1")
			.number("123abc")
			.status(Status.ACTIVE);
		IPerson pers = new Person(persBuilder);		
		IOrgUnitMember m1 = new OrgUnitMember(pers, Arrays.asList(role1));
		
		assertTrue(m1.hasRole(role1));
		
		ab = new OpenURApplicationBuilder("app2", "user2", "pw2");
		IApplication app2 = new OpenURApplication(ab);		
		pb = new OpenURPermissionBuilder("perm2", PermissionScope.SUB, app2);
		IPermission perm2 = new OpenURPermission(pb);		
		rb = new OpenURRoleBuilder("role2");
		rb.permissions(new HashSet<IPermission>(Arrays.asList(perm2)));
		IRole role2 = new OpenURRole(rb);
		
		assertFalse(m1.hasRole(role2));
	}

	@Test
	public void testHasPermission()
	{
		OpenURApplicationBuilder ab = new OpenURApplicationBuilder("app1", "user1", "pw1");
		IApplication app1 = new OpenURApplication(ab);		
		OpenURPermissionBuilder pb = new OpenURPermissionBuilder("perm1", PermissionScope.SELECTED,  app1);
		IPermission perm1 = new OpenURPermission(pb);
		
		ab = new OpenURApplicationBuilder("app2", "user2", "pw2");
		OpenURApplication app2 = new OpenURApplication(ab);		
		pb = new OpenURPermissionBuilder("perm2", PermissionScope.SUB, app2);
		IPermission perm2 = new OpenURPermission(pb);
		
		OpenURRoleBuilder rb = new OpenURRoleBuilder("role1");
		rb.permissions(new HashSet<IPermission>(Arrays.asList(perm1, perm2)));
		IRole role = new OpenURRole(rb);
		
		PersonBuilder persBuilder = new PersonBuilder("username1", "password1")
			.number("123abc")
			.status(Status.ACTIVE);
		IPerson pers = new Person(persBuilder);
		
		IOrgUnitMember member = new OrgUnitMember(pers, Arrays.asList(role));
		
		assertTrue(member.hasPermission(app1, perm1));
		assertTrue(member.hasPermission(app2, perm2));
		assertFalse(member.hasPermission(app1, perm2));
		
		pb = new OpenURPermissionBuilder("perm3", PermissionScope.SELECTED_SUB,  app1);
		IPermission perm3 = new OpenURPermission(pb);
		assertFalse(member.hasPermission(app1, perm3));
	}

	@Test
	public void testCompareTo()
	{		
		PersonBuilder persBuilder = new PersonBuilder("username1", "password1")
			.number("123abc")
			.status(Status.ACTIVE);
		IPerson pers1 = new Person(persBuilder);
		IOrgUnitMember m1 = new OrgUnitMember(new OrgUnitMemberBuilder(pers1));
		
		persBuilder = new PersonBuilder("username2", "password2")
			.number("456xyz")
			.status(Status.ACTIVE);
		IPerson pers2 = new Person(persBuilder);
		IOrgUnitMember m2 = new OrgUnitMember(new OrgUnitMemberBuilder(pers2));
		
		assertTrue(m1.compareTo(m2) < 0);
		
		m2 = new OrgUnitMember(new OrgUnitMemberBuilder(pers1));
		assertTrue(m1.compareTo(m2) == 0);		
	}
}
