package org.openur.module.persistence.rdbms.fixture;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.security.authorization.AuthorizableTechUser;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.security.secure_api.PermissionConstraints;
import org.openur.module.domain.userstructure.orgunit.OrgUnitFull;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.persistence.mapper.rdbms.IEntityDomainObjectMapper;
import org.openur.module.persistence.mapper.rdbms.IOrganizationalUnitMapper;
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
import org.openur.module.persistence.rdbms.entity.PUserAccount;
import org.openur.module.persistence.rdbms.repository.ApplicationRepository;
import org.openur.module.persistence.rdbms.repository.OrgUnitMemberRepository;
import org.openur.module.persistence.rdbms.repository.OrgUnitRepository;
import org.openur.module.persistence.rdbms.repository.PersonRepository;
import org.openur.module.persistence.rdbms.repository.TechnicalUserRepository;
import org.openur.module.persistence.rdbms.repository.UserAccountRepository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Ignore
@ContextConfiguration(classes = { RepositorySpringConfig.class, DaoSpringConfig.class, MapperSpringConfig.class })
@ActiveProfiles(profiles = { "testRepository", "testDao", "testMappers" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(readOnly=false)
public class RdbmsTestFixture
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
	private PTechnicalUser techUser_1;
	private PTechnicalUser techUser_2;
	
	@Inject
	private IOrganizationalUnitMapper<OrgUnitFull> organizationalUnitMapper;
	
	@Inject
	private IEntityDomainObjectMapper<PApplication, OpenURApplication> applicationMapper;
	
	@Inject
	private IEntityDomainObjectMapper<PPerson, Person> personMapper;
	
	@Inject
	private IEntityDomainObjectMapper<PPermission, OpenURPermission> permissionMapper;
	
	@Inject
	private IEntityDomainObjectMapper<PRole, OpenURRole> roleMapper;
	
	@Inject
	private IEntityDomainObjectMapper<PTechnicalUser, AuthorizableTechUser> technicalUserMapper;
	
	@Inject
	private ApplicationRepository applicationRepository;

	@Inject
	private PersonRepository personRepository;

	@Inject
	private TechnicalUserRepository technicalUserRepository;

	@Inject
	private OrgUnitRepository orgUnitRepository;

	@Inject
	private OrgUnitMemberRepository orgUnitMemberRepository;
	
	@Inject
	private UserAccountRepository userAccountRepository;
	
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
		
		saveOrgUnit(orgUnit_A);
		
		orgUnit_B = organizationalUnitMapper.mapFromDomainObject(TestObjectContainer.ORG_UNIT_B);
		orgUnit_B.setRootOu(rootOu);
		orgUnit_B.setSuperOu(superOu_1);

		member_1_B = new POrgUnitMember(orgUnit_B, person_1);
		member_1_B.addRole(role_Y);
		member_3_B = new POrgUnitMember(orgUnit_B, person_3);
		member_3_B.addRole(role_Z);
		orgUnit_B.setMembers(new HashSet<POrgUnitMember>(Arrays.asList(member_1_B, member_3_B)));
		
		saveOrgUnit(orgUnit_B);
		
		orgUnit_C = organizationalUnitMapper.mapFromDomainObject(TestObjectContainer.ORG_UNIT_C);
		orgUnit_C.setRootOu(rootOu);
		orgUnit_C.setSuperOu(superOu_2);
		
		saveOrgUnit(orgUnit_C);

		PPermission pRemoteRead_A = new PPermission(PermissionConstraints.REMOTE_READ, app_A);
		PPermission pRemoteRead_B = new PPermission(PermissionConstraints.REMOTE_READ, app_B);
//		PPermission pRemoteRead_C = new PPermission(PermissionConstraints.REMOTE_READ, app_C);
		PPermission pRemoteCheckAuth_A = new PPermission(PermissionConstraints.REMOTE_CHECK_AUTHENTICATION, app_A);
//		PPermission pRemoteCheckAuth_B = new PPermission(PermissionConstraints.REMOTE_CHECK_AUTHENTICATION, app_B);
//		PPermission pRemoteCheckAuth_C = new PPermission(PermissionConstraints.REMOTE_CHECK_AUTHENTICATION, app_C);
		PPermission pRemoteCheckPerm_A = new PPermission(PermissionConstraints.REMOTE_CHECK_PERMISSION, app_A);
//		PPermission pRemoteCheckPerm_B = new PPermission(PermissionConstraints.REMOTE_CHECK_PERMISSION, app_B);
//		PPermission pRemoteCheckPerm_C = new PPermission(PermissionConstraints.REMOTE_CHECK_PERMISSION, app_C);
		
		techUser_1 = technicalUserMapper.mapFromDomainObject(TestObjectContainer.TECH_USER_1);
		techUser_1.setPermissions(new HashSet<>(Arrays.asList(pRemoteRead_A, pRemoteCheckAuth_A, pRemoteCheckPerm_A)));
		saveTechnicalUser(techUser_1);	
		
		techUser_2 = technicalUserMapper.mapFromDomainObject(TestObjectContainer.TECH_USER_2);
		techUser_2.setPermissions(new HashSet<>(Arrays.asList(pRemoteRead_B)));
		saveTechnicalUser(techUser_2);			
		
		String someUserName = "userName_1";
		String somePassWord = "password_1";
		PUserAccount pUserAccount = new PUserAccount(person_1, someUserName, somePassWord);
		saveUserAccount(pUserAccount);	
		
		someUserName = "userName_2";
		somePassWord = "password_2";
		pUserAccount = new PUserAccount(techUser_1, someUserName, somePassWord);
		saveUserAccount(pUserAccount);
	}
	
	@Test
	@Transactional(readOnly=false)
	@Rollback(value=false)
	public void createTestDb()
	{
		assertTrue(true);
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
	
	@Transactional(readOnly = false)
	private PUserAccount saveUserAccount(PUserAccount userAccount)
	{
		return userAccountRepository.save(userAccount);
	}
}
