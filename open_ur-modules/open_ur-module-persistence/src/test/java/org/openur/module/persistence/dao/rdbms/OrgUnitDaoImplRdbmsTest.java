package org.openur.module.persistence.dao.rdbms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Title;
import org.openur.module.persistence.dao.IOrgUnitDao;
import org.openur.module.persistence.mapper.rdbms.OrgUnitMapperTest;
import org.openur.module.persistence.mapper.rdbms.OrganizationalUnitMapper;
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
	@Transactional(readOnly = false)
	public void testFindOrgUnitAndMembersById()
	{
		POrganizationalUnit pRootOu = new POrganizationalUnit(ROOT_OU_NO, "rootOu");
		saveOrgUnit(pRootOu);

		POrganizationalUnit pSuperOu = new POrganizationalUnit(SUPER_OU_NO, "superOu");
		pSuperOu.setSuperOu(pRootOu);
		pSuperOu.setRootOu(pRootOu);
		saveOrgUnit(pSuperOu);

		POrganizationalUnit pOrgUnit1 = new POrganizationalUnit("orgUnitNo1", "staff department");
		pOrgUnit1.setSuperOu(pSuperOu);
		pOrgUnit1.setRootOu(pRootOu);

		PAddress pAddress = new PAddress("11");
		pAddress.setCity("city_1");
		pOrgUnit1.setAddress(pAddress);
		
		PPerson pPersonA = new PPerson("persNoA", "Obama");
		pPersonA.setGender(Gender.MALE);
		pPersonA.setFirstName("Barack");
		savePerson(pPersonA);

		PPerson pPersonB = new PPerson("persNoB", "Merkel");
		pPersonB.setGender(Gender.FEMALE);
		pPersonB.setTitle(Title.DR);
		pPersonB.setFirstName("Angela");
		savePerson(pPersonB);

		POrgUnitMember pMember11 = new POrgUnitMember(pOrgUnit1, pPersonA);
		POrgUnitMember pMember12 = new POrgUnitMember(pOrgUnit1, pPersonB);
		pOrgUnit1.setMembers(new HashSet<POrgUnitMember>(Arrays.asList(pMember11, pMember12)));

		saveOrgUnit(pOrgUnit1);

		IOrganizationalUnit immutable = orgUnitDao.findOrgUnitAndMembersById(pOrgUnit1.getIdentifier());
		assertNotNull(immutable);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) immutable, pOrgUnit1));

		POrganizationalUnit pSuperOu2 = new POrganizationalUnit("superOuNo2", "superOu");
		pSuperOu2.setSuperOu(pRootOu);
		pSuperOu2.setRootOu(pRootOu);
		saveOrgUnit(pSuperOu2);

		POrganizationalUnit pOrgUnit2 = new POrganizationalUnit("orgUnitNo2", "hr department");
		pOrgUnit2.setSuperOu(pSuperOu2);
		pOrgUnit2.setRootOu(pRootOu);
		pOrgUnit2.setShortName("hr");
		pOrgUnit2.setDescription("human resources");
		pOrgUnit2.setEmailAddress("hr@company.com");
		pOrgUnit2.setAddress(pAddress);

		POrgUnitMember pMember21 = new POrgUnitMember(pOrgUnit2, pPersonA);
		POrgUnitMember pMember22 = new POrgUnitMember(pOrgUnit2, pPersonB);
		pOrgUnit2.setMembers(new HashSet<POrgUnitMember>(Arrays.asList(pMember21, pMember22)));

		saveOrgUnit(pOrgUnit2);

		immutable = orgUnitDao.findOrgUnitAndMembersById(pOrgUnit2.getIdentifier());
		assertNotNull(immutable);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) immutable, pOrgUnit2));
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

		IOrganizationalUnit immutable = orgUnitDao.findOrgUnitAndMembersRolesById(pOrgUnit.getIdentifier());
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
	public void testFindOrgUnitAndMembersByNumber()
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

		POrgUnitMember pMember11 = new POrgUnitMember(pOrgUnit, pPersonA);
		POrgUnitMember pMember12 = new POrgUnitMember(pOrgUnit, pPersonB);
		pOrgUnit.setMembers(new HashSet<POrgUnitMember>(Arrays.asList(pMember11, pMember12)));
		
		saveOrgUnit(pOrgUnit);

		IOrganizationalUnit immutable = orgUnitDao.findOrgUnitAndMembersByNumber(ORG_UNIT_NO);
		assertNotNull(immutable);
		assertTrue(OrgUnitMapperTest.immutableEqualsToEntity((AuthorizableOrgUnit) immutable, pOrgUnit));
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

		IOrganizationalUnit immutable = orgUnitDao.findOrgUnitAndMembersRolesByNumber(ORG_UNIT_NO);
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
		assertTrue(allOrgUnits.contains(OrganizationalUnitMapper.mapFromEntity(pOrgUnit1, false, false)));
		assertTrue(allOrgUnits.contains(OrganizationalUnitMapper.mapFromEntity(pOrgUnit2, false, false)));
		assertTrue(allOrgUnits.contains(OrganizationalUnitMapper.mapFromEntity(pOrgUnit3, false, false)));
		assertFalse(allOrgUnits.contains(new AuthorizableOrgUnit.AuthorizableOrgUnitBuilder("orgUnitNo4", "some department").build()));
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
		
		List<IAuthorizableOrgUnit> subOrgUnits = orgUnitDao.obtainSubOrgUnitsForOrgUnit(pSuperOu.getIdentifier(), false);
		
		assertEquals(3, subOrgUnits.size());
		assertTrue(subOrgUnits.contains(OrganizationalUnitMapper.mapFromEntity(pOrgUnit1, false, false)));
		assertTrue(subOrgUnits.contains(OrganizationalUnitMapper.mapFromEntity(pOrgUnit2, false, false)));
		assertTrue(subOrgUnits.contains(OrganizationalUnitMapper.mapFromEntity(pOrgUnit3, false, false)));
		assertFalse(subOrgUnits.contains(OrganizationalUnitMapper.mapFromEntity(pOrgUnit4, false, false)));
		
		Iterator<? extends IOrganizationalUnit> iter = subOrgUnits.iterator();		
		assertEquals(iter.next().getSuperOrgUnit(), OrganizationalUnitMapper.mapFromEntity(pSuperOu, false, false));
		assertEquals(iter.next().getSuperOrgUnit(), OrganizationalUnitMapper.mapFromEntity(pSuperOu, false, false));
		assertEquals(iter.next().getSuperOrgUnit(), OrganizationalUnitMapper.mapFromEntity(pSuperOu, false, false));
	}
	
	@Test
	public void testObtainSubOrgUnitsForOrgUnitInclMembers()
	{
		
	}
	
//	@Test
//	public void testObtainSubOrgUnitsForOrgUnitInclMembersAndRoles()
//	{
//		
//	}
//		
//  @Test
//  public void testObtainRootOrgUnits()
//  { 
//  }

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
