package org.openur.module.domain.userstructure.orgunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.security.IRole;
import org.openur.module.domain.security.OpenURApplicationBuilder;
import org.openur.module.domain.security.OpenURPermissionBuilder;
import org.openur.module.domain.security.OpenURRoleBuilder;
import org.openur.module.domain.security.PermissionScope;
import org.openur.module.domain.userstructure.InconsistentHierarchyException;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.user.person.IPerson;
import org.openur.module.domain.userstructure.user.person.PersonBuilder;
import org.openur.module.util.exception.OpenURRuntimeException;

public class OrgUnitSimpleTest
{
	@Test
	public void testFindMember()
	{
		IPerson pers1 = new PersonBuilder("username1", "password1")
			.build();
		
		IPerson pers2 = new PersonBuilder("username2", "password2")
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		
		IOrgUnitMember m2 = new OrgUnitMemberBuilder(pers2, OU_ID)
			.build();
		IOrgUnitMember m1 = new OrgUnitMemberBuilder(pers1, OU_ID)
			.build();		

		IOrganizationalUnit ou = new OrgUnitSimpleBuilder(OU_ID)
			.members(Arrays.asList(m1, m2))
			.build();
		
		assertEquals(m1, ou.findMember(m1.getPerson()));
		assertEquals(m1, ou.findMember(m1.getPerson().getIdentifier()));
		assertEquals(m2, ou.findMember(m2.getPerson()));
		assertEquals(m2, ou.findMember(m2.getPerson().getIdentifier()));
		
		IPerson pers3 = new PersonBuilder("username3", "password3")
			.build();
		IOrgUnitMember m3 = new OrgUnitMemberBuilder(pers3, OU_ID)
			.build();
		
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
		IPerson pers1 = new PersonBuilder("username1", "password1")
			.build();
	
		IPerson pers2 = new PersonBuilder("username2", "password2")
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();
		
		IOrgUnitMember m2 = new OrgUnitMemberBuilder(pers2, OU_ID)
			.build();
		IOrgUnitMember m1 = new OrgUnitMemberBuilder(pers1, OU_ID)
			.build();		
	
		IOrganizationalUnit ou = new OrgUnitSimpleBuilder(OU_ID)
			.members(Arrays.asList(m1, m2))
			.build();
		
		assertTrue(ou.isMember(m1.getPerson()));
		assertTrue(ou.isMember(m1.getPerson().getIdentifier()));
		assertTrue(ou.isMember(m2.getPerson()));
		assertTrue(ou.isMember(m2.getPerson().getIdentifier()));
		
		IPerson pers3 = new PersonBuilder("username3", "password3")
			.build();
		IOrgUnitMember m3 = new OrgUnitMemberBuilder(pers3, OU_ID)
			.build();
		
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
		IPerson pers = new PersonBuilder("username1", "password1").build();
		IOrgUnitMember member = new OrgUnitMemberBuilder(pers, "123").build();
		
		OrgUnitSimpleBuilder oub = new OrgUnitSimpleBuilder("456");
		oub.members(Arrays.asList(member));
	}

	@Test
	public void testHasPermission()
	{
		IApplication app1 = new OpenURApplicationBuilder("app1", "user1", "pw1")
			.build();		
		IPermission perm11 = new OpenURPermissionBuilder("perm11", PermissionScope.SELECTED,  app1)
			.build();		
		IRole role1 = new OpenURRoleBuilder("role1")
			.permissions(new HashSet<IPermission>(Arrays.asList(perm11)))
			.build();
		
		IPerson person = new PersonBuilder("username1", "password1")
			.build();
		
		final String OU_ID = UUID.randomUUID().toString();		
		
		IOrgUnitMember member = new OrgUnitMemberBuilder(person, OU_ID)
		  .roles(Arrays.asList(role1))
		  .build();
		
		IOrganizationalUnit ou = new OrgUnitSimpleBuilder(OU_ID)
			.members(Arrays.asList(member))
			.build();
		
		// has permission:		
		assertTrue(ou.hasPermission(person, app1, perm11));
	
		// doesn't have permission:
		IPermission perm12 = new OpenURPermissionBuilder("perm12", PermissionScope.SELECTED_SUB,  app1)
			.build();
		
		assertFalse(ou.hasPermission(person, app1, perm12));
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
