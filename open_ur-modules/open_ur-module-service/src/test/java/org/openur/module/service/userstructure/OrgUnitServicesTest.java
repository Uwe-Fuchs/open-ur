package org.openur.module.service.userstructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.domain.testfixture.dummyimpl.MyOrgUnitMember;
import org.openur.domain.testfixture.dummyimpl.MyOrgUnit;
import org.openur.domain.testfixture.dummyimpl.MyPerson;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.persistence.dao.IOrgUnitDao;
import org.openur.module.service.config.UserStructureTestSpringConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("testUserServices")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserStructureTestSpringConfig.class})
public class OrgUnitServicesTest
{
	private static String UUID_1;
	private static String UUID_2;
	private static String OTHER_UUID;	
	private static String NO_123;	
	private static String NO_456;
	private static String NUMBER_DIFFERENT_FROM_ALL_OTHERS;

	private static IOrganizationalUnit ORG_UNIT_1;
	private static IOrganizationalUnit ORG_UNIT_2;
	
	@Inject
	private IOrgUnitDao orgUnitDaoMock;
	
	@Inject
	private IOrgUnitServices orgUnitServices;
	
	@BeforeClass
	public static void init()
	{
		UUID_1 = UUID.randomUUID().toString();
		UUID_2 = UUID.randomUUID().toString();
		OTHER_UUID = UUID.randomUUID().toString();
		
		NO_123 = "123";
		NO_456 = "456";
		NUMBER_DIFFERENT_FROM_ALL_OTHERS = "numberDifferentFromAllOthers";
		
		ORG_UNIT_1 = new MyOrgUnit(UUID_1, NO_123);
		ORG_UNIT_2 = new MyOrgUnit(UUID_2, NO_456);
	}

	@Test
	public void testFindOrgUnitAndMembersById()
	{		
		// test with open-ur-specific domain-objects:
		Mockito.when(orgUnitDaoMock.findOrgUnitAndMembersById(TestObjectContainer.ORG_UNIT_UUID_A)).thenReturn(TestObjectContainer.ORG_UNIT_A);	
		Mockito.when(orgUnitDaoMock.findOrgUnitAndMembersById(TestObjectContainer.ORG_UNIT_UUID_B)).thenReturn(TestObjectContainer.ORG_UNIT_B);	
		
		IOrganizationalUnit o = orgUnitServices.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A, Boolean.TRUE);		
		assertNotNull(o);
		assertEquals(TestObjectContainer.ORG_UNIT_A, o);
		assertFalse(o.getMembers().isEmpty());
		
