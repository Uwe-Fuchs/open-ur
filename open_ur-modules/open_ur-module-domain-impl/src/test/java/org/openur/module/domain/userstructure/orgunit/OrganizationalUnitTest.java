package org.openur.module.domain.userstructure.orgunit;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.domain.userstructure.person.Title;

public class OrganizationalUnitTest
{
	@Test
	public void testCompareTo()
	{
		OrganizationalUnitBuilder oub = new OrganizationalUnitBuilder()
			.number("123abc")
			.status(Status.ACTIVE)
			.name("staff department")
			.shortName("stf");
		OrganizationalUnit ou = oub.build();
		
		oub = new OrganizationalUnitBuilder(UUID.randomUUID().toString())
			.number("456xyz")
			.status(Status.ACTIVE)
			.name("accounts department")
			.shortName("acc");
		OrganizationalUnit ou2 = oub.build();
		
		assertTrue("ou1 should be after ou2 because of name", ou.compareTo(ou2) > 0);
		
		oub = new OrganizationalUnitBuilder()
			.number("456xyz")
			.status(Status.ACTIVE)
			.name("staff department")
			.shortName("stf2");
		ou2 = oub.build();
		
		assertTrue("ou1 should be before ou2 because of short-name", ou.compareTo(ou2) < 0);
		
		oub = new OrganizationalUnitBuilder(UUID.randomUUID().toString())
			.number("456xyz")
			.status(Status.ACTIVE)
			.name("staff department")
			.shortName("stf");
		ou2 = oub.build();
		
		assertTrue("ou1 should be before ou2 because of org-unit-number", ou.compareTo(ou2) < 0);
	
		oub = new OrganizationalUnitBuilder(UUID.randomUUID().toString())
			.number("456xyz")
			.status(Status.ACTIVE);
		ou = oub.build();
	
		OrgUnitSimpleBuilder soub = new OrgUnitSimpleBuilder()
			.number("123abc")
			.status(Status.ACTIVE);
		OrgUnitSimple sou = soub.build();
	
		assertTrue("ou should be after ou-simple because of org-unit-number", ou.compareTo(sou) > 0);
	}

	@Test
	public void testIsRootOrgUnit()
	{
		OrganizationalUnit rootOu = new OrganizationalUnitBuilder()
			.build();
		assertTrue(rootOu.isRootOrgUnit());
		
		OrganizationalUnit superOu = new OrganizationalUnitBuilder(rootOu)
		  .superOrgUnit(rootOu)
			.build();
		
		OrganizationalUnit ou = new OrganizationalUnitBuilder(rootOu)
			.superOrgUnit(superOu)
			.build();
		assertFalse(ou.isRootOrgUnit());
	}
	
	@Test
	public void testFindMember()
	{
		Person pers1 = new PersonBuilder(Name.create(Gender.MALE, "Barack", "Obama"))
			.build();
		
		Person pers2 = new PersonBuilder(Name.create(Gender.FEMALE, Title.DR, "Angela", "Merkel"))
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		
		OrgUnitMember m2 = new OrgUnitMember(pers2, OU_ID);
		OrgUnitMember m1 = new OrgUnitMember(pers1, OU_ID);		
		
		OrganizationalUnitBuilder ouBuilder = new OrganizationalUnitBuilder(OU_ID)
			.orgUnitMembers(Arrays.asList(m1, m2));

		OrganizationalUnit ou = ouBuilder.build();
		
		assertEquals(m1, ou.findMember(m1.getPerson()));
		assertEquals(m1, ou.findMember(m1.getPerson().getIdentifier()));
		assertEquals(m2, ou.findMember(m2.getPerson()));
		assertEquals(m2, ou.findMember(m2.getPerson().getIdentifier()));
		
		Person pers3 = new PersonBuilder(Name.create(Gender.MALE, "Francois", "Hollande"))
			.build();
		OrgUnitMember m3 = new OrgUnitMember(pers3, OU_ID);
		
		assertNull(ou.findMember(m3.getPerson()));
		assertNull(ou.findMember(m3.getPerson().getIdentifier()));
		
		ouBuilder.addMember(m3);		
		ou = ouBuilder.build();
		assertEquals(m3, ou.findMember(m3.getPerson()));
		assertEquals(m3, ou.findMember(m3.getPerson().getIdentifier()));
	}
}
