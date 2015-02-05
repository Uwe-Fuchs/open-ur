package org.openur.module.service.userstructure;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.persistence.dao.IUserStructureDao;
import org.openur.module.service.MyAuthorizableMember;
import org.openur.module.service.MyAuthorizableOrgUnit;
import org.openur.module.service.MyPerson;
import org.openur.module.service.config.UserStructureTestSpringConfig;
import org.openur.module.service.userstructure.IOrgUnitServices;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("testUserServices")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserStructureTestSpringConfig.class})
public class OrgUnitServicesTest
{
	private final String UUID_1;
	private final String UUID_2;
	private final String OTHER_UUID;	
	private final String NO_123;	
	private final String NO_456;
	private final String NUMBER_DIFFERENT_FROM_ALL_OTHERS;

	private final IAuthorizableOrgUnit ORG_UNIT_1;
	private final IAuthorizableOrgUnit ORG_UNIT_2;
	
	@Inject
	private IUserStructureDao userStructureDaoMock;
	
	@Inject
	private IOrgUnitServices orgUnitServices;

	public OrgUnitServicesTest()
	{
		super();

		UUID_1 = UUID.randomUUID().toString();
		UUID_2 = UUID.randomUUID().toString();
		OTHER_UUID = UUID.randomUUID().toString();
		
		NO_123 = "123";
		NO_456 = "456";
		NUMBER_DIFFERENT_FROM_ALL_OTHERS = "numberDifferentFromAllOthers";
		
		ORG_UNIT_1 = new MyAuthorizableOrgUnit(UUID_1, NO_123);
		ORG_UNIT_2 = new MyAuthorizableOrgUnit(UUID_2, NO_456);
	}

	@Test
	public void testFindOrgUnitById()
	{		
		Mockito.when(userStructureDaoMock.findOrgUnitById(UUID_1)).thenReturn(ORG_UNIT_1);
		Mockito.when(userStructureDaoMock.findOrgUnitById(UUID_2)).thenReturn(ORG_UNIT_2);	
		
		IOrganizationalUnit o = orgUnitServices.findOrgUnitById(UUID_1);		
		assertNotNull(o);
		assertEquals("identifier", o.getIdentifier(), UUID_1);
		assertEquals("orgunit-number", o.getNumber(), NO_123);
		
		o = orgUnitServices.findOrgUnitById(UUID_2);		
		assertNotNull(o);
		assertEquals("identifier", o.getIdentifier(), UUID_2);
		assertEquals("orgunit-number", o.getNumber(), NO_456);
		
		o = orgUnitServices.findOrgUnitById(OTHER_UUID);
		assertTrue(o == null || !o.getIdentifier().equals(UUID_1) || !o.getIdentifier().equals(UUID_2));
	}

	@Test
	public void testFindOrgUnitByNumber()
	{
		Mockito.when(userStructureDaoMock.findOrgUnitByNumber(NO_123)).thenReturn(ORG_UNIT_1);
		Mockito.when(userStructureDaoMock.findOrgUnitByNumber(NO_456)).thenReturn(ORG_UNIT_2);
		
		IOrganizationalUnit o = orgUnitServices.findOrgUnitByNumber(NO_123);		
		assertNotNull(o);
		assertEquals("orgunit-number", o.getNumber(), NO_123);
		assertEquals("identifier", o.getIdentifier(), UUID_1);
		
		o = orgUnitServices.findOrgUnitByNumber(NO_456);		
		assertNotNull(o);
		assertEquals("orgunit-number", o.getNumber(), NO_456);
		assertEquals("identifier", o.getIdentifier(), UUID_2);
		
		o = orgUnitServices.findOrgUnitById(NUMBER_DIFFERENT_FROM_ALL_OTHERS);
		assertTrue(o == null || !o.getNumber().equals(NO_123) || !o.getNumber().equals(NO_456));
	}

	@Test
	public void testObtainAllOrgUnits()
	{
		Mockito.when(userStructureDaoMock.obtainAllOrgUnits()).thenReturn(Arrays.asList(ORG_UNIT_1, ORG_UNIT_2));
		
		Set<IAuthorizableOrgUnit> orgUnitSet = orgUnitServices.obtainAllOrgUnits();
		
		assertNotNull(orgUnitSet);
		assertEquals(2, orgUnitSet.size());
		
		for (IOrganizationalUnit o : orgUnitSet)
		{
			assertTrue(UUID_1.equals(o.getIdentifier()) || UUID_2.equals(o.getIdentifier()));
			assertTrue(NO_123.equals(o.getNumber()) || NO_456.equals(o.getNumber()));
		}
		
		for (IOrganizationalUnit o : orgUnitSet)
		{
			assertFalse(OTHER_UUID.equals(o.getIdentifier()));
			assertFalse(NUMBER_DIFFERENT_FROM_ALL_OTHERS.equals(o.getNumber()));
		}
	}

