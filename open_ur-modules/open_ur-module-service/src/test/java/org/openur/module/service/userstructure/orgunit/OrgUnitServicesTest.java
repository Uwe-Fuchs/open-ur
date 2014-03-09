package org.openur.module.service.userstructure.orgunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnitBuilder;
import org.openur.module.persistence.userstructure.IUserStructureDao;

public class OrgUnitServicesTest
{
	@Mock
	private IUserStructureDao dao;
	
	private IOrgUnitServices orgUnitServices;

	@Before
	public void setUp()
		throws Exception
	{
		MockitoAnnotations.initMocks(this);
		OrgUnitServicesImpl _orgUnitServices = new OrgUnitServicesImpl();
		_orgUnitServices.setUserStructureDao(dao);
		this.orgUnitServices = _orgUnitServices;
	}

	@Test
	public void testFindOrgUnitById()
	{		
		String uuid = UUID.randomUUID().toString();
		String uuid2 = UUID.randomUUID().toString();
		
		IOrganizationalUnit orgUnit1 = new OrganizationalUnit(
			new OrganizationalUnitBuilder(uuid).name("Human Resources"));
		IOrganizationalUnit orgUnit2 = new OrganizationalUnit(
			new OrganizationalUnitBuilder(uuid2).name("Marketing"));
		
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
		
		IOrganizationalUnit orgUnit1 = new OrganizationalUnit(
			new OrganizationalUnitBuilder().number(orgUnitNo1).name("Human Resources"));
		IOrganizationalUnit orgUnit2 = new OrganizationalUnit(
			new OrganizationalUnitBuilder().number(orgUnitNo2).name("Marketing"));
		
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
		
		IOrganizationalUnit orgUnit1 = new OrganizationalUnit(
			new OrganizationalUnitBuilder().number(orgUnitNo1));
		IOrganizationalUnit orgUnit2 = new OrganizationalUnit(
			new OrganizationalUnitBuilder().number(orgUnitNo2));
		
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
		String superOuId = UUID.randomUUID().toString();
		
		String orgUnitNo1 = "no123";		
		IOrganizationalUnit orgUnit1 = new OrganizationalUnit(
			new OrganizationalUnitBuilder()
				.number(orgUnitNo1)
				.superOuId(superOuId)
		);

		String orgUnitNo2 = "no456";
		String orgUnitId = UUID.randomUUID().toString();		
		IOrganizationalUnit orgUnit2 = new OrganizationalUnit(
			new OrganizationalUnitBuilder(orgUnitId)
				.number(orgUnitNo2)
				.superOuId(superOuId)
		);
		
		OrgUnitMember m1 = new OrgUnitMember("personalId1");
		OrgUnitMember m2 = new OrgUnitMember("personalId2");
		IOrganizationalUnit orgUnit2_m = new OrganizationalUnit(
			new OrganizationalUnitBuilder(orgUnitId)
				.number(orgUnitNo2)
				.members(Arrays.asList(m1, m2))
				.superOuId(superOuId)
		);
		
		Mockito.when(dao.obtainSubOrgUnitsForOrgUnit(superOuId, false))
			.thenReturn(Arrays.asList(orgUnit1, orgUnit2));
		Mockito.when(dao.obtainSubOrgUnitsForOrgUnit(superOuId, true))
			.thenReturn(Arrays.asList(orgUnit1, orgUnit2_m));
		
		Set<IOrganizationalUnit> orgUnitSet = 
			orgUnitServices.obtainSubOrgUnitsForOrgUnit(superOuId, false);
		
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
		
		orgUnitSet = orgUnitServices.obtainSubOrgUnitsForOrgUnit(superOuId, true);
		
		assertTrue(orgUnitSet.contains(orgUnit2_m));
		
		for (IOrganizationalUnit ou : orgUnitSet)
		{
			if (ou.equals(orgUnit2_m))
			{
				assertTrue(CollectionUtils.isNotEmpty(ou.getMembers()));
				assertEquals(2, ou.getMembers().size());
				assertTrue(ou.getMembers().contains(m1));
				assertTrue(ou.getMembers().contains(m2));
				
				break;
			}
		}
		
		orgUnitSet = orgUnitServices.obtainSubOrgUnitsForOrgUnit("idOfOuThatNotExtists", true);
		assertNotNull(orgUnitSet);
		assertTrue(orgUnitSet.isEmpty());
	}
}
