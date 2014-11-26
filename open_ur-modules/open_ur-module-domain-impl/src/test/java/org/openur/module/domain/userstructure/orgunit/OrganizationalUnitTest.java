package org.openur.module.domain.userstructure.orgunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.userstructure.InconsistentHierarchyException;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.domain.userstructure.person.Title;
import org.openur.module.util.exception.OpenURRuntimeException;

import static org.openur.module.domain.userstructure.orgunit.OrgUnitMember.OrgUnitMemberBuilder;

public class OrganizationalUnitTest
{
	@Test
	public void testCompareTo()
	{
		OrganizationalUnit ou1 = new OrganizationalUnitBuilder("numberOu1", "ou")
			.build();		
		
		OrganizationalUnit ou2 = new OrganizationalUnitBuilder("numberOu2", "ou")
			.build();
		
		assertTrue("ou1 should be before ou2 because of ou-numbers", ou1.compareTo(ou2) < 0);
		
		
		ou2 = new OrganizationalUnitBuilder("numberOu1",  "ou")
			.status(Status.INACTIVE)
			.build();
	
		assertTrue("ou1 should be before ou2 because of status", ou1.compareTo(ou2) < 0);
		
	
		ou1 = new OrganizationalUnitBuilder("numberOu1", "staff department")
			.build();
		
		ou2 = new OrganizationalUnitBuilder("numberOu2", "accounts department")
			.build();
		
		assertTrue("ou1 should be after ou2 because of name", ou1.compareTo(ou2) > 0);
		
		
		ou1 = new OrganizationalUnitBuilder("123abc", "staff department")
			.shortName("stf1")
			.build();
		ou2 = new OrganizationalUnitBuilder("123abc", "staff department")
			.shortName("stf2")
			.build();
		
		assertTrue("ou1 should be before ou2 because of short-name", ou1.compareTo(ou2) < 0);
	}

	@Test
	public void testIsRootOrgUnit()
	{
		OrganizationalUnit rootOu = new OrganizationalUnitBuilder("rootOuNumber", "rootOu")
			.build();
		assertTrue(rootOu.isRootOrgUnit());
		
		OrganizationalUnit ou = new OrganizationalUnitBuilder("ouNumber", "ou")
			.rootOrgUnit(rootOu)
		  .superOrgUnit(rootOu)
			.build();
		assertFalse(ou.isRootOrgUnit());
	}
	
	@Test
	public void testFindMember()
	{
		Person pers1 = new PersonBuilder("numberPers1", Name.create(Gender.MALE, "Barack", "Obama"))
			.build();
		
		Person pers2 = new PersonBuilder("numberPers2", Name.create(Gender.FEMALE, Title.DR, "Angela", "Merkel"))
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		
		OrgUnitMember m2 = new OrgUnitMemberBuilder(pers2, OU_ID).build();
		OrgUnitMember m1 = new OrgUnitMemberBuilder(pers1, OU_ID).build();		
		
		OrganizationalUnitBuilder ouBuilder = new OrganizationalUnitBuilder("ouNumber", "ou")
			.identifier(OU_ID)
			.orgUnitMembers(Arrays.asList(m1, m2));

		OrganizationalUnit ou = ouBuilder.build();
		
		assertEquals(m1, ou.findMember(m1.getPerson()));
		assertEquals(m1, ou.findMember(m1.getPerson().getIdentifier()));
		assertEquals(m2, ou.findMember(m2.getPerson()));
		assertEquals(m2, ou.findMember(m2.getPerson().getIdentifier()));
		
		Person pers3 = new PersonBuilder("numberPers3", Name.create(Gender.MALE, "Francois", "Hollande"))
			.build();
		OrgUnitMember m3 = new OrgUnitMemberBuilder(pers3, OU_ID).build();
		
		assertNull(ou.findMember(m3.getPerson()));
		assertNull(ou.findMember(m3.getPerson().getIdentifier()));
		
		ouBuilder.addMember(m3);		
		ou = ouBuilder.build();
		assertEquals(m3, ou.findMember(m3.getPerson()));
		assertEquals(m3, ou.findMember(m3.getPerson().getIdentifier()));
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
		
		OrganizationalUnit ou = new OrganizationalUnitBuilder("ouNumber", "ou")
			.identifier(OU_ID)
			.orgUnitMembers(Arrays.asList(m1, m2))
			.build();
		
		assertTrue(ou.isMember(m1.getPerson()));
		assertTrue(ou.isMember(m1.getPerson().getIdentifier()));
		assertTrue(ou.isMember(m2.getPerson()));
		assertTrue(ou.isMember(m2.getPerson().getIdentifier()));
		
		Person pers3 = new PersonBuilder("numberPers3", Name.create(Gender.MALE, "Francois", "Hollande"))
			.build();
		OrgUnitMember m3 = new OrgUnitMemberBuilder(pers3, OU_ID).build();
		
		assertFalse(ou.isMember(m3.getPerson()));
		assertFalse(ou.isMember(m3.getPerson().getIdentifier()));
		
		ou = new OrganizationalUnitBuilder("ouNumber", "ou")
			.identifier(OU_ID)
			.orgUnitMembers(Arrays.asList(m1, m2, m3))
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
		OrganizationalUnitBuilder oub = new OrganizationalUnitBuilder("ouNumber", "ou").identifier(OU_ID_2);
		oub.orgUnitMembers(Arrays.asList(member));
	}
	
	@Test
	public void testCreateWithNullMembers()
	{
		OrganizationalUnit ou = new OrganizationalUnitBuilder("ouNumber", "ou")
			.build();
		
		assertTrue(ou.getMembers().isEmpty());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateWithEmptyMembersList()
	{
		new OrganizationalUnitBuilder("ouNumber", "ou")
			.orgUnitMembers(new ArrayList<OrgUnitMember>(0))
			.build();
	}
	
	@Test(expected=InconsistentHierarchyException.class)
	public void testCreateAsRootWithSuperOuId()
	{
		OrganizationalUnit ou = new OrganizationalUnitBuilder("ouNumber", "ou")
			.build();
		
		new OrganizationalUnitBuilder("superOuNumber", "superOu")
			.superOrgUnit(ou)
			.build();
	}
	
	@Test(expected=InconsistentHierarchyException.class)
	public void testCreateAsNonRootWithoutSuperOuId()
	{
		OrganizationalUnit rootOu = new OrganizationalUnitBuilder("rootOuNumber", "rootOu")
			.build();
		
		new OrganizationalUnitBuilder("ouNumber", "ou")
			.rootOrgUnit(rootOu)
			.build();
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateWithEmptySuperOu()
	{
		OrganizationalUnit rootOu = new OrganizationalUnitBuilder("rootOuNumber", "rootOu")
			.build();
		
		new OrganizationalUnitBuilder("ouNumber", "ou")
			.rootOrgUnit(rootOu)
			.superOrgUnit(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateAsNonRootWithEmptyRoot()
	{
		new OrganizationalUnitBuilder("ouNumber", "ou").rootOrgUnit(null);
	}
}
