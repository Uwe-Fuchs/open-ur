package org.openur.module.domain.userstructure.orgunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURPermissionBuilder;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.security.authorization.OpenURRoleBuilder;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.InconsistentHierarchyException;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember.OrgUnitMemberBuilder;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.util.data.Gender;
import org.openur.module.util.data.Status;
import org.openur.module.util.data.Title;
import org.openur.module.util.exception.OpenURRuntimeException;

public class OrganizationalUnitTest
{
	@Test
	public void testCompareTo()
	{
		OrgUnitFull ou1 = new OrgUnitFullBuilder("numberOu1", "ou")
			.build();		
		
		OrgUnitFull ou2 = new OrgUnitFullBuilder("numberOu2", "ou")
			.build();
		
		assertTrue("ou1 should be before ou2 because of ou-numbers", ou1.compareTo(ou2) < 0);
		
		
		ou2 = new OrgUnitFullBuilder("numberOu1",  "ou")
			.status(Status.INACTIVE)
			.build();
	
		assertTrue("ou1 should be before ou2 because of status", ou1.compareTo(ou2) < 0);
		
	
		ou1 = new OrgUnitFullBuilder("numberOu1", "staff department")
			.build();
		
		ou2 = new OrgUnitFullBuilder("numberOu2", "accounts department")
			.build();
		
		assertTrue("ou1 should be after ou2 because of name", ou1.compareTo(ou2) > 0);
		
		
		ou1 = new OrgUnitFullBuilder("123abc", "staff department")
			.shortName("stf1")
			.build();
		ou2 = new OrgUnitFullBuilder("123abc", "staff department")
			.shortName("stf2")
			.build();
		
		assertTrue("ou1 should be before ou2 because of short-name", ou1.compareTo(ou2) < 0);
	}
	
	@Test
	public void testEquals()
	{
		final String IDENTIFIER_1 = "id1";
		final String IDENTIFIER_2 = "id2";
		
		OrgUnitFull orgUnit = new OrgUnitFullBuilder("numberOu1", "ou")
				.identifier(IDENTIFIER_1)
				.build();		
		
		Address address = new Address.AddressBuilder("111")
				.identifier(IDENTIFIER_1)
				.build();
		
		assertEquals(orgUnit, address);
		
		Person person = new PersonBuilder("number1", Name.create(null, null, "lastName1"))
				.identifier(IDENTIFIER_1)
				.build();
		
		assertEquals(person, address);
		assertEquals(person, orgUnit);
		
		person = new PersonBuilder("number2", Name.create(null, null, "lastName2"))
			.identifier(IDENTIFIER_2)
			.build();
		
		assertFalse(person.equals(address));
		assertFalse(person.equals(orgUnit));
	}

	@Test
	public void testIsRootOrgUnit()
	{
		OrgUnitFull rootOu = new OrgUnitFullBuilder("rootOuNumber", "rootOu")
			.build();
		assertTrue(rootOu.isRootOrgUnit());
		
		OrgUnitFull ou = new OrgUnitFullBuilder("ouNumber", "ou")
			.rootOrgUnit(rootOu)
		  .superOrgUnit(rootOu)
			.build();
		assertFalse(ou.isRootOrgUnit());
		
		OrgUnitFull _ou = ou.getRootOrgUnit();
		assertTrue(_ou.isRootOrgUnit());
		
		_ou = ou.getSuperOrgUnit();
		assertTrue(_ou.isRootOrgUnit());
	}

	@Test
	public void testIsMember()
	{
		Person pers1 = new PersonBuilder("numberPers1", Name.create(Gender.MALE, "Barack", "Obama"))
			.build();
		
		Person pers2 = new PersonBuilder("numberPers2", Name.create(Gender.FEMALE, Title.DR, "Angela", "Merkel"))
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		
		OrgUnitMember m2 = new OrgUnitMemberBuilder(pers2, OU_ID).build();
		OrgUnitMember m1 = new OrgUnitMemberBuilder(pers1, OU_ID).build();	
		
		OrgUnitFull ou = new OrgUnitFullBuilder("ouNumber", "ou")
			.identifier(OU_ID)
			.members(Arrays.asList(m1, m2))
			.build();
		
		Set<OrgUnitMember> _members = ou.getMembers();
		assertNotNull(_members);
		assertEquals(_members.size(), 2);
		
		assertTrue(ou.isMember(m1.getPerson()));
		assertTrue(ou.isMember(m1.getPerson().getIdentifier()));
		assertTrue(ou.isMember(m2.getPerson()));
		assertTrue(ou.isMember(m2.getPerson().getIdentifier()));
		
		Person pers3 = new PersonBuilder("numberPers3", Name.create(Gender.MALE, "Francois", "Hollande"))
			.build();
		OrgUnitMember m3 = new OrgUnitMemberBuilder(pers3, OU_ID).build();
		
		assertFalse(ou.isMember(m3.getPerson()));
		assertFalse(ou.isMember(m3.getPerson().getIdentifier()));
		
		ou = new OrgUnitFullBuilder("ouNumber", "ou")
			.identifier(OU_ID)
			.members(Arrays.asList(m1, m2, m3))
			.build();
		
		assertTrue(ou.isMember(m3.getPerson()));
		assertTrue(ou.isMember(m3.getPerson().getIdentifier()));
	}
	
	@Test(expected=OpenURRuntimeException.class)
	public void testCreateWithWrongOrgUnitID()
	{
		Person pers = new PersonBuilder("numberPers1", Name.create(Gender.MALE, "Barack", "Obama"))
			.build();
		final String OU_ID_1 = UUID.randomUUID().toString();
		OrgUnitMember member = new OrgUnitMemberBuilder(pers, OU_ID_1).build();
		
		final String OU_ID_2 = UUID.randomUUID().toString();
		OrgUnitFullBuilder oub = new OrgUnitFullBuilder("ouNumber", "ou").identifier(OU_ID_2);
		oub.members(Arrays.asList(member));
	}
	
