package org.openur.module.domain.userstructure.orgunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.userstructure.Status;

public class OrgUnitSimpleTest
{
	@Test
	public void testFindMember()
	{
		IOrgUnitMember m1 = new OrgUnitMember("personalId1");
		IOrgUnitMember m2 = new OrgUnitMember("personalId2");
		
		OrgUnitSimpleBuilder oub = new OrgUnitSimpleBuilder()
			.number("123abc")
			.status(Status.ACTIVE)
			.members(Arrays.asList(m1, m2));
		
		IOrganizationalUnit ou = new OrgUnitSimple(oub);
		
		assertEquals(m1, ou.findMember(m1.getPersonId()));
		assertEquals(m2, ou.findMember(m2.getPersonId()));
		
		IOrgUnitMember m3 = new OrgUnitMember("personalId3");
		assertNull(ou.findMember(m3.getPersonId()));
	}

	@Test
	public void testIsMember()
	{
		IOrgUnitMember m1 = new OrgUnitMember("personalId1");
		IOrgUnitMember m2 = new OrgUnitMember("personalId2");
		
		OrgUnitSimpleBuilder oub = new OrgUnitSimpleBuilder()
			.number("123abc")
			.status(Status.ACTIVE)
			.members(Arrays.asList(m1, m2));
		
		IOrganizationalUnit ou = new OrgUnitSimple(oub);
		
		assertTrue(ou.isMember(m1.getPersonId()));
		assertTrue(ou.isMember(m2.getPersonId()));
		
		IOrgUnitMember m3 = new OrgUnitMember("personalId3");
		assertFalse(ou.isMember(m3.getPersonId()));
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
