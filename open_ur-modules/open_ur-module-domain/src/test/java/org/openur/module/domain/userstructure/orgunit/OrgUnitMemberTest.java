package org.openur.module.domain.userstructure.orgunit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;
import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.security.IRole;
import org.openur.module.domain.security.OpenURApplicationBuilder;
import org.openur.module.domain.security.OpenURPermissionBuilder;
import org.openur.module.domain.security.OpenURRoleBuilder;
import org.openur.module.domain.security.PermissionScope;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.user.person.IPerson;
import org.openur.module.domain.userstructure.user.person.PersonBuilder;

public class OrgUnitMemberTest
{
	@Test
	public void testEqualsObject()
	{
		IPerson pers1 = new PersonBuilder("username1", "password1")
			.number("123abc")
			.status(Status.ACTIVE)
			.build();
		IOrganizationalUnit orgUnit1 = new OrgUnitSimpleBuilder()
			.number("123ABC")
			.status(Status.ACTIVE)
			.build();		
		IOrgUnitMember m11 = new OrgUnitMemberBuilder(pers1, orgUnit1)
			.build();
		
		assertTrue(m11.equals(m11));
		
		IPerson pers2 = new PersonBuilder("username2", "password2")
			.number("456xyz")
			.status(Status.ACTIVE)
			.build();
		IOrgUnitMember m21 = new OrgUnitMemberBuilder(pers2, orgUnit1)
			.build();	
		
		assertFalse(m11.equals(m21));
		assertFalse(m21.equals(m11));

		IOrganizationalUnit orgUnit2 = new OrgUnitSimpleBuilder()
			.number("456XYZ")
			.status(Status.ACTIVE)
			.build();
		IOrgUnitMember m12 = new OrgUnitMemberBuilder(pers1, orgUnit2)
			.build();
		IOrgUnitMember m22 = new OrgUnitMemberBuilder(pers2, orgUnit2)
			.build();
		
		assertFalse(m11.equals(m12));
		assertFalse(m21.equals(m12));
		assertFalse(m22.equals(m12));
		assertFalse(m11.equals(m22));
		assertFalse(m12.equals(m22));
		assertFalse(m21.equals(m22));
	}

	@Test
	public void testCompareTo()
	{		
		IPerson pers1 = new PersonBuilder("username1", "password1")
			.number("123")
			.status(Status.ACTIVE)
			.build();
		IOrganizationalUnit orgUnit1 = new OrgUnitSimpleBuilder()
			.number("XYZ")
			.status(Status.ACTIVE)
			.build();
		IOrgUnitMember m11 = new OrgUnitMemberBuilder(pers1, orgUnit1)
			.build();
		
		assertTrue(m11.compareTo(m11) == 0);
		
		IPerson pers2 = new PersonBuilder("username2", "password2")
			.number("456")
			.status(Status.ACTIVE)
			.build();
		IOrgUnitMember m21 = new OrgUnitMemberBuilder(pers2, orgUnit1)
			.build();
		
		assertTrue(m11.compareTo(m21) < 0);
		
		IOrganizationalUnit orgUnit2 = new OrgUnitSimpleBuilder()
			.number("ABC")
			.status(Status.ACTIVE)
			.build();
		IOrgUnitMember m12 = new OrgUnitMemberBuilder(pers1, orgUnit2)
			.build();
		IOrgUnitMember m22 = new OrgUnitMemberBuilder(pers2, orgUnit2)
			.build();
	
		assertTrue(m11.compareTo(m12) > 0);
		assertTrue(m11.compareTo(m22) > 0);
		assertTrue(m12.compareTo(m21) < 0);
		assertTrue(m12.compareTo(m22) < 0);
		assertTrue(m21.compareTo(m22) > 0);
	}

	@Test
	public void testHasRole()
	{
		IRole role1 = new OpenURRoleBuilder("role1")
			.build();		
		IPerson pers = new PersonBuilder("username1", "password1")
			.number("123")
			.status(Status.ACTIVE)
			.build();
		IOrganizationalUnit orgUnit1 = new OrgUnitSimpleBuilder()
			.number("ABC")
			.build();
		IOrgUnitMember member = new OrgUnitMemberBuilder(pers, orgUnit1)
		  .roles(Arrays.asList(role1))
		  .build();		
		assertTrue(member.hasRole(role1));
		
		IRole role2 = new OpenURRoleBuilder("role2")
			.build();		
		assertFalse(member.hasRole(role2));
		
		member = new OrgUnitMemberBuilder(pers, orgUnit1)
	  	.roles(Arrays.asList(role1, role2))
	  	.build();
		assertTrue(member.hasRole(role1));
		assertTrue(member.hasRole(role2));
	}

	@Test
	public void testHasPermission()
	{
		IApplication app1 = new OpenURApplicationBuilder("app1", "user1", "pw1")
			.build();		
		IPermission perm11 = new OpenURPermissionBuilder("perm11", PermissionScope.SELECTED,  app1)
			.build();
		IPermission perm12 = new OpenURPermissionBuilder("perm12", PermissionScope.SELECTED_SUB,  app1)
			.build();		
		IRole role1 = new OpenURRoleBuilder("role1")
			.permissions(new HashSet<IPermission>(Arrays.asList(perm11, perm12)))
			.build();
		
		IPerson person = new PersonBuilder("username1", "password1")
			.build();
		IOrganizationalUnit orgUnit = new OrgUnitSimpleBuilder()
			.build();
		
		IOrgUnitMember member = new OrgUnitMemberBuilder(person, orgUnit)
		  .roles(Arrays.asList(role1))
		  .build();		
		
		assertTrue(member.hasPermission(app1, perm11));
		assertTrue(member.hasPermission(app1, perm12));
		
		IApplication app2 = new OpenURApplicationBuilder("app2", "user2", "pw2")
			.build();		
		IPermission perm21 = new OpenURPermissionBuilder("perm21", PermissionScope.SELECTED,  app2)
			.build();
		IPermission perm22 = new OpenURPermissionBuilder("perm22", PermissionScope.SUB,  app2)
			.build();		
		IRole role2 = new OpenURRoleBuilder("role2")
		.permissions(new HashSet<IPermission>(Arrays.asList(perm21, perm22)))
		.build();
		
		assertFalse(member.hasPermission(app2, perm22));
		assertFalse(member.hasPermission(app2, perm22));
		
		member = new OrgUnitMemberBuilder(person, orgUnit)
	  	.roles(Arrays.asList(role1, role2))
	  	.build();
		assertTrue(member.hasPermission(app2, perm22));
		assertTrue(member.hasPermission(app2, perm22));
	}
}
