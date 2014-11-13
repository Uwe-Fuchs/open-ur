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
import org.openur.module.domain.userstructure.person.PersonSimple;
import org.openur.module.domain.userstructure.person.PersonSimpleBuilder;
import org.openur.module.util.exception.OpenURRuntimeException;

public class OrgUnitSimpleTest
{
	@Test
	public void testFindMember()
	{
		PersonSimple pers1 = new PersonSimpleBuilder()
			.build();
		
		PersonSimple pers2 = new PersonSimpleBuilder()
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		
		OrgUnitMemberSimple m2 = new OrgUnitMemberSimple(pers2, OU_ID);
		OrgUnitMemberSimple m1 = new OrgUnitMemberSimple(pers1, OU_ID);		
		
		OrgUnitSimpleBuilder ouBuilder = new OrgUnitSimpleBuilder(OU_ID)
			.orgUnitMembers(Arrays.asList(m1, m2));

		OrgUnitSimple ou = ouBuilder.build();
		
		assertEquals(m1, ou.findMember(m1.getPerson()));
		assertEquals(m1, ou.findMember(m1.getPerson().getIdentifier()));
		assertEquals(m2, ou.findMember(m2.getPerson()));
		assertEquals(m2, ou.findMember(m2.getPerson().getIdentifier()));
		
		PersonSimple pers3 = new PersonSimpleBuilder()
			.build();
		OrgUnitMemberSimple m3 = new OrgUnitMemberSimple(pers3, OU_ID);
		
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
		PersonSimple pers1 = new PersonSimpleBuilder()
			.build();
	
		PersonSimple pers2 = new PersonSimpleBuilder()
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		
		OrgUnitMemberSimple m2 = new OrgUnitMemberSimple(pers2, OU_ID);
		OrgUnitMemberSimple m1 = new OrgUnitMemberSimple(pers1, OU_ID);		
	
		OrgUnitSimple ou = new OrgUnitSimpleBuilder(OU_ID)
			.orgUnitMembers(Arrays.asList(m1, m2))
			.build();
		
		assertTrue(ou.isMember(m1.getPerson()));
		assertTrue(ou.isMember(m1.getPerson().getIdentifier()));
		assertTrue(ou.isMember(m2.getPerson()));
		assertTrue(ou.isMember(m2.getPerson().getIdentifier()));
		
		PersonSimple pers3 = new PersonSimpleBuilder()
			.build();
		OrgUnitMemberSimple m3 = new OrgUnitMemberSimple(pers3, OU_ID);
		
		assertFalse(ou.isMember(m3.getPerson()));
		assertFalse(ou.isMember(m3.getPerson().getIdentifier()));
		
		ou = new OrgUnitSimpleBuilder(OU_ID)
			.orgUnitMembers(Arrays.asList(m1, m2, m3))
			.build();
		
		assertTrue(ou.isMember(m3.getPerson()));
		assertTrue(ou.isMember(m3.getPerson().getIdentifier()));
	}
	
	@Test(expected=OpenURRuntimeException.class)
	public void testCreateWithWrongOrgUnitID()
	{
		PersonSimple pers = new PersonSimpleBuilder().build();
		OrgUnitMemberSimple member = new OrgUnitMemberSimple(pers, "123");
		
		OrgUnitSimpleBuilder oub = new OrgUnitSimpleBuilder("456");
		oub.orgUnitMembers(Arrays.asList(member));
	}
	
	@Test
	public void testCreateWithNullMembers()
	{
		OrgUnitSimple ou = new OrgUnitSimpleBuilder()
			.build();
		
		assertTrue(ou.getMembers().isEmpty());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateWithEmptyMembersList()
	{
		new OrgUnitSimpleBuilder()
			.orgUnitMembers(new ArrayList<OrgUnitMemberSimple>(0))
			.build();
	}

	@Test
	public void testCompareTo()
	{
		OrgUnitSimple ou = new OrgUnitSimpleBuilder(UUID.randomUUID().toString())
			.number("123abc")
			.status(Status.ACTIVE)
			.build();		
		
		OrgUnitSimple ou2 = new OrgUnitSimpleBuilder()
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

	@Test
	public void testIsRootOrgUnit()
	{
		OrgUnitSimple rootOu = new OrgUnitSimpleBuilder()
			.build();
		assertTrue(rootOu.isRootOrgUnit());
		
		OrgUnitSimple superOu = new OrgUnitSimpleBuilder(rootOu)
			.superOrgUnit(rootOu)
			.build();
		
		OrgUnitSimple ou = new OrgUnitSimpleBuilder(rootOu)
			.superOrgUnit(superOu)
			.build();
		assertFalse(ou.isRootOrgUnit());
	}	
	
	@Test(expected=InconsistentHierarchyException.class)
	public void testCreateAsRootWithSuperOuId()
	{
		OrgUnitSimple ou = new OrgUnitSimpleBuilder()
			.build();
		
		new OrgUnitSimpleBuilder()
			.superOrgUnit(ou)
			.build();
	}
	
	@Test(expected=InconsistentHierarchyException.class)
	public void testCreateAsNonRootWithoutSuperOuId()
	{
		OrgUnitSimple rootOu = new OrgUnitSimpleBuilder().build();
		
		new OrgUnitSimpleBuilder(rootOu)
			.build();
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateWithEmptySuperOu()
	{
		OrgUnitSimple rootOu = new OrgUnitSimpleBuilder()
			.build();
		
		new OrgUnitSimpleBuilder(rootOu)
			.superOrgUnit(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateAsNonRootWithEmptyRoot()
	{
		OrgUnitSimple rootOu = null;
		
		new OrgUnitSimpleBuilder(rootOu);
	}
}
