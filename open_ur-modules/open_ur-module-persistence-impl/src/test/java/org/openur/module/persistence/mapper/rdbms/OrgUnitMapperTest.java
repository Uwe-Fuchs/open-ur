package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.persistence.rdbms.config.MapperSpringConfig;
import org.openur.module.persistence.rdbms.entity.POrganizationalUnit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { MapperSpringConfig.class })
@ActiveProfiles(profiles = { "testMappers" })
@RunWith(SpringJUnit4ClassRunner.class)
public class OrgUnitMapperTest
{
	@Inject
	private OrganizationalUnitMapper organizationalUnitMapper;
	
	@Test
	public void testMapFromDomainObject()
	{		
		POrganizationalUnit pRootOu = organizationalUnitMapper.mapRootOuFromDomainObject(TestObjectContainer.ROOT_OU);
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity(TestObjectContainer.ROOT_OU, pRootOu));
		
		POrganizationalUnit pSuperOu = organizationalUnitMapper.mapSuperOuFromDomainObject(TestObjectContainer.SUPER_OU_2, pRootOu);
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity(TestObjectContainer.SUPER_OU_2, pSuperOu));
		
		POrganizationalUnit pOrgUnit = organizationalUnitMapper.mapFromDomainObject(TestObjectContainer.ORG_UNIT_C);		
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity(TestObjectContainer.ORG_UNIT_C, pOrgUnit));
	}

	@Test
	public void testMapFromEntity()
	{
		POrganizationalUnit pRootOu = organizationalUnitMapper.mapRootOuFromDomainObject(TestObjectContainer.ROOT_OU);		
		AuthorizableOrgUnit rootOu = organizationalUnitMapper.mapRootOuFromEntity(pRootOu);
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity(rootOu, pRootOu));
		
		POrganizationalUnit pSuperOu = organizationalUnitMapper.mapSuperOuFromDomainObject(TestObjectContainer.SUPER_OU_1, pRootOu);		
		AuthorizableOrgUnit superOu = organizationalUnitMapper.mapSuperOuFromEntity(pSuperOu, rootOu);
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity(superOu, pSuperOu));
		
		POrganizationalUnit pOrgUnit = organizationalUnitMapper.mapFromDomainObject(TestObjectContainer.ORG_UNIT_A);		
		AuthorizableOrgUnit orgUnit = organizationalUnitMapper.mapFromEntity(pOrgUnit, false, false);
		assertTrue(orgUnit.getMembers().isEmpty());

		orgUnit = organizationalUnitMapper.mapFromEntity(pOrgUnit, true, false);
		assertFalse(orgUnit.getMembers().isEmpty());
		for (AuthorizableMember member : orgUnit.getMembers())
		{
			assertTrue(member.getRoles().isEmpty());
		}
		
		orgUnit = organizationalUnitMapper.mapFromEntity(pOrgUnit, true, true);		
		assertFalse(orgUnit.getMembers().isEmpty());
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity(orgUnit, pOrgUnit));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testMapFromEntityWithoutMembersButWithRoles()
	{
		POrganizationalUnit pOrgUnit = new POrganizationalUnit("orgUnitNo", "staff department");
		organizationalUnitMapper.mapFromEntity(pOrgUnit, false, true);		
	}
}
