package org.openur.module.service.userstructure.orgunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.OrgUnitSimple;
import org.openur.module.domain.userstructure.orgunit.OrgUnitSimpleBuilder;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnitBuilder;
import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnit;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.persistence.dao.IUserStructureDao;
import org.openur.module.service.userstructure.UserStructureTestSpringConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserStructureTestSpringConfig.class})
public class OrgUnitServicesTest
{
	@Inject
	private IUserStructureDao dao;
	
	@Inject
	private IOrgUnitServices orgUnitServices;

	@Test
	public void testFindOrgUnitById()
	{		
		final String UUID_1 = UUID.randomUUID().toString();
		final String UUID_2 = UUID.randomUUID().toString();
		
		IOrganizationalUnit orgUnit1 = new OrganizationalUnitBuilder(UUID_1)
			.name("Human Resources")
			.build();
		IOrganizationalUnit orgUnit2 = new OrganizationalUnitBuilder(UUID_2)
			.name("Marketing")
			.build();
		
		Mockito.when(dao.findOrgUnitById(UUID_1)).thenReturn(orgUnit1);
		Mockito.when(dao.findOrgUnitById(UUID_2)).thenReturn(orgUnit2);	
		
		IOrganizationalUnit p = orgUnitServices.findOrgUnitById(UUID_1);		
		assertNotNull(p);
		assertEquals("Name", "Human Resources", ((OrganizationalUnit) p).getName());	
		
		IOrganizationalUnit p2 = orgUnitServices.findOrgUnitById(UUID_2);		
		assertNotNull(p2);
		assertEquals("Name", "Marketing", ((OrganizationalUnit) p2).getName());
	}

	@Test
	public void testFindOrgUnitByNumber()
	{
		final String NO_123 = "no123";
		final String NO_456 = "no456";
		
		IOrganizationalUnit orgUnit1 = new OrganizationalUnitBuilder()
			.number(NO_123)
			.name("Human Resources")
			.build();
		IOrganizationalUnit orgUnit2 = new OrganizationalUnitBuilder()
			.number(NO_456)
			.name("Marketing")
			.build();
		
		Mockito.when(dao.findOrgUnitByNumber(NO_123)).thenReturn(orgUnit1);
		Mockito.when(dao.findOrgUnitByNumber(NO_456)).thenReturn(orgUnit2);
		
		IOrganizationalUnit p = orgUnitServices.findOrgUnitByNumber(NO_123);		
		assertNotNull(p);
		assertEquals("Name", "Human Resources", ((OrganizationalUnit) p).getName());	
		
		IOrganizationalUnit p2 = orgUnitServices.findOrgUnitByNumber(NO_456);		
		assertNotNull(p2);
		assertEquals("Name", "Marketing", ((OrganizationalUnit) p2).getName());
	}

	@Test
	public void testObtainAllOrgUnits()
	{
		IOrganizationalUnit orgUnit1 = new OrganizationalUnitBuilder()
			.number("no123")
			.build();
		IOrganizationalUnit orgUnit2 = new OrganizationalUnitBuilder()
			.number("no456")
			.build();
		
		Mockito.when(dao.obtainAllOrgUnits()).thenReturn(Arrays.asList(orgUnit1, orgUnit2));
		
		Set<IOrganizationalUnit> orgUnitSet = orgUnitServices.obtainAllOrgUnits();
		
		assertTrue(CollectionUtils.isNotEmpty(orgUnitSet));
		assertEquals(2, orgUnitSet.size());
		assertTrue(orgUnitSet.contains(orgUnit1));
		assertTrue(orgUnitSet.contains(orgUnit2));
		
		Mockito.when(dao.obtainAllOrgUnits()).thenReturn(null);
		orgUnitSet = orgUnitServices.obtainAllOrgUnits();
		assertNotNull(orgUnitSet);
		assertTrue(orgUnitSet.isEmpty());
	}

