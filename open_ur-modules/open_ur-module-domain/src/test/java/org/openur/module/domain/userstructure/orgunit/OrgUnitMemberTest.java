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
import org.openur.module.domain.userstructure.user.person.PersonBuilder;

public class OrgUnitMemberTest
{
	@Test
	public void testEqualsObject()
	{
		PersonBuilder persBuilder = new PersonBuilder("username1", "password1")
			.number("123abc")
			.status(Status.ACTIVE);
		IPerson pers1 = persBuilder.build();
		OrgUnitSimpleBuilder oub = new OrgUnitSimpleBuilder()
			.number("123ABC")
			.status(Status.ACTIVE);
		IOrganizationalUnit orgUnit1 = new OrgUnitSimple(oub);		
		IOrgUnitMember m11 = new OrgUnitMember(new OrgUnitMemberBuilder(pers1, orgUnit1));
		
		assertTrue(m11.equals(m11));
		
		persBuilder = new PersonBuilder("username2", "password2")
			.number("456xyz")
			.status(Status.ACTIVE);
		IPerson pers2 = persBuilder.build();
		IOrgUnitMember m21 = new OrgUnitMember(new OrgUnitMemberBuilder(pers2, orgUnit1));	
		
		assertFalse(m11.equals(m21));
		assertFalse(m21.equals(m11));
		
		oub = new OrgUnitSimpleBuilder()
			.number("456XYZ")
			.status(Status.ACTIVE);
		IOrganizationalUnit orgUnit2 = new OrgUnitSimple(oub);		
		IOrgUnitMember m12 = new OrgUnitMember(new OrgUnitMemberBuilder(pers1, orgUnit2));
		IOrgUnitMember m22 = new OrgUnitMember(new OrgUnitMemberBuilder(pers2, orgUnit2));
		
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
		PersonBuilder persBuilder = new PersonBuilder("username1", "password1")
			.number("123")
			.status(Status.ACTIVE);
		IPerson pers1 = persBuilder.build();
		OrgUnitSimpleBuilder oub = new OrgUnitSimpleBuilder()
			.number("XYZ")
			.status(Status.ACTIVE);
		IOrganizationalUnit orgUnit1 = new OrgUnitSimple(oub);		
		IOrgUnitMember m11 = new OrgUnitMember(new OrgUnitMemberBuilder(pers1, orgUnit1));
		
		assertTrue(m11.compareTo(m11) == 0);
		
		persBuilder = new PersonBuilder("username2", "password2")
			.number("456")
			.status(Status.ACTIVE);
		IPerson pers2 = persBuilder.build();
		IOrgUnitMember m21 = new OrgUnitMember(new OrgUnitMemberBuilder(pers2, orgUnit1));
		
		assertTrue(m11.compareTo(m21) < 0);
		
		oub = new OrgUnitSimpleBuilder()
			.number("ABC")
			.status(Status.ACTIVE);
		IOrganizationalUnit orgUnit2 = new OrgUnitSimple(oub);		
		IOrgUnitMember m12 = new OrgUnitMember(new OrgUnitMemberBuilder(pers1, orgUnit2));
		IOrgUnitMember m22 = new OrgUnitMember(new OrgUnitMemberBuilder(pers2, orgUnit2));
	
		assertTrue(m11.compareTo(m12) > 0);
		assertTrue(m11.compareTo(m22) > 0);
		assertTrue(m12.compareTo(m21) < 0);
		assertTrue(m12.compareTo(m22) < 0);
		assertTrue(m21.compareTo(m22) > 0);
	}

	@Test
	public void testHasRole()
	{
		IRole role1 = new OpenURRole(new OpenURRoleBuilder("role1"));		
		PersonBuilder persBuilder = new PersonBuilder("username1", "password1")
			.number("123")
			.status(Status.ACTIVE);
		IPerson pers = persBuilder.build();
		OrgUnitSimpleBuilder ouBuilder = new OrgUnitSimpleBuilder()
			.number("ABC")
			.status(Status.ACTIVE);
		IOrganizationalUnit orgUnit1 = new OrgUnitSimple(ouBuilder);
		OrgUnitMemberBuilder memberBuilder = new OrgUnitMemberBuilder(pers, orgUnit1)
		  .roles(Arrays.asList(role1));
		IOrgUnitMember member = new OrgUnitMember(memberBuilder);		
		assertTrue(member.hasRole(role1));
		
		IRole role2 = new OpenURRole(new OpenURRoleBuilder("role2"));		
		assertFalse(member.hasRole(role2));
		
		memberBuilder = new OrgUnitMemberBuilder(pers, orgUnit1)
	  	.roles(Arrays.asList(role1, role2));
		member = new OrgUnitMember(memberBuilder);
		assertTrue(member.hasRole(role1));
		assertTrue(member.hasRole(role2));
	}

	@Test
	public void testHasPermission()
	{
		IApplication app1 = new OpenURApplication(new OpenURApplicationBuilder("app1", "user1", "pw1"));		
		IPermission perm11 = new OpenURPermission(new OpenURPermissionBuilder("perm11", PermissionScope.SELECTED,  app1));
		IPermission perm12 = new OpenURPermission(new OpenURPermissionBuilder("perm12", PermissionScope.SELECTED_SUB,  app1));		
		OpenURRoleBuilder rb = new OpenURRoleBuilder("role1")
			.permissions(new HashSet<IPermission>(Arrays.asList(perm11, perm12)));
		IRole role1 = new OpenURRole(rb);
		
		IPerson person = new PersonBuilder("username1", "password1").build();
		IOrganizationalUnit orgUnit = new OrgUnitSimple(new OrgUnitSimpleBuilder());
		
		OrgUnitMemberBuilder memberBuilder = new OrgUnitMemberBuilder(person, orgUnit)
		  .roles(Arrays.asList(role1));
		IOrgUnitMember member = new OrgUnitMember(memberBuilder);		
		
		assertTrue(member.hasPermission(app1, perm11));
		assertTrue(member.hasPermission(app1, perm12));
		
		IApplication app2 = new OpenURApplication(new OpenURApplicationBuilder("app2", "user2", "pw2"));		
		IPermission perm21 = new OpenURPermission(new OpenURPermissionBuilder("perm21", PermissionScope.SELECTED,  app2));
		IPermission perm22 = new OpenURPermission(new OpenURPermissionBuilder("perm22", PermissionScope.SUB,  app2));		
		rb = new OpenURRoleBuilder("role2").permissions(new HashSet<IPermission>(Arrays.asList(perm21, perm22)));
		IRole role2 = new OpenURRole(rb);
		
		assertFalse(member.hasPermission(app2, perm22));
		assertFalse(member.hasPermission(app2, perm22));
		
		memberBuilder = new OrgUnitMemberBuilder(person, orgUnit)
	  	.roles(Arrays.asList(role1, role2));
		member = new OrgUnitMember(memberBuilder);
		assertTrue(member.hasPermission(app2, perm22));
		assertTrue(member.hasPermission(app2, perm22));
	}
}
