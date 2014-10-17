package org.openur.module.domain.security.authorization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.userstructure.person.PersonSimple;
import org.openur.module.domain.userstructure.person.PersonSimpleBuilder;

public class AuthOrgUnitSimpleTest
{
	@Test
	public void testFindAuthorizableMember()
	{
		PersonSimple pers1 = new PersonSimpleBuilder()
			.build();
		
		PersonSimple pers2 = new PersonSimpleBuilder()
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		
		AuthorizableMember m1 = new AuthorizableMember(pers1, OU_ID);
		AuthorizableMember m2 = new AuthorizableMember(pers2, OU_ID);
		
		AuthOrgUnitSimple ou = new AuthOrgUnitSimple.AuthOrgUnitSimpleBuilder(OU_ID)
			.authorizableMembers(Arrays.asList(m1, m2))
			.build();
		
		assertEquals(m1, ou.findMember(m1.getPerson()));
		assertEquals(m1, ou.findAuthorizableMember(m1.getPerson().getIdentifier()));
		assertEquals(m2, ou.findMember(m2.getPerson()));
		assertEquals(m2, ou.findAuthorizableMember(m2.getPerson().getIdentifier()));
		
		PersonSimple pers3 = new PersonSimpleBuilder()
			.build();
		AuthorizableMember m3 = new AuthorizableMember(pers3, OU_ID);
		
		assertNull(ou.findMember(m3.getPerson()));
		assertNull(ou.findAuthorizableMember(m3.getPerson().getIdentifier()));
		
		ou = new AuthOrgUnitSimple.AuthOrgUnitSimpleBuilder(OU_ID)
			.authorizableMembers(Arrays.asList(m1, m2, m3))
			.build();

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
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm11)))
			.build();
		
		PersonSimple person = new PersonSimpleBuilder()
			.apps(new HashSet<OpenURApplication>(Arrays.asList(app1)))
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();		
		AuthorizableMember member = new AuthorizableMember(person, OU_ID, Arrays.asList(role1));
		AuthOrgUnitSimple ou = new AuthOrgUnitSimple.AuthOrgUnitSimpleBuilder(OU_ID)
			.authorizableMembers(Arrays.asList(member))
			.build();
		
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
		AuthOrgUnitSimple ou = new AuthOrgUnitSimple.AuthOrgUnitSimpleBuilder()
			.build();
		
		assertTrue(ou.getMembers().isEmpty());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateWithEmptyMembersList()
	{
		new AuthOrgUnitSimple.AuthOrgUnitSimpleBuilder()
			.authorizableMembers(new ArrayList<AuthorizableMember>(0))
			.build();
	}
}
