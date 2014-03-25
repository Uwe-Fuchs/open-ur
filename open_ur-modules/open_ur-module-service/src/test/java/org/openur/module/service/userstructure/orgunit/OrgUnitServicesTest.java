package org.openur.module.service.userstructure.orgunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrgUnitDelegateBuilder;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMemberBuilder;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnitBuilder;
import org.openur.module.domain.userstructure.user.person.IPerson;
import org.openur.module.domain.userstructure.user.person.PersonBuilder;
import org.openur.module.persistence.userstructure.IUserStructureDao;
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
		String uuid = UUID.randomUUID().toString();
		String uuid2 = UUID.randomUUID().toString();
		
		IOrganizationalUnit orgUnit1 = 
			new OrganizationalUnitBuilder(uuid).name("Human Resources").build();
		IOrganizationalUnit orgUnit2 = 
			new OrganizationalUnitBuilder(uuid2).name("Marketing").build();
		
		Mockito.when(dao.findOrgUnitById(uuid)).thenReturn(orgUnit1);
		Mockito.when(dao.findOrgUnitById(uuid2)).thenReturn(orgUnit2);	
		
		IOrganizationalUnit p = orgUnitServices.findOrgUnitById(uuid);		
		assertNotNull(p);
		assertEquals("Name", "Human Resources", ((OrganizationalUnit) p).getName());	
		
		IOrganizationalUnit p2 = orgUnitServices.findOrgUnitById(uuid2);		
		assertNotNull(p2);
		assertEquals("Name", "Marketing", ((OrganizationalUnit) p2).getName());
	}

	@Test
	public void testFindOrgUnitByNumber()
	{
		String orgUnitNo1 = "no123";
		String orgUnitNo2 = "no456";
		
		IOrganizationalUnit orgUnit1 = 
			new OrganizationalUnitBuilder().number(orgUnitNo1).name("Human Resources").build();
		IOrganizationalUnit orgUnit2 = 
			new OrganizationalUnitBuilder().number(orgUnitNo2).name("Marketing").build();
		
		Mockito.when(dao.findOrgUnitByNumber(orgUnitNo1)).thenReturn(orgUnit1);
		Mockito.when(dao.findOrgUnitByNumber(orgUnitNo2)).thenReturn(orgUnit2);
		
		IOrganizationalUnit p = orgUnitServices.findOrgUnitByNumber(orgUnitNo1);		
		assertNotNull(p);
		assertEquals("Name", "Human Resources", ((OrganizationalUnit) p).getName());	
		
		IOrganizationalUnit p2 = orgUnitServices.findOrgUnitByNumber(orgUnitNo2);		
		assertNotNull(p2);
		assertEquals("Name", "Marketing", ((OrganizationalUnit) p2).getName());
	}

	@Test
	public void testObtainAllOrgUnits()
	{
		String orgUnitNo1 = "no123";
		String orgUnitNo2 = "no456";
		
		IOrganizationalUnit orgUnit1 = 
			new OrganizationalUnitBuilder().number(orgUnitNo1).build();
		IOrganizationalUnit orgUnit2 = 
			new OrganizationalUnitBuilder().number(orgUnitNo2).build();
		
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
		final String SUPER_OU_ID = UUID.randomUUID().toString();
		
		final String OU_NUMBER_1 = "no123";
		final String OU_ID_1 = UUID.randomUUID().toString();		
		IOrganizationalUnit orgUnit1 = new OrganizationalUnitBuilder(OU_ID_1)
				.number(OU_NUMBER_1)
				.superOuId(SUPER_OU_ID)
				.build();

		final String OU_NUMBER_2 = "no456";
		final String OU_ID_2 = UUID.randomUUID().toString();		
		IOrganizationalUnit orgUnit2 = new OrganizationalUnitBuilder(OU_ID_2)
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
		
		for (IOrganizationalUnit ou : orgUnitSet)
		{
			if (ou.equals(orgUnit2))
			{
				assertTrue(CollectionUtils.isEmpty(ou.getMembers()));
				
				break;
			}
		}
		
		IOrganizationalUnit oud_2 = new OrgUnitDelegateBuilder(OU_ID_2)
				.number(OU_NUMBER_2)
				.superOuId(SUPER_OU_ID)
				.build();
		
		IPerson persA = new PersonBuilder("usernameA", "passwordA").build();
		IOrgUnitMember mA = new OrgUnitMemberBuilder(persA, oud_2).build();
		
		IPerson persB = new PersonBuilder("usernameB", "passwordB").build();
		IOrgUnitMember mB = new OrgUnitMemberBuilder(persB, oud_2).build();
		
		IOrganizationalUnit orgUnit2_m = new OrganizationalUnitBuilder(OU_ID_2)
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
}
