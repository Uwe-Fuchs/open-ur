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
import org.openur.module.domain.security.authorization.AuthorizableMember.AuthorizableMemberBuilder;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit.AuthorizableOrgUnitBuilder;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.domain.userstructure.person.Title;

public class AuthorizableOrgUnitTest
{
	@Test
	public void testFindAuthorizableMember()
	{
		Person pers1 = new PersonBuilder("numberPers1", Name.create(Gender.MALE, "Barack", "Obama"))
			.build();
		
		Person pers2 = new PersonBuilder("numberPers2", Name.create(Gender.FEMALE, Title.DR, "Angela", "Merkel"))
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		
		AuthorizableMember m1 = new AuthorizableMemberBuilder(pers1, OU_ID).build();
		AuthorizableMember m2 = new AuthorizableMemberBuilder(pers2, OU_ID).build();
		
		final String NAME_STAFF_DEPARTMENT = "staff department";
		AuthorizableOrgUnitBuilder oub = new AuthorizableOrgUnitBuilder("123abc", NAME_STAFF_DEPARTMENT)
			.identifier(OU_ID)
			.status(Status.ACTIVE)
			.shortName("stf")
			.description("description_123abc")
			.authorizableMembers(Arrays.asList(m1, m2));
		
		AuthorizableOrgUnit ou = oub.build();
		
		assertEquals(NAME_STAFF_DEPARTMENT, ou.getName());
		AuthorizableMember _m1 = ou.findMember(m1.getPerson());
		assertEquals(m1, _m1);
		assertEquals(m1, ou.findMember(m1.getPerson().getIdentifier()));
		assertEquals(m2, ou.findMember(m2.getPerson()));
		assertEquals(m2, ou.findMember(m2.getPerson().getIdentifier()));
		
		Person pers3 = new PersonBuilder("numberPers3", Name.create(Gender.MALE, "Francois", "Hollande"))
			.build();
		AuthorizableMember m3 = new AuthorizableMemberBuilder(pers3, OU_ID).build();
		
		assertNull(ou.findMember(m3.getPerson()));
		assertNull(ou.findMember(m3.getPerson().getIdentifier()));
		
		oub.addMember(m3);		
		ou = oub.build();

		assertEquals(m3, ou.findMember(m3.getPerson()));
		assertEquals(m3, ou.findMember(m3.getPerson().getIdentifier()));
	}

	@Test
	public void testHasPermission()
	{
		OpenURApplication app1 = new OpenURApplicationBuilder("app1")
			.build();		
		OpenURPermission perm11 = new OpenURPermissionBuilder("perm11", app1)
			.build();		
		OpenURRole role1 = new OpenURRoleBuilder("role1")
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm11)))
			.build();
		
		Person person = new PersonBuilder("numberPers1", Name.create(Gender.MALE, "Barack", "Obama"))
			.applications(new HashSet<OpenURApplication>(Arrays.asList(app1)))
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();		
		AuthorizableMember member = new AuthorizableMemberBuilder(person, OU_ID)
			.addRole(role1)
			.build();
		
		AuthorizableOrgUnit ou = new AuthorizableOrgUnitBuilder("ouNumber", "ou")
			.identifier(OU_ID)
			.authorizableMembers(Arrays.asList(member))
			.build();

		// has permission:		
		assertTrue(ou.hasPermission(person, app1, perm11));
	
		// doesn't have permission:
		OpenURPermission perm12 = new OpenURPermissionBuilder("perm12", app1)
			.build();
		
		assertFalse(ou.hasPermission(person, app1, perm12));
	}
}