	@Test
	public void testObtainSubOrgUnitsForOrgUnit()
	{		
		final String SUPER_OU_ID = UUID.randomUUID().toString();		
		final MyAuthorizableOrgUnit SUPER_OU = new MyAuthorizableOrgUnit(SUPER_OU_ID, "superOuNumber");		
		final MyAuthorizableOrgUnit ROOT_OU = new MyAuthorizableOrgUnit(UUID.randomUUID().toString(), "rootOuNumber");		

		MyAuthorizableOrgUnit orgUnit1 = new MyAuthorizableOrgUnit(UUID_1, NO_123);
		orgUnit1.setSuperOrgUnit(SUPER_OU);
		orgUnit1.setRootOrgUnit(ROOT_OU);
		
		MyAuthorizableOrgUnit orgUnit2 = new MyAuthorizableOrgUnit(UUID_2, NO_456);
		orgUnit2.setSuperOrgUnit(SUPER_OU);
		orgUnit2.setRootOrgUnit(ROOT_OU);
		
		Mockito.when(userStructureDaoMock.obtainSubOrgUnitsForOrgUnit(SUPER_OU_ID, false)).thenReturn(Arrays.asList(orgUnit1, orgUnit2));
		
		Set<IAuthorizableOrgUnit> orgUnitSet = orgUnitServices.obtainSubOrgUnitsForOrgUnit(SUPER_OU_ID, false);
		
		assertNotNull(orgUnitSet);
		assertEquals(2, orgUnitSet.size());
		
		for (IOrganizationalUnit o : orgUnitSet)
		{
			assertTrue(UUID_1.equals(o.getIdentifier()) || UUID_2.equals(o.getIdentifier()));
			assertTrue(NO_123.equals(o.getNumber()) || NO_456.equals(o.getNumber()));
		}
		
		for (IOrganizationalUnit o : orgUnitSet)
		{
			assertFalse(OTHER_UUID.equals(o.getIdentifier()));
			assertFalse(NUMBER_DIFFERENT_FROM_ALL_OTHERS.equals(o.getNumber()));
		}
		
		final String UUID_PERS_A = UUID.randomUUID().toString();
		final String UUID_PERS_B = UUID.randomUUID().toString();
		final String NUMBER_PERS_A = "numberPersA";
		final String NUMBER_PERS_B = "numberPersB";
		
		IPerson persA = new MyPerson(UUID_PERS_A, NUMBER_PERS_A);
		MyAuthorizableMember mA = new MyAuthorizableMember(persA, UUID_2);
		
		IPerson persB = new MyPerson(UUID_PERS_B, NUMBER_PERS_B);
		MyAuthorizableMember mB = new MyAuthorizableMember(persB, UUID_2);
		
		MyAuthorizableOrgUnit orgUnit2_m = new MyAuthorizableOrgUnit(UUID_2, NO_456);
		orgUnit2_m.setSuperOrgUnit(SUPER_OU);
		orgUnit2_m.setRootOrgUnit(ROOT_OU);
		orgUnit2_m.addMember(mA);
		orgUnit2_m.addMember(mB);
		
		Mockito.when(userStructureDaoMock.obtainSubOrgUnitsForOrgUnit(SUPER_OU_ID, true)).thenReturn(Arrays.asList(orgUnit1, orgUnit2_m));
		
		orgUnitSet = orgUnitServices.obtainSubOrgUnitsForOrgUnit(SUPER_OU_ID, true);
		
		assertNotNull(orgUnitSet);
		assertEquals(2, orgUnitSet.size());
		
		for (IOrganizationalUnit ou : orgUnitSet)
		{
			if (UUID_2.equals(ou.getIdentifier()))
			{
				assertNotNull(ou.getMembers());
				assertEquals(2, ou.getMembers().size());
				
				for (IOrgUnitMember m : ou.getMembers())
				{
					assertTrue(UUID_PERS_A.equals(m.getPerson().getIdentifier()) || UUID_PERS_B.equals(m.getPerson().getIdentifier()));
					assertTrue(NUMBER_PERS_A.equals(m.getPerson().getNumber()) || NUMBER_PERS_B.equals(m.getPerson().getNumber()));
				}
				
				break;
			}
		}
	}

	@Test
	public void testObtainRootOrgUnits()
	{		
		final String ROOT_OU_ID = UUID.randomUUID().toString();
		final String ROOT_OU_NUMBER = "rootOuNumber";
		MyAuthorizableOrgUnit rootOu = new MyAuthorizableOrgUnit(ROOT_OU_ID, ROOT_OU_NUMBER);

		MyAuthorizableOrgUnit orgUnit1 = new MyAuthorizableOrgUnit(UUID_1, NO_123);
		orgUnit1.setRootOrgUnit(rootOu);
		
		Mockito.when(userStructureDaoMock.obtainRootOrgUnits()).thenReturn(Arrays.asList(rootOu));
		
		Set<IAuthorizableOrgUnit> orgUnitSet = orgUnitServices.obtainRootOrgUnits();
		
		assertNotNull(orgUnitSet);
		assertEquals(1, orgUnitSet.size());
		IOrganizationalUnit resultOu = orgUnitSet.iterator().next();
		assertEquals(ROOT_OU_ID, resultOu.getIdentifier());
		assertEquals(ROOT_OU_NUMBER, resultOu.getNumber());
		assertEquals(resultOu, orgUnit1.getRootOrgUnit());
	}
}
