package org.openur.module.domain.security.authorization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.security.authorization.AuthOrgUnitSimple;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.IAuthorizableMember;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.domain.security.authorization.OpenURPermissionBuilder;
import org.openur.module.domain.security.authorization.OpenURRoleBuilder;
import org.openur.module.domain.security.authorization.PermissionScope;
import org.openur.module.domain.userstructure.orgunit.OrgUnitSimpleBuilder;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnitBuilder;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.PersonBuilder;

public class AuthOrganizationalUnitTest
{
	private Name name;
	
	@Before
	public void setUp()
		throws Exception
	{
		this.name = Name.create(null, null, "Meier");
	}
	
	@Test
	public void testFindAuthorizableMember()
	{
		IPerson pers1 = new PersonBuilder("username1", "password1", name)
			.build();
		
		IPerson pers2 = new PersonBuilder("username2", "password2", name)
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		
		IAuthorizableMember m1 = new AuthorizableMember(pers1, OU_ID);
		IAuthorizableMember m2 = new AuthorizableMember(pers2, OU_ID);
		
		IAuthorizableOrgUnit ou = new AuthorizableOrgUnit(
			new OrganizationalUnitBuilder(OU_ID), Arrays.asList(m1, m2));
		
		assertEquals(m1, ou.findMember(m1.getPerson()));
		assertEquals(m1, ou.findAuthorizableMember(m1.getPerson().getIdentifier()));
		assertEquals(m2, ou.findMember(m2.getPerson()));
		assertEquals(m2, ou.findAuthorizableMember(m2.getPerson().getIdentifier()));
		
		IPerson pers3 = new PersonBuilder("username3", "password3", name)
			.build();
		IAuthorizableMember m3 = new AuthorizableMember(pers3, OU_ID);
		
		assertNull(ou.findMember(m3.getPerson()));
		assertNull(ou.findAuthorizableMember(m3.getPerson().getIdentifier()));
		
		ou = new AuthOrgUnitSimple(
			new OrgUnitSimpleBuilder(OU_ID), Arrays.asList(m1, m2, m3));

		assertEquals(m3, ou.findMember(m3.getPerson()));
		assertEquals(m3, ou.findAuthorizableMember(m3.getPerson().getIdentifier()));
	}

	@Test
	public void testHasPermission()
	{
		IApplication app1 = new OpenURApplicationBuilder("app1")
			.build();		
		IPermission perm11 = new OpenURPermissionBuilder("perm11", PermissionScope.SELECTED,  app1)
			.build();		
		IRole role1 = new OpenURRoleBuilder("role1")
			.permissions(new HashSet<IPermission>(Arrays.asList(perm11)))
			.build();
		
		IPerson person = new PersonBuilder("username1", "password1", name)
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();		
		IAuthorizableMember member = new AuthorizableMember(person, OU_ID,Arrays.asList(role1));		
		IAuthorizableOrgUnit ou = new AuthorizableOrgUnit(
			new OrganizationalUnitBuilder(OU_ID), Arrays.asList(member));
		
		// has permission:		
		assertTrue(ou.hasPermission(person, app1, perm11));
	
		// doesn't have permission:
		IPermission perm12 = new OpenURPermissionBuilder("perm12", PermissionScope.SELECTED_SUB,  app1)
			.build();
		
		assertFalse(ou.hasPermission(person, app1, perm12));
	}
	
	@Test
	public void testCreateWithNullMembers()
	{
		IAuthorizableOrgUnit ou = new AuthorizableOrgUnit(
			new OrganizationalUnitBuilder(), null);
		
		assertTrue(ou.getMembers().isEmpty());
	}
}