		o = orgUnitServices.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_B, Boolean.TRUE);		
		assertNotNull(o);
		assertEquals(TestObjectContainer.ORG_UNIT_B, o);
		assertFalse(o.getMembers().isEmpty());
		
		o = orgUnitServices.findOrgUnitById(OTHER_UUID, Boolean.TRUE);
		assertTrue(o == null);

		// test with arbitrary domain-objects:
		Mockito.when(orgUnitDaoMock.findOrgUnitAndMembersById(UUID_1)).thenReturn(ORG_UNIT_1);
		Mockito.when(orgUnitDaoMock.findOrgUnitAndMembersById(UUID_2)).thenReturn(ORG_UNIT_2);	
		
		o = orgUnitServices.findOrgUnitById(UUID_1, Boolean.TRUE);		
		assertNotNull(o);
		assertEquals("identifier", o.getIdentifier(), UUID_1);
		assertEquals("orgunit-number", o.getNumber(), NO_123);
		
		o = orgUnitServices.findOrgUnitById(UUID_2, Boolean.TRUE);		
		assertNotNull(o);
		assertEquals("identifier", o.getIdentifier(), UUID_2);
		assertEquals("orgunit-number", o.getNumber(), NO_456);
		
		o = orgUnitServices.findOrgUnitById(OTHER_UUID, Boolean.TRUE);
		assertTrue(o == null);
	}

	@Test
	public void testFindOrgUnitWithoutMembersById()
	{		
		// test with open-ur-specific domain-objects:
		Mockito.when(orgUnitDaoMock.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A)).thenReturn(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS);	
		
		IOrganizationalUnit o = orgUnitServices.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A, Boolean.FALSE);		
		assertNotNull(o);
		assertEquals(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, o);
		assertTrue(o.getMembers().isEmpty());
	}

	@Test
	public void testFindOrgUnitAndMembersByNumber()
	{
		// test with open-ur-specific domain-objects:
		Mockito.when(orgUnitDaoMock.findOrgUnitAndMembersByNumber(TestObjectContainer.ORG_UNIT_NUMBER_A)).thenReturn(TestObjectContainer.ORG_UNIT_A);
		Mockito.when(orgUnitDaoMock.findOrgUnitAndMembersByNumber(TestObjectContainer.ORG_UNIT_NUMBER_B)).thenReturn(TestObjectContainer.ORG_UNIT_B);
		
		IOrganizationalUnit o = orgUnitServices.findOrgUnitByNumber(TestObjectContainer.ORG_UNIT_NUMBER_A, Boolean.TRUE);		
		assertNotNull(o);
		assertEquals(TestObjectContainer.ORG_UNIT_A, o);
		assertFalse(o.getMembers().isEmpty());
		
		o = orgUnitServices.findOrgUnitByNumber(TestObjectContainer.ORG_UNIT_NUMBER_B, Boolean.TRUE);		
		assertNotNull(o);
		assertEquals(TestObjectContainer.ORG_UNIT_B, o);
		assertFalse(o.getMembers().isEmpty());
		
		o = orgUnitServices.findOrgUnitByNumber(NUMBER_DIFFERENT_FROM_ALL_OTHERS, Boolean.TRUE);
		assertTrue(o == null);

		// test with arbitrary domain-objects:
		Mockito.when(orgUnitDaoMock.findOrgUnitAndMembersByNumber(NO_123)).thenReturn(ORG_UNIT_1);
		Mockito.when(orgUnitDaoMock.findOrgUnitAndMembersByNumber(NO_456)).thenReturn(ORG_UNIT_2);
		
		o = orgUnitServices.findOrgUnitByNumber(NO_123, Boolean.TRUE);		
		assertNotNull(o);
		assertEquals("orgunit-number", o.getNumber(), NO_123);
		assertEquals("identifier", o.getIdentifier(), UUID_1);
		
		o = orgUnitServices.findOrgUnitByNumber(NO_456, Boolean.TRUE);		
		assertNotNull(o);
		assertEquals("orgunit-number", o.getNumber(), NO_456);
		assertEquals("identifier", o.getIdentifier(), UUID_2);
		
		o = orgUnitServices.findOrgUnitById(NUMBER_DIFFERENT_FROM_ALL_OTHERS, Boolean.TRUE);
		assertTrue(o == null);
	}

	@Test
	public void testFindOrgUnitWithoutMembersByNumber()
	{
		// test with open-ur-specific domain-objects:
		Mockito.when(orgUnitDaoMock.findOrgUnitByNumber(TestObjectContainer.ORG_UNIT_NUMBER_A)).thenReturn(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS);
		
		IOrganizationalUnit o = orgUnitServices.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A, Boolean.FALSE);		
		assertNotNull(o);
		assertEquals(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS, o);
		assertTrue(o.getMembers().isEmpty());
	}

	@Test
	public void testObtainAllOrgUnits()
	{		
		// test with open-ur-specific domain-objects:
		Mockito.when(orgUnitDaoMock.obtainAllOrgUnits()).thenReturn(Arrays.asList(TestObjectContainer.ORG_UNIT_A, TestObjectContainer.ORG_UNIT_B));
		
		Set<IOrganizationalUnit> orgUnitSet = orgUnitServices.obtainAllOrgUnits();
		
		assertNotNull(orgUnitSet);
		assertEquals(2, orgUnitSet.size());
		
		for (IOrganizationalUnit o : orgUnitSet)
		{
			assertTrue(TestObjectContainer.ORG_UNIT_A.equals(o) || TestObjectContainer.ORG_UNIT_B.equals(o));
		}

		// test with arbitrary domain-objects:
		Mockito.when(orgUnitDaoMock.obtainAllOrgUnits()).thenReturn(Arrays.asList(ORG_UNIT_1, ORG_UNIT_2));
		
		orgUnitSet = orgUnitServices.obtainAllOrgUnits();
		
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
		// test with open-ur-specific domain-objects:
		Mockito.when(orgUnitDaoMock.obtainSubOrgUnitsForOrgUnitInclMembers(TestObjectContainer.SUPER_OU_NUMBER_1))
				.thenReturn(Arrays.asList(TestObjectContainer.ORG_UNIT_A, TestObjectContainer.ORG_UNIT_B));
		
		Set<? extends IOrganizationalUnit> orgUnitSet = orgUnitServices.obtainSubOrgUnitsForOrgUnit(TestObjectContainer.SUPER_OU_NUMBER_1, Boolean.TRUE);
		
		assertNotNull(orgUnitSet);
		assertEquals(2, orgUnitSet.size());
		
		for (IOrganizationalUnit o : orgUnitSet)
		{
			assertTrue(TestObjectContainer.ORG_UNIT_A.equals(o) || TestObjectContainer.ORG_UNIT_B.equals(o));
			
			if (TestObjectContainer.ORG_UNIT_A.equals(o))
			{
				assertNotNull(o.getMembers());
				assertEquals(2, o.getMembers().size());
				
				for (IOrgUnitMember m : o.getMembers())
				{
					assertTrue(TestObjectContainer.PERSON_1.equals(m.getPerson()) || TestObjectContainer.PERSON_2.equals(m.getPerson()));
				}
			} else
			{
				assertNotNull(o.getMembers());
				assertEquals(2, o.getMembers().size());
				
				for (IOrgUnitMember m : o.getMembers())
				{
					assertTrue(TestObjectContainer.PERSON_1.equals(m.getPerson()) || TestObjectContainer.PERSON_3.equals(m.getPerson()));
				}
			}
		}

		// test with arbitrary domain-objects:
		final String SUPER_OU_ID = UUID.randomUUID().toString();		
		final MyOrgUnit SUPER_OU = new MyOrgUnit(SUPER_OU_ID, "superOuNumber");		
		final MyOrgUnit ROOT_OU = new MyOrgUnit(UUID.randomUUID().toString(), "rootOuNumber");		

		MyOrgUnit orgUnit1 = new MyOrgUnit(UUID_1, NO_123);
		orgUnit1.setSuperOrgUnit(SUPER_OU);
		orgUnit1.setRootOrgUnit(ROOT_OU);
		
		MyOrgUnit orgUnit2 = new MyOrgUnit(UUID_2, NO_456);
		orgUnit2.setSuperOrgUnit(SUPER_OU);
		orgUnit2.setRootOrgUnit(ROOT_OU);
		
		Mockito.when(orgUnitDaoMock.obtainSubOrgUnitsForOrgUnit(SUPER_OU_ID)).thenReturn(Arrays.asList(orgUnit1, orgUnit2));
		
		orgUnitSet = orgUnitServices.obtainSubOrgUnitsForOrgUnit(SUPER_OU_ID, Boolean.FALSE);
		
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
		MyOrgUnitMember mA = new MyOrgUnitMember(persA, UUID_2);
		
		IPerson persB = new MyPerson(UUID_PERS_B, NUMBER_PERS_B);
		MyOrgUnitMember mB = new MyOrgUnitMember(persB, UUID_2);
		
		MyOrgUnit orgUnit2_m = new MyOrgUnit(UUID_2, NO_456);
		orgUnit2_m.setSuperOrgUnit(SUPER_OU);
		orgUnit2_m.setRootOrgUnit(ROOT_OU);
		orgUnit2_m.addMember(mA);
		orgUnit2_m.addMember(mB);
		
		Mockito.when(orgUnitDaoMock.obtainSubOrgUnitsForOrgUnitInclMembers(SUPER_OU_ID)).thenReturn(Arrays.asList(orgUnit1, orgUnit2_m));
		
		orgUnitSet = orgUnitServices.obtainSubOrgUnitsForOrgUnit(SUPER_OU_ID, Boolean.TRUE);
		
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
		// test with open-ur-specific domain-objects:
		Mockito.when(orgUnitDaoMock.obtainRootOrgUnits()).thenReturn(Arrays.asList(TestObjectContainer.ROOT_OU));
		
		Set<? extends IOrganizationalUnit> orgUnitSet = orgUnitServices.obtainRootOrgUnits();
		
		assertNotNull(orgUnitSet);
		assertEquals(1, orgUnitSet.size());
		IOrganizationalUnit resultOu = orgUnitSet.iterator().next();
		assertEquals(TestObjectContainer.ROOT_OU, resultOu);
		assertEquals(resultOu, TestObjectContainer.ORG_UNIT_A.getRootOrgUnit());

		// test with arbitrary domain-objects:
		final String ROOT_OU_ID = UUID.randomUUID().toString();
		final String ROOT_OU_NUMBER = "rootOuNumber";
		MyOrgUnit rootOu = new MyOrgUnit(ROOT_OU_ID, ROOT_OU_NUMBER);

		MyOrgUnit orgUnit1 = new MyOrgUnit(UUID_1, NO_123);
		orgUnit1.setRootOrgUnit(rootOu);
		
		Mockito.when(orgUnitDaoMock.obtainRootOrgUnits()).thenReturn(Arrays.asList(rootOu));
		
		orgUnitSet = orgUnitServices.obtainRootOrgUnits();
		
		assertNotNull(orgUnitSet);
		assertEquals(1, orgUnitSet.size());
		resultOu = orgUnitSet.iterator().next();
		assertEquals(ROOT_OU_ID, resultOu.getIdentifier());
		assertEquals(ROOT_OU_NUMBER, resultOu.getNumber());
		assertEquals(resultOu, orgUnit1.getRootOrgUnit());
	}
}
