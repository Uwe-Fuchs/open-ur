package org.openur.module.domain.security.authorization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
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
		Person pers1 = new PersonBuilder(name)
			.build();
		
		Person pers2 = new PersonBuilder(name)
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		
		AuthorizableMember m1 = new AuthorizableMember(pers1, OU_ID);
		AuthorizableMember m2 = new AuthorizableMember(pers2, OU_ID);
		
		final String NAME_STAFF_DEPARTMENT = "staff department";
		AuthorizableOrgUnit ou = new AuthorizableOrgUnit.AuthorizableOrgUnitBuilder(OU_ID)
			.number("123abc")
			.status(Status.ACTIVE)
			.name(NAME_STAFF_DEPARTMENT)
			.shortName("stf")
			.authorizableMembers(Arrays.asList(m1, m2))
			.build();
		
		assertEquals(NAME_STAFF_DEPARTMENT, ou.getName());
		assertEquals(m1, ou.findMember(m1.getPerson()));
		assertEquals(m1, ou.findAuthorizableMember(m1.getPerson().getIdentifier()));
		assertEquals(m2, ou.findMember(m2.getPerson()));
		assertEquals(m2, ou.findAuthorizableMember(m2.getPerson().getIdentifier()));
		
		Person pers3 = new PersonBuilder(name)
			.build();
		AuthorizableMember m3 = new AuthorizableMember(pers3, OU_ID);
		
		assertNull(ou.findMember(m3.getPerson()));
		assertNull(ou.findAuthorizableMember(m3.getPerson().getIdentifier()));
		
		ou = new AuthorizableOrgUnit.AuthorizableOrgUnitBuilder(OU_ID)
			.number("123abc")
			.status(Status.ACTIVE)
			.name("staff department")
			.shortName("stf")
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
		
		Person person = new PersonBuilder(name)
			.apps(new HashSet<OpenURApplication>(Arrays.asList(app1)))
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();		
		AuthorizableMember member = new AuthorizableMember(person, OU_ID,Arrays.asList(role1));
		
		AuthorizableOrgUnit ou = new AuthorizableOrgUnit.AuthorizableOrgUnitBuilder(OU_ID)
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
		AuthorizableOrgUnit ou = new AuthorizableOrgUnit.AuthorizableOrgUnitBuilder()
			.build();
		
		assertTrue(ou.getMembers().isEmpty());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateWithEmptyMembersList()
	{
		new AuthorizableOrgUnit.AuthorizableOrgUnitBuilder()
			.authorizableMembers(new ArrayList<AuthorizableMember>(0))
			.build();
	}
}
