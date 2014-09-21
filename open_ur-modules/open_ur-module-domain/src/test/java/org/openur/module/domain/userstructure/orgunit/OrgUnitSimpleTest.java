package org.openur.module.domain.userstructure.orgunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.userstructure.InconsistentHierarchyException;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.PersonSimpleBuilder;
import org.openur.module.util.exception.OpenURRuntimeException;

public class OrgUnitSimpleTest
{
	@Test
	public void testFindMember()
	{
		IPerson pers1 = new PersonSimpleBuilder()
			.build();
		
		IPerson pers2 = new PersonSimpleBuilder()
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		
		IOrgUnitMember m2 = new OrgUnitMember(pers2, OU_ID);
		IOrgUnitMember m1 = new OrgUnitMember(pers1, OU_ID);		

		IOrganizationalUnit ou = new OrgUnitSimpleBuilder(OU_ID)
			.members(Arrays.asList(m1, m2))
			.build();
		
		assertEquals(m1, ou.findMember(m1.getPerson()));
		assertEquals(m1, ou.findMember(m1.getPerson().getIdentifier()));
		assertEquals(m2, ou.findMember(m2.getPerson()));
		assertEquals(m2, ou.findMember(m2.getPerson().getIdentifier()));
		
		IPerson pers3 = new PersonSimpleBuilder()
			.build();
		IOrgUnitMember m3 = new OrgUnitMember(pers3, OU_ID);
		
		assertNull(ou.findMember(m3.getPerson()));
		assertNull(ou.findMember(m3.getPerson().getIdentifier()));
		
		ou = new OrgUnitSimpleBuilder(OU_ID)
			.members(Arrays.asList(m1, m2, m3))
			.build();
		assertEquals(m3, ou.findMember(m3.getPerson()));
		assertEquals(m3, ou.findMember(m3.getPerson().getIdentifier()));
	}

	@Test
	public void testIsMember()
	{
		IPerson pers1 = new PersonSimpleBuilder()
			.build();
	
		IPerson pers2 = new PersonSimpleBuilder()
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		
		IOrgUnitMember m2 = new OrgUnitMember(pers2, OU_ID);
		IOrgUnitMember m1 = new OrgUnitMember(pers1, OU_ID);		
	
		IOrganizationalUnit ou = new OrgUnitSimpleBuilder(OU_ID)
			.members(Arrays.asList(m1, m2))
			.build();
		
		assertTrue(ou.isMember(m1.getPerson()));
		assertTrue(ou.isMember(m1.getPerson().getIdentifier()));
		assertTrue(ou.isMember(m2.getPerson()));
		assertTrue(ou.isMember(m2.getPerson().getIdentifier()));
		
		IPerson pers3 = new PersonSimpleBuilder()
			.build();
		IOrgUnitMember m3 = new OrgUnitMember(pers3, OU_ID);
		
		assertFalse(ou.isMember(m3.getPerson()));
		assertFalse(ou.isMember(m3.getPerson().getIdentifier()));
		
		ou = new OrgUnitSimpleBuilder(OU_ID)
			.members(Arrays.asList(m1, m2, m3))
			.build();
		
		assertTrue(ou.isMember(m3.getPerson()));
		assertTrue(ou.isMember(m3.getPerson().getIdentifier()));
	}
	
	@Test(expected=OpenURRuntimeException.class)
	public void testCreateWithWrongOrgUnitID()
	{
		IPerson pers = new PersonSimpleBuilder().build();
		IOrgUnitMember member = new OrgUnitMember(pers, "123");
		
		OrgUnitSimpleBuilder oub = new OrgUnitSimpleBuilder("456");
		oub.members(Arrays.asList(member));
	}
	
	@Test
	public void testCreateWithNullMembers()
	{
		OrgUnitSimple ou = new OrgUnitSimpleBuilder()
			.members(null)
			.build();
		
		assertTrue(ou.getMembers().isEmpty());
	}

	@Test
	public void testCompareTo()
	{
		IOrganizationalUnit ou = new OrgUnitSimpleBuilder(UUID.randomUUID().toString())
			.number("123abc")
			.status(Status.ACTIVE)
			.build();		
		
		IOrganizationalUnit ou2 = new OrgUnitSimpleBuilder()
			.number("456xyz")
			.status(Status.ACTIVE)
			.build();
		
		assertTrue("ou1 should be before ou2 because of ou-numbers", ou.compareTo(ou2) < 0);
		
		ou2 = new OrgUnitSimpleBuilder()
			.number("123abc")
			.status(Status.INACTIVE)
			.build();
		
		assertTrue("ou1 should be before ou2 because of status", ou.compareTo(ou2) < 0);
	}

	public void testIsRootOrgUnit()
	{
		IOrganizationalUnit ou = new OrgUnitSimpleBuilder()
			.build();
		assertTrue(ou.isRootOrgUnit());
		
		IOrganizationalUnit rootOu = new OrgUnitSimpleBuilder()
			.build();
		
		ou = new OrgUnitSimpleBuilder(rootOu)
			.superOuId("123")
			.build();
		assertFalse(ou.isRootOrgUnit());
	}	
	
	@Test(expected=InconsistentHierarchyException.class)
	public void testCreateAsRootWithSuperOuId()
	{
		new OrgUnitSimpleBuilder()
			.superOuId("testId")
			.build();
	}
	
	@Test(expected=InconsistentHierarchyException.class)
	public void testCreateAsNonRootWithoutSuperOuId()
	{
		IOrganizationalUnit rootOu = new OrgUnitSimpleBuilder().build();
		
		new OrgUnitSimpleBuilder(rootOu)
			.build();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateAsNonRootWithEmptySuperOuId()
	{
		IOrganizationalUnit rootOu = new OrgUnitSimpleBuilder().build();
		
		new OrgUnitSimpleBuilder(rootOu)
			.superOuId("");
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateAsNonRootWithEmptyRoot()
	{
		IOrganizationalUnit rootOu = null;
		
		new OrgUnitSimpleBuilder(rootOu);
	}
}
