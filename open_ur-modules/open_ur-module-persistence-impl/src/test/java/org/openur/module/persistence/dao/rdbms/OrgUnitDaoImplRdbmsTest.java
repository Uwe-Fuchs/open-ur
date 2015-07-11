package org.openur.module.persistence.dao.rdbms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IAuthorizableMember;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Title;
import org.openur.module.persistence.dao.IOrgUnitDao;
import org.openur.module.persistence.mapper.rdbms.OrgUnitMapperTest;
import org.openur.module.persistence.mapper.rdbms.OrganizationalUnitMapper;
import org.openur.module.persistence.mapper.rdbms.OrganizationalUnitMapper.OrgUnitMemberMapper;
import org.openur.module.persistence.rdbms.config.DaoSpringConfig;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.openur.module.persistence.rdbms.entity.PAddress;
import org.openur.module.persistence.rdbms.entity.POrgUnitMember;
import org.openur.module.persistence.rdbms.entity.POrganizationalUnit;
import org.openur.module.persistence.rdbms.entity.PPerson;
import org.openur.module.persistence.rdbms.entity.PRole;
import org.openur.module.persistence.rdbms.entity.PTechnicalUser;
import org.openur.module.persistence.rdbms.repository.AddressRepository;
import org.openur.module.persistence.rdbms.repository.OrgUnitMemberRepository;
import org.openur.module.persistence.rdbms.repository.OrgUnitRepository;
import org.openur.module.persistence.rdbms.repository.PersonRepository;
import org.openur.module.persistence.rdbms.repository.TechnicalUserRepository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositorySpringConfig.class, DaoSpringConfig.class })
@ActiveProfiles(profiles = { "testRepository", "testDao" })
@RunWith(SpringJUnit4ClassRunner.class)
public class OrgUnitDaoImplRdbmsTest
{
	private static final String ROOT_OU_NO = "rootOuNo";
	private static final String SUPER_OU_NO = "superOuNo";
	private static final String ORG_UNIT_NO = "orgUnitNo";

	@Inject
	private PersonRepository personRepository;

	@Inject
	private AddressRepository addressRepository;

	@Inject
	private TechnicalUserRepository technicalUserRepository;

	@Inject
	private OrgUnitRepository orgUnitRepository;

	@Inject
	private OrgUnitMemberRepository orgUnitMemberRepository;

	@Inject
	private IOrgUnitDao orgUnitDao;

