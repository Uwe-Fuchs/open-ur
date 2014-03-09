package org.openur.module.domain.userstructure.orgunit;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.openur.module.domain.userstructure.Status;

public class OrgUnitSimpleTest
{
	@Before
	public void setUp()
		throws Exception
	{
	}

	@Test
	public void testFindMember()
	{
		OrgUnitMember m1 = new OrgUnitMember("personalId1");
		OrgUnitMember m2 = new OrgUnitMember("personalId2");
		
		OrgUnitSimpleBuilder oub = new OrgUnitSimpleBuilder()
			.number("123abc")
			.status(Status.ACTIVE)
			.members(Arrays.asList(m1, m2));
		
		OrgUnitSimple ou = new OrgUnitSimple(oub);
		
		assertEquals(m1, ou.findMember(m1.getPersonId()));
		assertEquals(m2, ou.findMember(m2.getPersonId()));
		
		OrgUnitMember m3 = new OrgUnitMember("personalId3");
		assertNull(ou.findMember(m3.getPersonId()));
	}

	@Test
	public void testIsMember()
	{
		OrgUnitMember m1 = new OrgUnitMember("personalId1");
		OrgUnitMember m2 = new OrgUnitMember("personalId2");
		
		OrgUnitSimpleBuilder oub = new OrgUnitSimpleBuilder()
			.number("123abc")
			.status(Status.ACTIVE)
			.members(Arrays.asList(m1, m2));
		
		OrgUnitSimple ou = new OrgUnitSimple(oub);
		
		assertTrue(ou.isMember(m1.getPersonId()));
		assertTrue(ou.isMember(m2.getPersonId()));
		
		OrgUnitMember m3 = new OrgUnitMember("personalId3");
		assertFalse(ou.isMember(m3.getPersonId()));
	}

	@Test
	public void testCompareTo()
	{
		OrgUnitSimpleBuilder oub = new OrgUnitSimpleBuilder()
			.number("123abc")
			.status(Status.ACTIVE);
		OrgUnitSimple ou = new OrgUnitSimple(oub);
		
		oub = new OrgUnitSimpleBuilder(UUID.randomUUID().toString())
			.number("456xyz")
			.status(Status.ACTIVE)
			.superOuId(ou.getIdentifier());
		OrgUnitSimple ou2 = new OrgUnitSimple(oub);
		
		assertTrue("ou1 should be before ou2 because of ou-numbers", ou.compareTo(ou2) < 0);
		
		oub = new OrgUnitSimpleBuilder()
			.number("123abc")
			.status(Status.INACTIVE);
		ou2 = new OrgUnitSimple(oub);
		
		assertTrue("ou1 should be before ou2 because of status", ou.compareTo(ou2) < 0);
	}
}
