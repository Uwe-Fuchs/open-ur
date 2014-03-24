package org.openur.module.domain.userstructure.orgunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.user.person.IPerson;
import org.openur.module.domain.userstructure.user.person.PersonBuilder;
import org.openur.module.util.exception.OpenURRuntimeException;

public class OrgUnitSimpleTest
{
	@Test
	public void testFindMember()
	{
		PersonBuilder persBuilder = new PersonBuilder("username1", "password1")
			.number("123abc")
			.status(Status.ACTIVE);
		IPerson pers1 = persBuilder.build();
		
		persBuilder = new PersonBuilder("username2", "password2")
			.number("456xyz")
			.status(Status.ACTIVE);
		IPerson pers2 = persBuilder.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		final String OU_NUMBER = "123abc";
		final Status OU_STATUS = Status.ACTIVE;
		OrgUnitDelegateBuilder oudb = new OrgUnitDelegateBuilder(OU_ID)
			.number(OU_NUMBER)
			.status(OU_STATUS);
		IOrganizationalUnit oud = new OrgUnitDelegate(oudb);
		
		IOrgUnitMember m2 = new OrgUnitMember(new OrgUnitMemberBuilder(pers2, oud));
		IOrgUnitMember m1 = new OrgUnitMember(new OrgUnitMemberBuilder(pers1, oud));		

		OrgUnitSimpleBuilder oub = new OrgUnitSimpleBuilder(OU_ID)
			.number(OU_NUMBER)
			.status(OU_STATUS)
			.members(Arrays.asList(m1, m2));
		IOrganizationalUnit ou = new OrgUnitSimple(oub);
		
		assertEquals(m1, ou.findMember(m1.getPerson()));
		assertEquals(m1, ou.findMember(m1.getPerson().getIdentifier()));
		assertEquals(m2, ou.findMember(m2.getPerson()));
		assertEquals(m2, ou.findMember(m2.getPerson().getIdentifier()));
		
		persBuilder = new PersonBuilder("username3", "password3")
			.number("789äöü")
			.status(Status.ACTIVE);
		IPerson pers3 = persBuilder.build();
		IOrgUnitMember m3 = new OrgUnitMember(new OrgUnitMemberBuilder(pers3, oud));
		
		assertNull(ou.findMember(m3.getPerson()));
		assertNull(ou.findMember(m3.getPerson().getIdentifier()));
		
		oub.members(Arrays.asList(m1, m2, m3));
		ou = new OrgUnitSimple(oub);
		assertEquals(m3, ou.findMember(m3.getPerson()));
		assertEquals(m3, ou.findMember(m3.getPerson().getIdentifier()));
	}

	@Test
	public void testIsMember()
	{
		PersonBuilder persBuilder = new PersonBuilder("username1", "password1")
			.number("123abc")
			.status(Status.ACTIVE);
		IPerson pers1 = persBuilder.build();
	
		persBuilder = new PersonBuilder("username2", "password2")
			.number("456xyz")
			.status(Status.ACTIVE);
		IPerson pers2 = persBuilder.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		final String OU_NUMBER = "123abc";
		final Status OU_STATUS = Status.ACTIVE;
		OrgUnitDelegateBuilder oudb = new OrgUnitDelegateBuilder(OU_ID)
			.number(OU_NUMBER)
			.status(OU_STATUS);
		IOrganizationalUnit oud = new OrgUnitDelegate(oudb);
		
		IOrgUnitMember m2 = new OrgUnitMember(new OrgUnitMemberBuilder(pers2, oud));
		IOrgUnitMember m1 = new OrgUnitMember(new OrgUnitMemberBuilder(pers1, oud));		
	
		OrgUnitSimpleBuilder oub = new OrgUnitSimpleBuilder(OU_ID)
			.number(OU_NUMBER)
			.status(OU_STATUS)
			.members(Arrays.asList(m1, m2));
		IOrganizationalUnit ou = new OrgUnitSimple(oub);
		
		assertTrue(ou.isMember(m1.getPerson()));
		assertTrue(ou.isMember(m1.getPerson().getIdentifier()));
		assertTrue(ou.isMember(m2.getPerson()));
		assertTrue(ou.isMember(m2.getPerson().getIdentifier()));
		
		persBuilder = new PersonBuilder("username3", "password3")
			.number("789äöü")
			.status(Status.ACTIVE);
		IPerson pers3 = persBuilder.build();
		IOrgUnitMember m3 = new OrgUnitMember(new OrgUnitMemberBuilder(pers3, oud));
		
		assertFalse(ou.isMember(m3.getPerson()));
		assertFalse(ou.isMember(m3.getPerson().getIdentifier()));
		
		oub.members(Arrays.asList(m1, m2, m3));
		ou = new OrgUnitSimple(oub);		
		assertTrue(ou.isMember(m3.getPerson()));
		assertTrue(ou.isMember(m3.getPerson().getIdentifier()));
	}
	
	@Test(expected=OpenURRuntimeException.class)
	public void checkWrongOrgUnitID()
	{
		IPerson pers = new PersonBuilder("username1", "password1").build();
		IOrganizationalUnit oud = new OrgUnitDelegate(new OrgUnitDelegateBuilder("123"));
		IOrgUnitMember member = new OrgUnitMember(new OrgUnitMemberBuilder(pers, oud));
		
		OrgUnitSimpleBuilder oub = new OrgUnitSimpleBuilder("456");
		oub.members(Arrays.asList(member));
	}

	@Test
	public void testCompareTo()
	{
		OrgUnitSimpleBuilder oub = new OrgUnitSimpleBuilder()
			.number("123abc")
			.status(Status.ACTIVE);
		IOrganizationalUnit ou = new OrgUnitSimple(oub);
		
		oub = new OrgUnitSimpleBuilder(UUID.randomUUID().toString())
			.number("456xyz")
			.status(Status.ACTIVE)
			.superOuId(ou.getIdentifier());
		IOrganizationalUnit ou2 = new OrgUnitSimple(oub);
		
		assertTrue("ou1 should be before ou2 because of ou-numbers", ou.compareTo(ou2) < 0);
		
		oub = new OrgUnitSimpleBuilder()
			.number("123abc")
			.status(Status.INACTIVE);
		ou2 = new OrgUnitSimple(oub);
		
		assertTrue("ou1 should be before ou2 because of status", ou.compareTo(ou2) < 0);
	}
}