	@Test
	public void testFindOrgUnitById()
	{
		POrganizationalUnit pRootOu = new POrganizationalUnit(ROOT_OU_NO, "rootOu");
		saveOrgUnit(pRootOu);

		POrganizationalUnit pSuperOu = new POrganizationalUnit(SUPER_OU_NO, "superOu");
		pSuperOu.setSuperOu(pRootOu);
		pSuperOu.setRootOu(pRootOu);
		saveOrgUnit(pSuperOu);

		POrganizationalUnit pOrgUnit = new POrganizationalUnit(ORG_UNIT_NO, "staff department");
		pOrgUnit.setSuperOu(pSuperOu);
		pOrgUnit.setRootOu(pRootOu);
		pOrgUnit.setShortName("stf");
		pOrgUnit.setDescription("managing staff");
		pOrgUnit.setEmailAddress("staff@company.com");

		PAddress pAddress = new PAddress("11");
		pAddress.setCountryCode("DE");
		pAddress.setCity("city_1");
		pAddress.setStreet("street_1");
		pAddress.setPoBox("poBox_1");
		pOrgUnit.setAddress(pAddress);

		pOrgUnit = saveOrgUnit(pOrgUnit);

		IOrganizationalUnit immutable = orgUnitDao.findOrgUnitById(pOrgUnit.getIdentifier());
		assertNotNull(immutable);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) immutable, pOrgUnit));
	}

	@Test
	public void testFindOrgUnitAndMembersRolesById()
	{
		POrganizationalUnit pRootOu = new POrganizationalUnit(ROOT_OU_NO, "rootOu");
		saveOrgUnit(pRootOu);

		POrganizationalUnit pSuperOu = new POrganizationalUnit(SUPER_OU_NO, "superOu");
		pSuperOu.setSuperOu(pRootOu);
		pSuperOu.setRootOu(pRootOu);
		saveOrgUnit(pSuperOu);

		POrganizationalUnit pOrgUnit = new POrganizationalUnit(ORG_UNIT_NO, "staff department");
		pOrgUnit.setSuperOu(pSuperOu);
		pOrgUnit.setRootOu(pRootOu);
		pOrgUnit.setShortName("stf");
		pOrgUnit.setDescription("managing staff");
		pOrgUnit.setEmailAddress("staff@company.com");

		PAddress pAddress = new PAddress("11");
		pAddress.setCountryCode("DE");
		pAddress.setCity("city_1");
		pAddress.setStreet("street_1");
		pAddress.setPoBox("poBox_1");
		pOrgUnit.setAddress(pAddress);

		PPerson pPerson1 = new PPerson("persNo1", "Obama");
		pPerson1.setGender(Gender.MALE);
		pPerson1.setFirstName("Barack");
		savePerson(pPerson1);

		PPerson pPerson2 = new PPerson("persNo2", "Merkel");
		pPerson2.setGender(Gender.FEMALE);
		pPerson2.setTitle(Title.DR);
		pPerson2.setFirstName("Angela");
		savePerson(pPerson2);

		PRole pRole1 = new PRole("role1");
		pRole1.setDescription("description role1");

		PRole pRole2 = new PRole("role2");
		pRole2.setDescription("description role2");

		POrgUnitMember pMember1 = new POrgUnitMember(pOrgUnit, pPerson1);
		pMember1.addRole(pRole1);

		POrgUnitMember pMember2 = new POrgUnitMember(pOrgUnit, pPerson2);
		pMember2.addRole(pRole2);

		pOrgUnit.setMembers(new HashSet<POrgUnitMember>(Arrays.asList(pMember1, pMember2)));

		saveOrgUnit(pOrgUnit);

		IOrganizationalUnit immutable = orgUnitDao.findOrgUnitAndMembersById(pOrgUnit.getIdentifier());
		assertNotNull(immutable);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) immutable, pOrgUnit));
	}

	@Test
	public void testFindOrgUnitByNumber()
	{
		POrganizationalUnit pRootOu = new POrganizationalUnit(ROOT_OU_NO, "rootOu");
		saveOrgUnit(pRootOu);

		POrganizationalUnit pSuperOu = new POrganizationalUnit(SUPER_OU_NO, "superOu");
		pSuperOu.setSuperOu(pRootOu);
		pSuperOu.setRootOu(pRootOu);
		saveOrgUnit(pSuperOu);

		POrganizationalUnit pOrgUnit = new POrganizationalUnit(ORG_UNIT_NO, "staff department");

		pOrgUnit.setSuperOu(pSuperOu);
		pOrgUnit.setRootOu(pRootOu);
		saveOrgUnit(pOrgUnit);

		IOrganizationalUnit immutable = orgUnitDao.findOrgUnitByNumber(ORG_UNIT_NO);
		assertNotNull(immutable);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) immutable, pOrgUnit));

		immutable = orgUnitDao.findOrgUnitByNumber(ROOT_OU_NO);
		assertNotNull(immutable);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) immutable, pRootOu));

		immutable = orgUnitDao.findOrgUnitByNumber(SUPER_OU_NO);
		assertNotNull(immutable);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) immutable, pSuperOu));
	}

	@Test
	public void testFindOrgUnitAndMembersRolesByNumber()
	{
		POrganizationalUnit pRootOu = new POrganizationalUnit(ROOT_OU_NO, "rootOu");
		saveOrgUnit(pRootOu);

		POrganizationalUnit pSuperOu = new POrganizationalUnit(SUPER_OU_NO, "superOu");
		pSuperOu.setSuperOu(pRootOu);
		pSuperOu.setRootOu(pRootOu);
		saveOrgUnit(pSuperOu);

		POrganizationalUnit pOrgUnit = new POrganizationalUnit(ORG_UNIT_NO, "staff department");

		pOrgUnit.setSuperOu(pSuperOu);
		pOrgUnit.setRootOu(pRootOu);
		
		PPerson pPersonA = new PPerson("persNoA", "Obama");
		pPersonA.setGender(Gender.MALE);
		pPersonA.setFirstName("Barack");
		savePerson(pPersonA);

		PPerson pPersonB = new PPerson("persNoB", "Merkel");
		pPersonB.setGender(Gender.FEMALE);
		pPersonB.setTitle(Title.DR);
		pPersonB.setFirstName("Angela");
		savePerson(pPersonB);

		PRole pRole1 = new PRole("role1");
		pRole1.setDescription("description role1");

		PRole pRole2 = new PRole("role2");
		pRole2.setDescription("description role2");

		POrgUnitMember pMember1 = new POrgUnitMember(pOrgUnit, pPersonA);
		pMember1.addRole(pRole1);

		POrgUnitMember pMember2 = new POrgUnitMember(pOrgUnit, pPersonB);
		pMember2.addRole(pRole2);
		
		pOrgUnit.setMembers(new HashSet<POrgUnitMember>(Arrays.asList(pMember1, pMember1)));
		
		saveOrgUnit(pOrgUnit);

		IOrganizationalUnit immutable = orgUnitDao.findOrgUnitAndMembersByNumber(ORG_UNIT_NO);
		assertNotNull(immutable);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) immutable, pOrgUnit));
	}
	
	@Test
	public void testObtainAllOrgUnits()
	{
		POrganizationalUnit pOrgUnit1 = new POrganizationalUnit(ORG_UNIT_NO, "staff department");
		saveOrgUnit(pOrgUnit1);
		POrganizationalUnit pOrgUnit2 = new POrganizationalUnit("orgUnitNo2", "hr department");
		saveOrgUnit(pOrgUnit2);
		POrganizationalUnit pOrgUnit3 = new POrganizationalUnit("orgUnitNo3", "it department");
		saveOrgUnit(pOrgUnit3);
		
		List<IAuthorizableOrgUnit> allOrgUnits = orgUnitDao.obtainAllOrgUnits();
		
		assertEquals(3, allOrgUnits.size());
		IAuthorizableOrgUnit orgUnit1 = findOrgUnitInList(allOrgUnits, ORG_UNIT_NO);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) orgUnit1, pOrgUnit1));
		IAuthorizableOrgUnit orgUnit2 = findOrgUnitInList(allOrgUnits, "orgUnitNo2");
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) orgUnit2, pOrgUnit2));
		IAuthorizableOrgUnit orgUnit3 = findOrgUnitInList(allOrgUnits, "orgUnitNo3");
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) orgUnit3, pOrgUnit3));
		assertNull(findOrgUnitInList(allOrgUnits, "orgUnitNo4"));
	}
	
	private IAuthorizableOrgUnit findOrgUnitInList(List<IAuthorizableOrgUnit> orgUnitList, String orgUnitNumber) {
		Optional<IAuthorizableOrgUnit> result = orgUnitList
				.stream()
				.filter(o -> o.getOrgUnitNumber().equals(orgUnitNumber))
				.findFirst();
		
		return result.isPresent() ? result.get() : null;
	}
	
	@Test
	public void testObtainSubOrgUnitsForOrgUnit()
	{
		POrganizationalUnit pRootOu = new POrganizationalUnit(ROOT_OU_NO, "rootOu");
		saveOrgUnit(pRootOu);

		POrganizationalUnit pSuperOu = new POrganizationalUnit(SUPER_OU_NO, "superOu");
		pSuperOu.setSuperOu(pRootOu);
		pSuperOu.setRootOu(pRootOu);
		saveOrgUnit(pSuperOu);

		POrganizationalUnit pOrgUnit1 = new POrganizationalUnit(ORG_UNIT_NO, "staff department");
		pOrgUnit1.setSuperOu(pSuperOu);
		pOrgUnit1.setRootOu(pRootOu);
		saveOrgUnit(pOrgUnit1);
		
		POrganizationalUnit pOrgUnit2 = new POrganizationalUnit("orgUnitNo2", "hr department");
		pOrgUnit2.setSuperOu(pSuperOu);
		pOrgUnit2.setRootOu(pRootOu);
		saveOrgUnit(pOrgUnit2);
		
		POrganizationalUnit pOrgUnit3 = new POrganizationalUnit("orgUnitNo3", "it department");
		pOrgUnit3.setSuperOu(pSuperOu);
		pOrgUnit3.setRootOu(pRootOu);
		saveOrgUnit(pOrgUnit3);
		
		POrganizationalUnit pOrgUnit4 = new POrganizationalUnit("orgUnitNo4", "some department");
		pOrgUnit4.setSuperOu(pRootOu);
		pOrgUnit4.setRootOu(pRootOu);
		saveOrgUnit(pOrgUnit4);
		
		List<IAuthorizableOrgUnit> subOrgUnits = orgUnitDao.obtainSubOrgUnitsForOrgUnit(pSuperOu.getIdentifier());
		
		assertEquals(3, subOrgUnits.size());
		IAuthorizableOrgUnit orgUnit1 = findOrgUnitInList(subOrgUnits, ORG_UNIT_NO);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) orgUnit1, pOrgUnit1));
		IAuthorizableOrgUnit orgUnit2 = findOrgUnitInList(subOrgUnits, "orgUnitNo2");
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) orgUnit2, pOrgUnit2));
		IAuthorizableOrgUnit orgUnit3 = findOrgUnitInList(subOrgUnits, "orgUnitNo3");
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) orgUnit3, pOrgUnit3));
		assertNull(findOrgUnitInList(subOrgUnits, "orgUnitNo4"));
		
		for (IAuthorizableOrgUnit orgUnit : subOrgUnits)
		{
			assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity((AuthorizableOrgUnit) orgUnit.getSuperOrgUnit(), pSuperOu));
		}
		
		subOrgUnits = orgUnitDao.obtainSubOrgUnitsForOrgUnit(pRootOu.getIdentifier());
		assertEquals(2, subOrgUnits.size());
		IAuthorizableOrgUnit superOu = findOrgUnitInList(subOrgUnits, SUPER_OU_NO);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) superOu, pSuperOu));
		IAuthorizableOrgUnit orgUnit4 = findOrgUnitInList(subOrgUnits, "orgUnitNo4");
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) orgUnit4, pOrgUnit4));
		
		for (IAuthorizableOrgUnit orgUnit : subOrgUnits)
		{
			assertTrue(OrganizationalUnitMapper.immutableEqualsToEntity((AuthorizableOrgUnit) orgUnit.getSuperOrgUnit(), pRootOu));
		}
	}
	
	@Test
	@Transactional(readOnly=false)
	//@Rollback(value=false)
	public void testObtainSubOrgUnitsForOrgUnitInclMembers()
	{
		POrganizationalUnit pRootOu = new POrganizationalUnit(ROOT_OU_NO, "rootOu");
		saveOrgUnit(pRootOu);

		POrganizationalUnit pSuperOu = new POrganizationalUnit(SUPER_OU_NO, "superOu");
		pSuperOu.setSuperOu(pRootOu);
		pSuperOu.setRootOu(pRootOu);
		saveOrgUnit(pSuperOu);
		
		PPerson pPersonA = new PPerson("persNoA", "namePersonA");
		pPersonA.setGender(Gender.MALE);
		pPersonA.setFirstName("firstNameA");
		savePerson(pPersonA);

		PPerson pPersonB = new PPerson("persNoB", "namePersonB");
		pPersonB.setGender(Gender.FEMALE);
		pPersonB.setTitle(Title.DR);
		pPersonB.setFirstName("firstNameB");
		savePerson(pPersonB);
		
		PPerson pPersonC = new PPerson("persNoC", "namePersonC");
		pPersonC.setGender(Gender.MALE);
		pPersonC.setFirstName("firstNameC");
		savePerson(pPersonC);

		PPerson pPersonD = new PPerson("persNoD", "namePersonD");
		pPersonD.setGender(Gender.FEMALE);
		pPersonD.setTitle(Title.PROF);
		pPersonD.setFirstName("firstNameD");
		savePerson(pPersonD);

		PRole pRole1 = new PRole("role1");
		pRole1.setDescription("description role1");

		PRole pRole2 = new PRole("role2");
		pRole2.setDescription("description role2");

		POrganizationalUnit pOrgUnit1 = new POrganizationalUnit(ORG_UNIT_NO, "staff department");
		pOrgUnit1.setSuperOu(pSuperOu);
		pOrgUnit1.setRootOu(pRootOu);
		POrgUnitMember pMember1_1 = new POrgUnitMember(pOrgUnit1, pPersonA);
		pMember1_1.addRole(pRole1);	
		pOrgUnit1.addMember(pMember1_1);
		POrgUnitMember pMember1_2 = new POrgUnitMember(pOrgUnit1, pPersonB);
		pMember1_2.addRole(pRole2);		
		pOrgUnit1.addMember(pMember1_2);
		saveOrgUnit(pOrgUnit1);
		
		POrganizationalUnit pOrgUnit2 = new POrganizationalUnit("orgUnitNo2", "hr department");
		pOrgUnit2.setSuperOu(pSuperOu);
		pOrgUnit2.setRootOu(pRootOu);
		POrgUnitMember pMember2_1 = new POrgUnitMember(pOrgUnit2, pPersonC);
		pMember2_1.addRole(pRole2);		
		pOrgUnit2.addMember(pMember2_1);
		saveOrgUnit(pOrgUnit2);
		
		POrganizationalUnit pOrgUnit3 = new POrganizationalUnit("orgUnitNo3", "it department");
		pOrgUnit3.setSuperOu(pSuperOu);
		pOrgUnit3.setRootOu(pRootOu);
		POrgUnitMember pMember3_1 = new POrgUnitMember(pOrgUnit3, pPersonA);
		pMember3_1.addRole(pRole1);	
		pOrgUnit3.addMember(pMember3_1);
		POrgUnitMember pMember3_2 = new POrgUnitMember(pOrgUnit3, pPersonB);
		pMember3_2.addRole(pRole2);		
		pOrgUnit3.addMember(pMember3_2);
		POrgUnitMember pMember3_3 = new POrgUnitMember(pOrgUnit3, pPersonD);
		pMember3_3.addRole(pRole2);		
		pOrgUnit3.addMember(pMember3_3);
		saveOrgUnit(pOrgUnit3);
		
		List<IAuthorizableOrgUnit> subOrgUnits = orgUnitDao.obtainSubOrgUnitsForOrgUnitInclMembers(pSuperOu.getIdentifier());		
		assertEquals(3, subOrgUnits.size());
		
		IAuthorizableOrgUnit resultOrgUnit = findOrgUnitInList(subOrgUnits, ORG_UNIT_NO);		
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) resultOrgUnit, pOrgUnit1));
		
		resultOrgUnit = findOrgUnitInList(subOrgUnits, "orgUnitNo2");		
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) resultOrgUnit, pOrgUnit2));
		
		resultOrgUnit = findOrgUnitInList(subOrgUnits, "orgUnitNo3");		
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) resultOrgUnit, pOrgUnit3));
	}

  @Test
  public void testObtainRootOrgUnits()
  {
		POrganizationalUnit pRootOu1 = new POrganizationalUnit(ROOT_OU_NO, "rootOu1");
		saveOrgUnit(pRootOu1);

		POrganizationalUnit pRootOu2 = new POrganizationalUnit("rootOuNo2", "rootOu2");
		saveOrgUnit(pRootOu2);

		POrganizationalUnit pOrgUnit = new POrganizationalUnit(ORG_UNIT_NO, "orgUnit");
		pOrgUnit.setSuperOu(pRootOu1);
		pOrgUnit.setRootOu(pRootOu1);
		saveOrgUnit(pOrgUnit);
		
		List<IAuthorizableOrgUnit> rootOrgUnits = orgUnitDao.obtainRootOrgUnits();
		assertEquals(2, rootOrgUnits.size());
		IAuthorizableOrgUnit rootOu1 = findOrgUnitInList(rootOrgUnits, ROOT_OU_NO);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) rootOu1, pRootOu1));
		IAuthorizableOrgUnit rootOu2 = findOrgUnitInList(rootOrgUnits, "rootOuNo2");
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) rootOu2, pRootOu2));
		
		assertNull(findOrgUnitInList(rootOrgUnits, ORG_UNIT_NO));
  }

  @Test
  public void testFindMembersForOrgUnit()
  {
		PPerson pPerson1 = new PPerson("persNo1", "Obama");
		savePerson(pPerson1);

		PPerson pPerson2 = new PPerson("persNo2", "Merkel");
		savePerson(pPerson2);
		
		POrganizationalUnit pOrgUnit = new POrganizationalUnit(ORG_UNIT_NO, "staff department");
		POrgUnitMember pMember1 = new POrgUnitMember(pOrgUnit, pPerson1);
		POrgUnitMember pMember2 = new POrgUnitMember(pOrgUnit, pPerson2);
		pOrgUnit.setMembers(new HashSet<POrgUnitMember>(Arrays.asList(pMember1, pMember2)));

		saveOrgUnit(pOrgUnit);		

		POrganizationalUnit pOrgUnit2 = new POrganizationalUnit("orgUnitNo2", "hr department");
		saveOrgUnit(pOrgUnit2);
		
		List<IAuthorizableMember> members = orgUnitDao.findMembersForOrgUnit(pOrgUnit.getIdentifier());
		assertEquals(2, members.size());
		assertTrue(members.contains(OrgUnitMemberMapper.mapFromEntity(pMember1, pOrgUnit.getIdentifier(), false)));
		assertTrue(members.contains(OrgUnitMemberMapper.mapFromEntity(pMember2, pOrgUnit.getIdentifier(), false)));
		
		members = orgUnitDao.findMembersForOrgUnit(pOrgUnit2.getIdentifier());
		assertEquals(0, members.size());
  }

	@After
	public void tearDown()
		throws Exception
	{
		orgUnitMemberRepository.deleteAll();
		orgUnitRepository.deleteAll();
		personRepository.deleteAll();
		technicalUserRepository.deleteAll();
		addressRepository.deleteAll();
	}

	@Transactional(readOnly = false)
	private PPerson savePerson(PPerson persistable)
	{
		return personRepository.save(persistable);
	}

	@Transactional(readOnly = false)
	private PTechnicalUser saveTechnicalUser(PTechnicalUser persistable)
	{
		return technicalUserRepository.save(persistable);
	}

	@Transactional(readOnly = false)
	private POrganizationalUnit saveOrgUnit(POrganizationalUnit persistable)
	{
		return orgUnitRepository.save(persistable);
	}

	@Transactional(readOnly = false)
	private POrgUnitMember saveMember(POrgUnitMember persistable)
	{
		return orgUnitMemberRepository.save(persistable);
	}
}