	@Test
	public void testObtainSubOrgUnitsForOrgUnit()
	{		
		OrgUnitSimple rootOu = new OrgUnitSimpleBuilder()
			.build();
		
		final String SUPER_OU_ID = UUID.randomUUID().toString();
		
		final String OU_NUMBER_1 = "no123";
		final String OU_ID_1 = UUID.randomUUID().toString();		
		OrganizationalUnit orgUnit1 = new OrganizationalUnitBuilder(OU_ID_1, rootOu)
				.number(OU_NUMBER_1)
				.superOuId(SUPER_OU_ID)
				.build();

		final String OU_NUMBER_2 = "no456";
		final String OU_ID_2 = UUID.randomUUID().toString();		
		IOrganizationalUnit orgUnit2 = new OrganizationalUnitBuilder(OU_ID_2, rootOu)
				.number(OU_NUMBER_2)
				.superOuId(SUPER_OU_ID)
				.build();
		
		Mockito.when(dao.obtainSubOrgUnitsForOrgUnit(SUPER_OU_ID, false))
			.thenReturn(Arrays.asList(orgUnit1, orgUnit2));
		
		Set<IOrganizationalUnit> orgUnitSet = 
			orgUnitServices.obtainSubOrgUnitsForOrgUnit(SUPER_OU_ID, false);
		
		assertTrue(CollectionUtils.isNotEmpty(orgUnitSet));
		assertEquals(2, orgUnitSet.size());
		assertTrue(orgUnitSet.contains(orgUnit1));
		assertTrue(orgUnitSet.contains(orgUnit2));
		
		
		IPerson persA = new PersonBuilder(Name.create(null, null, "Meier")).build();
		OrgUnitMember mA = new OrgUnitMember(persA, OU_ID_2);
		
		IPerson persB = new PersonBuilder(Name.create(null, null, "Meier")).build();
		OrgUnitMember mB = new OrgUnitMember(persB, OU_ID_2);
		
		IOrganizationalUnit orgUnit2_m = new OrganizationalUnitBuilder(OU_ID_2, rootOu)
				.number(OU_NUMBER_2)
				.superOuId(SUPER_OU_ID)
				.members(Arrays.asList(mA, mB))
				.build();
		
		Mockito.when(dao.obtainSubOrgUnitsForOrgUnit(SUPER_OU_ID, true))
			.thenReturn(Arrays.asList(orgUnit1, orgUnit2_m));
		
		orgUnitSet = orgUnitServices.obtainSubOrgUnitsForOrgUnit(SUPER_OU_ID, true);
		
		assertTrue(orgUnitSet.contains(orgUnit2_m));
		
		for (IOrganizationalUnit ou : orgUnitSet)
		{
			if (ou.equals(orgUnit2_m))
			{
				assertTrue(CollectionUtils.isNotEmpty(ou.getMembers()));
				assertEquals(2, ou.getMembers().size());
				assertTrue(ou.getMembers().contains(mA));
				assertTrue(ou.getMembers().contains(mB));
				
				break;
			}
		}
		
		orgUnitSet = orgUnitServices.obtainSubOrgUnitsForOrgUnit("idOfOuThatNotExtists", true);
		assertNotNull(orgUnitSet);
		assertTrue(orgUnitSet.isEmpty());
	}

	@Test
	public void testObtainRootOrgUnits()
	{		
		final String SUPER_OU_ID = UUID.randomUUID().toString();
		AbstractOrgUnit superOu = new OrganizationalUnitBuilder(SUPER_OU_ID)
			.build();
		
		final String OU_ID_1 = UUID.randomUUID().toString();		
		IOrganizationalUnit orgUnit1 = new OrganizationalUnitBuilder(OU_ID_1, superOu)
			.superOuId(SUPER_OU_ID)
			.build();
		
		Mockito.when(dao.obtainRootOrgUnits())
			.thenReturn(Arrays.asList(superOu));
		
		Set<IOrganizationalUnit> orgUnitSet = orgUnitServices.obtainRootOrgUnits();
		assertTrue(orgUnitSet.contains(superOu));
		assertFalse(orgUnitSet.contains(orgUnit1));
		
		Mockito.when(dao.obtainRootOrgUnits())
			.thenReturn(null);
		orgUnitSet = orgUnitServices.obtainRootOrgUnits();
		assertTrue(orgUnitSet.isEmpty());
		
		Mockito.when(dao.obtainRootOrgUnits())
			.thenReturn(new ArrayList<IOrganizationalUnit>());
		orgUnitSet = orgUnitServices.obtainRootOrgUnits();
		assertTrue(orgUnitSet.isEmpty());
	}
}
