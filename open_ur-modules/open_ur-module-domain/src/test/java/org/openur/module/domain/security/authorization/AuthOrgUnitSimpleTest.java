package org.openur.module.domain.security.authorization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.userstructure.orgunit.OrgUnitSimpleBuilder;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.PersonSimpleBuilder;

public class AuthOrgUnitSimpleTest
{
	@Test
	public void testFindAuthorizableMember()
	{
		IPerson pers1 = new PersonSimpleBuilder()
			.build();
		
		IPerson pers2 = new PersonSimpleBuilder()
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		
		IAuthorizableMember m1 = new AuthorizableMember(pers1, OU_ID);
		IAuthorizableMember m2 = new AuthorizableMember(pers2, OU_ID);
		
		IAuthorizableOrgUnit ou = new AuthOrgUnitSimple(
			new OrgUnitSimpleBuilder(OU_ID), Arrays.asList(m1, m2));
		
		assertEquals(m1, ou.findMember(m1.getPerson()));
		assertEquals(m1, ou.findAuthorizableMember(m1.getPerson().getIdentifier()));
		assertEquals(m2, ou.findMember(m2.getPerson()));
		assertEquals(m2, ou.findAuthorizableMember(m2.getPerson().getIdentifier()));
		
		IPerson pers3 = new PersonSimpleBuilder()
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
		OpenURApplication app1 = new OpenURApplicationBuilder("app1")
			.build();		
		OpenURPermission perm11 = new OpenURPermissionBuilder("perm11", PermissionScope.SELECTED,  app1)
			.build();		
		OpenURRole role1 = new OpenURRoleBuilder("role1")
			.permissions(new HashSet<IPermission>(Arrays.asList(perm11)))
			.build();
		
		IPerson person = new PersonSimpleBuilder()
			.apps(new HashSet<OpenURApplication>(Arrays.asList(app1)))
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();		
		IAuthorizableMember member = new AuthorizableMember(person, OU_ID,Arrays.asList(role1));
		IAuthorizableOrgUnit ou = new AuthOrgUnitSimple(
			new OrgUnitSimpleBuilder(OU_ID), Arrays.asList(member));
		
		// has permission:		
		assertTrue(ou.hasPermission(person, app1, perm11));
	
		// doesn't have permission:
		OpenURPermission perm12 = new OpenURPermissionBuilder("perm12", PermissionScope.SELECTED_SUB,  app1)
			.build();
		
		assertFalse(ou.hasPermission(person, app1, perm12));
	}
	
	@Test
	public void testCreateWithNullMembers()
	{
		IAuthorizableOrgUnit ou = new AuthOrgUnitSimple(
			new OrgUnitSimpleBuilder(), null);
		
		assertTrue(ou.getMembers().isEmpty());
	}
}
