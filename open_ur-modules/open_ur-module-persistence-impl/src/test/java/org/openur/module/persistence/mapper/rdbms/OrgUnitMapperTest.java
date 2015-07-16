package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.persistence.rdbms.entity.POrganizationalUnit;

public class OrgUnitMapperTest
{
	@Test
	public void testMapFromImmutable()
	{		
		POrganizationalUnit pRootOu = OrganizationalUnitMapper.mapRootOuFromImmutable(TestObjectContainer.ROOT_OU);
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(TestObjectContainer.ROOT_OU, pRootOu));
		
		POrganizationalUnit pSuperOu = OrganizationalUnitMapper.mapSuperOuFromImmutable(TestObjectContainer.SUPER_OU_2, pRootOu);
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(TestObjectContainer.SUPER_OU_2, pSuperOu));
		
		POrganizationalUnit pOrgUnit = OrganizationalUnitMapper.mapFromImmutable(TestObjectContainer.ORG_UNIT_C);		
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(TestObjectContainer.ORG_UNIT_C, pOrgUnit));
	}

	@Test
	public void testMapFromEntity()
	{
		POrganizationalUnit pRootOu = OrganizationalUnitMapper.mapRootOuFromImmutable(TestObjectContainer.ROOT_OU);		
		AuthorizableOrgUnit rootOu = OrganizationalUnitMapper.mapRootOuFromEntity(pRootOu);
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(rootOu, pRootOu));
		
		POrganizationalUnit pSuperOu = OrganizationalUnitMapper.mapSuperOuFromImmutable(TestObjectContainer.SUPER_OU_1, pRootOu);		
		AuthorizableOrgUnit superOu = OrganizationalUnitMapper.mapSuperOuFromEntity(pSuperOu, rootOu);
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(superOu, pSuperOu));
		
		POrganizationalUnit pOrgUnit = OrganizationalUnitMapper.mapFromImmutable(TestObjectContainer.ORG_UNIT_A);		
		AuthorizableOrgUnit orgUnit = OrganizationalUnitMapper.mapFromEntity(pOrgUnit, false, false);
		assertTrue(orgUnit.getMembers().isEmpty());

		orgUnit = OrganizationalUnitMapper.mapFromEntity(pOrgUnit, true, false);
		assertFalse(orgUnit.getMembers().isEmpty());
		for (AuthorizableMember member : orgUnit.getMembers())
		{
			assertTrue(member.getRoles().isEmpty());
		}
		
		orgUnit = OrganizationalUnitMapper.mapFromEntity(pOrgUnit, true, true);		
		assertFalse(orgUnit.getMembers().isEmpty());
		assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity(orgUnit, pOrgUnit));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testMapFromEntityWithoutMembersButWithRoles()
	{
		POrganizationalUnit pOrgUnit = new POrganizationalUnit("orgUnitNo", "staff department");
		OrganizationalUnitMapper.mapFromEntity(pOrgUnit, false, true);		
	}
}