	@Test
	public void testCreateWithoutMembers()
	{
		OrgUnitFull ou = new OrgUnitFullBuilder("ouNumber", "ou")
			.build();
		
		assertTrue(ou.getMembers().isEmpty());
	}
	
	@Test
	public void testFindMember()
	{
		Person pers1 = new PersonBuilder("numberPers1", Name.create(Gender.MALE, "Barack", "Obama"))
			.build();
		
		Person pers2 = new PersonBuilder("numberPers2", Name.create(Gender.FEMALE, Title.DR, "Angela", "Merkel"))
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		
		OrgUnitMember m1 = new OrgUnitMemberBuilder(pers1, OU_ID).build();
		OrgUnitMember m2 = new OrgUnitMemberBuilder(pers2, OU_ID).build();
		
		final String NAME_STAFF_DEPARTMENT = "staff department";
		OrgUnitFullBuilder oub = new OrgUnitFullBuilder("123abc", NAME_STAFF_DEPARTMENT)
			.identifier(OU_ID)
			.status(Status.ACTIVE)
			.shortName("stf")
			.description("description_123abc")
			.members(Arrays.asList(m1, m2));
		
		OrgUnitFull ou = oub.build();
		
		assertEquals(NAME_STAFF_DEPARTMENT, ou.getName());
		OrgUnitMember _m1 = ou.findMemberByPerson(m1.getPerson());
		assertEquals(m1, _m1);
		assertEquals(m1, ou.findMemberByPersonId(m1.getPerson().getIdentifier()));
		assertEquals(m2, ou.findMemberByPerson(m2.getPerson()));
		assertEquals(m2, ou.findMemberByPersonId(m2.getPerson().getIdentifier()));
		
		Person pers3 = new PersonBuilder("numberPers3", Name.create(Gender.MALE, "Francois", "Hollande"))
			.build();
		OrgUnitMember m3 = new OrgUnitMemberBuilder(pers3, OU_ID).build();
		
		assertNull(ou.findMemberByPerson(m3.getPerson()));
		assertNull(ou.findMemberByPersonId(m3.getPerson().getIdentifier()));
		
		oub.addMember(m3);		
		ou = oub.build();

		assertEquals(m3, ou.findMemberByPerson(m3.getPerson()));
		assertEquals(m3, ou.findMemberByPersonId(m3.getPerson().getIdentifier()));
	}

	@Test
	public void testHasPermission()
	{
		OpenURApplication app1 = new OpenURApplicationBuilder("app1")
			.build();		
		OpenURPermission perm11 = new OpenURPermissionBuilder("perm11", app1)
			.build();	
		OpenURRoleBuilder roleBuilder = new OpenURRoleBuilder("role1");
		OpenURRole role1 = roleBuilder
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm11)), roleBuilder)
			.build();
		
		Person person = new PersonBuilder("numberPers1", Name.create(Gender.MALE, "Barack", "Obama"))
			.applications(new HashSet<OpenURApplication>(Arrays.asList(app1)))
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();		
		OrgUnitMember member = new OrgUnitMemberBuilder(person, OU_ID)
			.addRole(role1)
			.build();
		
		OrgUnitFull ou = new OrgUnitFullBuilder("ouNumber", "ou")
			.identifier(OU_ID)
			.members(Arrays.asList(member))
			.build();

		// has permission:		
		assertTrue(ou.hasPermission(person, perm11));
	
		// doesn't have permission:
		OpenURPermission perm12 = new OpenURPermissionBuilder("perm12", app1)
			.build();
		
		assertFalse(ou.hasPermission(person, perm12));
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateWithNullMembersList()
	{
		new OrgUnitFullBuilder("ouNumber", "ou")
			.members(null);
	}
	
	@Test(expected=InconsistentHierarchyException.class)
	public void testCreateAsRootWithSuperOuId()
	{
		OrgUnitFull ou = new OrgUnitFullBuilder("ouNumber", "ou")
			.build();
		
		new OrgUnitFullBuilder("superOuNumber", "superOu")
			.superOrgUnit(ou)
			.build();
	}
	
	@Test(expected=InconsistentHierarchyException.class)
	public void testCreateAsNonRootWithoutSuperOuId()
	{
		OrgUnitFull rootOu = new OrgUnitFullBuilder("rootOuNumber", "rootOu")
			.build();
		
		new OrgUnitFullBuilder("ouNumber", "ou")
			.rootOrgUnit(rootOu)
			.build();
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateWithEmptySuperOu()
	{
		OrgUnitFull rootOu = new OrgUnitFullBuilder("rootOuNumber", "rootOu")
			.build();
		
		new OrgUnitFullBuilder("ouNumber", "ou")
			.rootOrgUnit(rootOu)
			.superOrgUnit(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateAsNonRootWithEmptyRoot()
	{
		new OrgUnitFullBuilder("ouNumber", "ou").rootOrgUnit(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateWithEmptyNumber()
	{
		new OrgUnitFullBuilder().name("someName").build();
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateWithEmptyName()
	{
		new OrgUnitFullBuilder().number("someNumber").build();
	}
	
	@Test(expected=NullPointerException.class)
	public void checkEmptyNumber()
	{
		new OrgUnitFullBuilder(null, "someName").build();
	}
	
	@Test(expected=NullPointerException.class)
	public void checkEmptyName()
	{
		new OrgUnitFullBuilder("someNumber", null).build();
	}
}
