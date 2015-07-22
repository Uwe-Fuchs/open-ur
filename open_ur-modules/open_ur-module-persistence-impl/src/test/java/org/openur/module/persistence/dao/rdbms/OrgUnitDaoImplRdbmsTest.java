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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IAuthorizableMember;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.persistence.dao.IOrgUnitDao;
import org.openur.module.persistence.mapper.rdbms.IApplicationMapper;
import org.openur.module.persistence.mapper.rdbms.IOrgUnitMemberMapper;
import org.openur.module.persistence.mapper.rdbms.IOrganizationalUnitMapper;
import org.openur.module.persistence.mapper.rdbms.IPermissionMapper;
import org.openur.module.persistence.mapper.rdbms.IPersonMapper;
import org.openur.module.persistence.mapper.rdbms.IRoleMapper;
import org.openur.module.persistence.mapper.rdbms.OrganizationalUnitMapper;
import org.openur.module.persistence.rdbms.config.DaoSpringConfig;
import org.openur.module.persistence.rdbms.config.MapperSpringConfig;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.POrgUnitMember;
import org.openur.module.persistence.rdbms.entity.POrganizationalUnit;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.openur.module.persistence.rdbms.entity.PPerson;
import org.openur.module.persistence.rdbms.entity.PRole;
import org.openur.module.persistence.rdbms.entity.PTechnicalUser;
import org.openur.module.persistence.rdbms.repository.AddressRepository;
import org.openur.module.persistence.rdbms.repository.ApplicationRepository;
import org.openur.module.persistence.rdbms.repository.OrgUnitMemberRepository;
import org.openur.module.persistence.rdbms.repository.OrgUnitRepository;
import org.openur.module.persistence.rdbms.repository.PersonRepository;
import org.openur.module.persistence.rdbms.repository.TechnicalUserRepository;
//import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositorySpringConfig.class, DaoSpringConfig.class, MapperSpringConfig.class })
@ActiveProfiles(profiles = { "testRepository", "testDao", "testMappers" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(readOnly=false)
public class OrgUnitDaoImplRdbmsTest
{
	private POrganizationalUnit orgUnit_A;
	private POrganizationalUnit orgUnit_B;
	private POrganizationalUnit orgUnit_C;
	private POrganizationalUnit superOu_1;
	private POrganizationalUnit superOu_2;
	private POrganizationalUnit rootOu;
	private POrgUnitMember member_1_A;
	private POrgUnitMember member_2_A;
	private POrgUnitMember member_1_B;
	private POrgUnitMember member_3_B;
	
	@Inject
	private IOrganizationalUnitMapper<AuthorizableOrgUnit> organizationalUnitMapper;
	
	@Inject
	private IOrgUnitMemberMapper<AuthorizableMember> orgUnitMemberMapper;
	
	@Inject
	private IApplicationMapper<OpenURApplication> applicationMapper;
	
	@Inject
	private IPersonMapper<Person> personMapper;
	
	@Inject
	private IPermissionMapper<OpenURPermission> permissionMapper;
	
	@Inject
	private IRoleMapper<OpenURRole> roleMapper;
	
	@Inject
	private ApplicationRepository applicationRepository;

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
	
	@Before
	public void setUp()
	{
		PApplication app_A = applicationMapper.mapFromDomainObject(TestObjectContainer.APP_A);
		PApplication app_B = applicationMapper.mapFromDomainObject(TestObjectContainer.APP_B);
		PApplication app_C = applicationMapper.mapFromDomainObject(TestObjectContainer.APP_C);
		
		PPermission perm_1_A = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_A);	
		perm_1_A.setApplication(app_A);
		PPermission perm_2_A = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_2_A);	
		perm_2_A.setApplication(app_A);
		PRole role_X = roleMapper.mapFromDomainObject(TestObjectContainer.ROLE_X);
		role_X.setPermissions(new HashSet<>(Arrays.asList(perm_1_A, perm_2_A)));

		PPermission perm_1_B = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_B);	
		perm_1_B.setApplication(app_B);
		PPermission perm_2_B = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_2_B);	
		perm_2_B.setApplication(app_B);
		PRole role_Y = roleMapper.mapFromDomainObject(TestObjectContainer.ROLE_Y);
		role_Y.setPermissions(new HashSet<>(Arrays.asList(perm_1_B, perm_2_B)));

		PPermission perm_1_C = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_C);	
		perm_1_C.setApplication(app_C);
		PPermission perm_2_C = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_2_C);	
		perm_2_C.setApplication(app_C);
		PRole role_Z = roleMapper.mapFromDomainObject(TestObjectContainer.ROLE_Z);
		role_Z.setPermissions(new HashSet<>(Arrays.asList(perm_1_C, perm_2_C)));

		PPerson person_1 = personMapper.mapFromDomainObject(TestObjectContainer.PERSON_1);
		person_1.setApplications(new HashSet<>(Arrays.asList(app_A, app_B)));
		person_1 = savePerson(person_1);

		PPerson person_2 = personMapper.mapFromDomainObject(TestObjectContainer.PERSON_2);
		person_2.setApplications(new HashSet<>(Arrays.asList(app_B, app_C)));
		person_2 = savePerson(person_2);

		PPerson person_3 = personMapper.mapFromDomainObject(TestObjectContainer.PERSON_3);
		person_3.setApplications(new HashSet<>(Arrays.asList(app_A, app_C)));
		person_3 = savePerson(person_3);
		
		rootOu = organizationalUnitMapper.mapFromDomainObject(TestObjectContainer.ROOT_OU);	
		saveOrgUnit(rootOu);

		superOu_1 = organizationalUnitMapper.mapFromDomainObject(TestObjectContainer.SUPER_OU_1);
		superOu_1.setRootOu(rootOu);
		superOu_1.setSuperOu(rootOu);
		POrgUnitMember member_3_s1 = new POrgUnitMember(superOu_1, person_3);
		superOu_1.setMembers(new HashSet<>(Arrays.asList(member_3_s1)));
		saveOrgUnit(superOu_1);

		superOu_2 = organizationalUnitMapper.mapFromDomainObject(TestObjectContainer.SUPER_OU_2);
		superOu_2.setRootOu(rootOu);
		superOu_2.setSuperOu(rootOu);
		saveOrgUnit(superOu_2);
		
		orgUnit_A = organizationalUnitMapper.mapFromDomainObject(TestObjectContainer.ORG_UNIT_A);
		orgUnit_A.setRootOu(rootOu);
		orgUnit_A.setSuperOu(superOu_1);

		member_1_A = new POrgUnitMember(orgUnit_A, person_1);
		member_1_A.addRole(role_X);
		member_2_A = new POrgUnitMember(orgUnit_A, person_2);
		member_2_A.addRole(role_Y);
		orgUnit_A.setMembers(new HashSet<POrgUnitMember>(Arrays.asList(member_1_A, member_2_A)));
		
		orgUnit_A = saveOrgUnit(orgUnit_A);
		
		orgUnit_B = organizationalUnitMapper.mapFromDomainObject(TestObjectContainer.ORG_UNIT_B);
		orgUnit_B.setRootOu(rootOu);
		orgUnit_B.setSuperOu(superOu_1);

		member_1_B = new POrgUnitMember(orgUnit_B, person_1);
		member_1_B.addRole(role_Y);
		member_3_B = new POrgUnitMember(orgUnit_B, person_3);
		member_3_B.addRole(role_Z);
		orgUnit_B.setMembers(new HashSet<POrgUnitMember>(Arrays.asList(member_1_B, member_3_B)));
		
		orgUnit_B = saveOrgUnit(orgUnit_B);
		
		orgUnit_C = organizationalUnitMapper.mapFromDomainObject(TestObjectContainer.ORG_UNIT_C);
		orgUnit_C.setRootOu(rootOu);
		orgUnit_C.setSuperOu(superOu_2);
		
		orgUnit_C = saveOrgUnit(orgUnit_C);
	}
	
	@Test
	public void testFindOrgUnitById()
	{
		IOrganizationalUnit immutable = orgUnitDao.findOrgUnitById(orgUnit_C.getIdentifier());
		assertNotNull(immutable);
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) immutable, orgUnit_C));
	}

	@Test
	public void testFindOrgUnitAndMembersRolesById()
	{
		IOrganizationalUnit immutable = orgUnitDao.findOrgUnitAndMembersById(orgUnit_A.getIdentifier());
		assertNotNull(immutable);
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) immutable, orgUnit_A));
	}

	@Test
	public void testFindOrgUnitByNumber()
	{
		IOrganizationalUnit immutable = orgUnitDao.findOrgUnitByNumber(TestObjectContainer.ORG_UNIT_NUMBER_C);
		assertNotNull(immutable);
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) immutable, orgUnit_C));

		immutable = orgUnitDao.findOrgUnitByNumber(TestObjectContainer.ROOT_OU_NUMBER);
		assertNotNull(immutable);
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) immutable, rootOu));

		immutable = orgUnitDao.findOrgUnitByNumber(TestObjectContainer.SUPER_OU_NUMBER_2);
		assertNotNull(immutable);
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) immutable, superOu_2));
	}

	@Test
	public void testFindOrgUnitAndMembersRolesByNumber()
	{
		IOrganizationalUnit immutable = orgUnitDao.findOrgUnitAndMembersByNumber(TestObjectContainer.ORG_UNIT_NUMBER_A);
		assertNotNull(immutable);
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) immutable, orgUnit_A));
	}
	
	@Test
	public void testObtainAllOrgUnitsInclMembers()
	{
		List<IAuthorizableOrgUnit> allOrgUnits = orgUnitDao.obtainAllOrgUnitsInclMembers();
		
		assertEquals(6, allOrgUnits.size());
		IAuthorizableOrgUnit orgUnit1 = findOrgUnitInList(allOrgUnits, TestObjectContainer.ORG_UNIT_NUMBER_A);
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) orgUnit1, orgUnit_A));
		IAuthorizableOrgUnit orgUnit2 = findOrgUnitInList(allOrgUnits, TestObjectContainer.ORG_UNIT_NUMBER_B);
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) orgUnit2, orgUnit_B));
		IAuthorizableOrgUnit orgUnit3 = findOrgUnitInList(allOrgUnits, TestObjectContainer.ORG_UNIT_NUMBER_C);
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) orgUnit3, orgUnit_C));
		assertNull(findOrgUnitInList(allOrgUnits, "orgUnitNo4"));
	}
	
	@Test
	public void testObtainAllOrgUnits()
	{
		POrganizationalUnit pOrgUnit1 = new POrganizationalUnit("orgUnitNo1", "staff department");
		saveOrgUnit(pOrgUnit1);
		POrganizationalUnit pOrgUnit2 = new POrganizationalUnit("orgUnitNo2", "hr department");
		saveOrgUnit(pOrgUnit2);
		POrganizationalUnit pOrgUnit3 = new POrganizationalUnit("orgUnitNo3", "it department");
		saveOrgUnit(pOrgUnit3);
		
		List<IAuthorizableOrgUnit> allOrgUnits = orgUnitDao.obtainAllOrgUnits();

		IAuthorizableOrgUnit orgUnit1 = findOrgUnitInList(allOrgUnits, "orgUnitNo1");
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) orgUnit1, pOrgUnit1));
		IAuthorizableOrgUnit orgUnit2 = findOrgUnitInList(allOrgUnits, "orgUnitNo2");
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) orgUnit2, pOrgUnit2));
		IAuthorizableOrgUnit orgUnit3 = findOrgUnitInList(allOrgUnits, "orgUnitNo3");
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) orgUnit3, pOrgUnit3));
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
		POrganizationalUnit pRootOu = new POrganizationalUnit("rootOu", "rootOu");
		saveOrgUnit(pRootOu);

		POrganizationalUnit pSuperOu = new POrganizationalUnit("superOu", "superOu");
		pSuperOu.setSuperOu(pRootOu);
		pSuperOu.setRootOu(pRootOu);
		saveOrgUnit(pSuperOu);

		POrganizationalUnit pOrgUnit1 = new POrganizationalUnit("orgUnit1", "staff department");
		pOrgUnit1.setSuperOu(pSuperOu);
		pOrgUnit1.setRootOu(pRootOu);
		saveOrgUnit(pOrgUnit1);
		
		POrganizationalUnit pOrgUnit2 = new POrganizationalUnit("orgUnit2", "hr department");
		pOrgUnit2.setSuperOu(pSuperOu);
		pOrgUnit2.setRootOu(pRootOu);
		saveOrgUnit(pOrgUnit2);
		
		POrganizationalUnit pOrgUnit3 = new POrganizationalUnit("orgUnit3", "it department");
		pOrgUnit3.setSuperOu(pSuperOu);
		pOrgUnit3.setRootOu(pRootOu);
		saveOrgUnit(pOrgUnit3);
		
		POrganizationalUnit pOrgUnit4 = new POrganizationalUnit("orgUnit4", "some department");
		pOrgUnit4.setSuperOu(pRootOu);
		pOrgUnit4.setRootOu(pRootOu);
		saveOrgUnit(pOrgUnit4);
		
		List<IAuthorizableOrgUnit> subOrgUnits = orgUnitDao.obtainSubOrgUnitsForOrgUnit(pSuperOu.getIdentifier());
		
		assertEquals(3, subOrgUnits.size());
		IAuthorizableOrgUnit orgUnit1 = findOrgUnitInList(subOrgUnits, "orgUnit1");
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) orgUnit1, pOrgUnit1));
		IAuthorizableOrgUnit orgUnit2 = findOrgUnitInList(subOrgUnits, "orgUnit2");
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) orgUnit2, pOrgUnit2));
		IAuthorizableOrgUnit orgUnit3 = findOrgUnitInList(subOrgUnits, "orgUnit3");
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) orgUnit3, pOrgUnit3));
		assertNull(findOrgUnitInList(subOrgUnits, "orgUnit4"));
		
		for (IAuthorizableOrgUnit orgUnit : subOrgUnits)
		{
			assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) orgUnit.getSuperOrgUnit(), pSuperOu));
		}
		
		subOrgUnits = orgUnitDao.obtainSubOrgUnitsForOrgUnit(pRootOu.getIdentifier());
		assertEquals(2, subOrgUnits.size());
		IAuthorizableOrgUnit superOu = findOrgUnitInList(subOrgUnits, "superOu");
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) superOu, pSuperOu));
		IAuthorizableOrgUnit orgUnit4 = findOrgUnitInList(subOrgUnits, "orgUnit4");
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) orgUnit4, pOrgUnit4));
		
		for (IAuthorizableOrgUnit orgUnit : subOrgUnits)
		{
			assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) orgUnit.getSuperOrgUnit(), pRootOu));
		}
	}
	
	@Test
	@Transactional(readOnly=false)
	//@Rollback(value=false)
	public void testObtainSubOrgUnitsForOrgUnitInclMembers()
	{
		List<IAuthorizableOrgUnit> subOrgUnits = orgUnitDao.obtainSubOrgUnitsForOrgUnitInclMembers(superOu_1.getIdentifier());
		
		assertEquals(2, subOrgUnits.size());
		IAuthorizableOrgUnit orgUnit1 = findOrgUnitInList(subOrgUnits, TestObjectContainer.ORG_UNIT_NUMBER_A);
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) orgUnit1, orgUnit_A));
		IAuthorizableOrgUnit orgUnit2 = findOrgUnitInList(subOrgUnits, TestObjectContainer.ORG_UNIT_NUMBER_B);
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) orgUnit2, orgUnit_B));
		assertNull(findOrgUnitInList(subOrgUnits, "orgUnitNo4"));
		
		for (IAuthorizableOrgUnit orgUnit : subOrgUnits)
		{
			assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) orgUnit.getSuperOrgUnit(), superOu_1));
		}
		
		subOrgUnits = orgUnitDao.obtainSubOrgUnitsForOrgUnitInclMembers(superOu_2.getIdentifier());
		assertEquals(1, subOrgUnits.size());
		IAuthorizableOrgUnit orgUnit3 = findOrgUnitInList(subOrgUnits, TestObjectContainer.ORG_UNIT_NUMBER_C);
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) orgUnit3, orgUnit_C));
		
		for (IAuthorizableOrgUnit orgUnit : subOrgUnits)
		{
			assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) orgUnit.getSuperOrgUnit(), superOu_2));
		}
		
		subOrgUnits = orgUnitDao.obtainSubOrgUnitsForOrgUnitInclMembers(orgUnit_A.getIdentifier());
		assertEquals(0, subOrgUnits.size());
	}

  @Test
  public void testObtainRootOrgUnits()
  {
		List<IAuthorizableOrgUnit> rootOrgUnits = orgUnitDao.obtainRootOrgUnits();
		assertEquals(1, rootOrgUnits.size());
		IAuthorizableOrgUnit rootOu = findOrgUnitInList(rootOrgUnits, TestObjectContainer.ROOT_OU_NUMBER);
		assertTrue(OrganizationalUnitMapper.domainObjectEqualsToEntity((AuthorizableOrgUnit) rootOu, this.rootOu));
		
		assertNull(findOrgUnitInList(rootOrgUnits, TestObjectContainer.ORG_UNIT_NUMBER_A));
		assertNull(findOrgUnitInList(rootOrgUnits, TestObjectContainer.SUPER_OU_NUMBER_1));
  }

  @Test
  public void testFindMembersForOrgUnit()
  {
		List<IAuthorizableMember> members = orgUnitDao.findMembersForOrgUnit(orgUnit_A.getIdentifier());
		assertEquals(2, members.size());
		assertTrue(members.contains(orgUnitMemberMapper.mapFromEntity(member_1_A, orgUnit_A.getIdentifier(), false)));
		assertTrue(members.contains(orgUnitMemberMapper.mapFromEntity(member_2_A, orgUnit_A.getIdentifier(), false)));
		
		members = orgUnitDao.findMembersForOrgUnit(orgUnit_C.getIdentifier());
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
		applicationRepository.deleteAll();
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

	@Transactional(readOnly = false)
	private PApplication saveApplication(PApplication persistable)
	{
		return applicationRepository.save(persistable);
	}
}
