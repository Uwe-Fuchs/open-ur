package org.openur.module.persistence.dao.rdbms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authentication.IUserAccount;
import org.openur.module.domain.security.authentication.UserAccount;
import org.openur.module.domain.security.authentication.UserAccountBuilder;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.persistence.dao.ISecurityDao;
import org.openur.module.persistence.mapper.rdbms.IEntityDomainObjectMapper;
import org.openur.module.persistence.mapper.rdbms.IUserAccountMapper;
import org.openur.module.persistence.mapper.rdbms.PermissionMapper;
import org.openur.module.persistence.mapper.rdbms.RoleMapper;
import org.openur.module.persistence.mapper.rdbms.UserAccountMapper;
import org.openur.module.persistence.rdbms.config.DaoSpringConfig;
import org.openur.module.persistence.rdbms.config.MapperSpringConfig;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.openur.module.persistence.rdbms.entity.PRole;
import org.openur.module.persistence.rdbms.entity.PUserAccount;
import org.openur.module.persistence.rdbms.repository.ApplicationRepository;
import org.openur.module.persistence.rdbms.repository.PermissionRepository;
import org.openur.module.persistence.rdbms.repository.RoleRepository;
import org.openur.module.persistence.rdbms.repository.UserAccountRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositorySpringConfig.class, DaoSpringConfig.class, MapperSpringConfig.class })
@ActiveProfiles(profiles = { "testRepository", "testDao", "testMappers" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SecurityDaoImplRdbmsTest
{	
	@Inject
	private IEntityDomainObjectMapper<PPermission, OpenURPermission> permissionMapper;
	
	@Inject
	private IEntityDomainObjectMapper<PRole, OpenURRole> roleMapper;
	
	@Inject
	private IUserAccountMapper<UserAccount, UserStructureBase> userAccountMapper;
	
	@Inject
	private RoleRepository roleRepository;
	
	@Inject
	private PermissionRepository permissionRepository;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private UserAccountRepository userAccountRepository;
	
	@Inject
	private ISecurityDao securityDao;

	@Test
	public void testFindPermissionById()
	{
		PPermission persistable = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_A);		
		persistable = savePermission(persistable);
		
		IPermission p = securityDao.findPermissionById(persistable.getIdentifier());
		
		assertNotNull(p);	
		assertTrue(PermissionMapper.domainObjectEqualsToEntity((OpenURPermission) p, persistable));
	}

	@Test
	public void testFindPermission()
	{
		PPermission persistable = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_A);	
		persistable = savePermission(persistable);
		
		IPermission p = securityDao.findPermission(
			TestObjectContainer.PERMISSION_1_A.getPermissionText(), TestObjectContainer.APP_A.getApplicationName());
		
		assertNotNull(p);	
		assertTrue(PermissionMapper.domainObjectEqualsToEntity((OpenURPermission) p, persistable));
		
		p = securityDao.findPermission(
			TestObjectContainer.PERMISSION_1_A.getPermissionText(), "someApplicationName");
		
		assertNull(p);	
	}
	
	@Test
	@Transactional(readOnly=false)
	//@Rollback(value=false)
	public void testObtainAllPermissions()
	{
		List<IPermission> allPermissions = securityDao.obtainAllPermissions();
		assertNotNull(allPermissions);
		assertEquals(allPermissions.size(), 0);
		
		PPermission perm1 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_A);	
		perm1 = savePermission(perm1);
		PApplication pApp = perm1.getApplication();
		PPermission perm2 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_2_A);	
		perm2.setApplication(pApp);
		perm2 = savePermission(perm2);
		
		allPermissions = securityDao.obtainAllPermissions();
		assertNotNull(allPermissions);
		assertEquals(allPermissions.size(), 2);

		Iterator<IPermission> iter = allPermissions.iterator();
		OpenURPermission _p1 = (OpenURPermission) iter.next();
		OpenURPermission _p2 = (OpenURPermission) iter.next();
		OpenURPermission permission_1 = _p1.getPermissionText().equals(perm1.getPermissionText()) ? _p1 : _p2;
		OpenURPermission permission_2 = _p1.getPermissionText().equals(perm1.getPermissionText()) ? _p2 : _p1;

		assertTrue(PermissionMapper.domainObjectEqualsToEntity(permission_1, perm1));
		assertTrue(PermissionMapper.domainObjectEqualsToEntity(permission_2, perm2));
	}

	@Test
	@Transactional(readOnly=false)
	public void testObtainPermissionsForApp()
	{		
		PPermission perm_1_A = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_A);	
		perm_1_A = savePermission(perm_1_A);
		PApplication app_A = perm_1_A.getApplication();
		PPermission perm_2_A = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_2_A);	
		perm_2_A.setApplication(app_A);
		perm_2_A = savePermission(perm_2_A);
		
		PPermission perm_1_B = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_B);	
		perm_1_B = savePermission(perm_1_B);
		PApplication app_B = perm_1_B.getApplication();
		PPermission perm_2_B = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_2_B);	
		perm_2_B.setApplication(app_B);
		perm_2_B = savePermission(perm_2_B);
		
		List<IPermission> permList = securityDao.obtainPermissionsForApp(TestObjectContainer.APP_A.getApplicationName());
		assertNotNull(permList);
		assertEquals(permList.size(), 2);

		Iterator<IPermission> iter = permList.iterator();
		OpenURPermission _p1 = (OpenURPermission) iter.next();
		OpenURPermission _p2 = (OpenURPermission) iter.next();
		OpenURPermission permission_1 = _p1.getPermissionText().equals(perm_1_A.getPermissionText()) ? _p1 : _p2;
		OpenURPermission permission_2 = _p1.getPermissionText().equals(perm_1_A.getPermissionText()) ? _p2 : _p1;

		assertTrue(PermissionMapper.domainObjectEqualsToEntity(permission_1, perm_1_A));
		assertTrue(PermissionMapper.domainObjectEqualsToEntity(permission_2, perm_2_A));
	}

	@Test
	@Transactional(readOnly=false)
	public void testFindRoleById()
	{
		PPermission perm1 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_A);	
		PApplication pApp = perm1.getApplication();
		PPermission perm2 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_2_A);	
		perm2.setApplication(pApp);
		PRole pRole = roleMapper.mapFromDomainObject(TestObjectContainer.ROLE_X);
		pRole.setPermissions(new HashSet<>(Arrays.asList(perm1, perm2)));
		saveRole(pRole);
		
		IRole p = securityDao.findRoleById(pRole.getIdentifier());
		
		assertNotNull(p);	
		assertTrue(RoleMapper.domainObjectEqualsToEntity((OpenURRole) p, pRole));
	}

	@Test
	@Transactional(readOnly=false)
	public void testFindRoleByName()
	{
		PPermission perm1 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_A);	
		PApplication pApp = perm1.getApplication();
		PPermission perm2 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_2_A);	
		perm2.setApplication(pApp);
		PRole pRole = roleMapper.mapFromDomainObject(TestObjectContainer.ROLE_X);
		pRole.setPermissions(new HashSet<>(Arrays.asList(perm1, perm2)));
		saveRole(pRole);
		
		IRole p = securityDao.findRoleByName(pRole.getRoleName());	
		
		assertNotNull(p);	
		assertTrue(RoleMapper.domainObjectEqualsToEntity((OpenURRole) p, pRole));
	}

	@Test
	@Transactional(readOnly=false)
	public void testObtainAllRoles()
	{
		PPermission perm_1_A = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_A);	
		PApplication app_A = perm_1_A.getApplication();
		PPermission perm_2_A = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_2_A);	
		perm_2_A.setApplication(app_A);
		PRole pRole_X = roleMapper.mapFromDomainObject(TestObjectContainer.ROLE_X);
		pRole_X.setPermissions(new HashSet<>(Arrays.asList(perm_1_A, perm_2_A)));
		saveRole(pRole_X);

		PPermission perm_1_B = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_B);	
		PApplication app_B = perm_1_B.getApplication();
		PPermission perm_2_B = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_2_B);	
		perm_2_B.setApplication(app_B);
		PRole pRole_Y = roleMapper.mapFromDomainObject(TestObjectContainer.ROLE_Y);
		pRole_Y.setPermissions(new HashSet<>(Arrays.asList(perm_1_B, perm_2_B)));
		saveRole(pRole_Y);
		
		List<IRole> allRoles = securityDao.obtainAllRoles();
		assertNotNull(allRoles);
		assertEquals(allRoles.size(), 2);
		
		Iterator<IRole> iter = allRoles.iterator();
		OpenURRole _r1 = (OpenURRole) iter.next();
		OpenURRole _r2 = (OpenURRole) iter.next();
		OpenURRole role_X = _r1.getRoleName().equals(pRole_X.getRoleName()) ? _r1 : _r2;
		OpenURRole role_Y = _r2.getRoleName().equals(pRole_Y.getRoleName()) ? _r2 : _r1;

		assertTrue(RoleMapper.domainObjectEqualsToEntity(role_X, pRole_X));
		assertTrue(RoleMapper.domainObjectEqualsToEntity(role_Y, pRole_Y));
	}

	@Test
	@Transactional(readOnly=false)
	public void testFindUserAccount()
	{
		String saltBase = "MyVerySecretPersonalSalt";	
		String someUserName = "someUserName";
		String somePassWord = "somePassWord";
		UserAccount PERSON_1_ACCOUNT = new UserAccountBuilder(someUserName, somePassWord)
				.identifier(TestObjectContainer.PERSON_UUID_1)
				.salt(saltBase)
				.build();
		
		PUserAccount persistable = userAccountMapper.mapFromDomainObject(PERSON_1_ACCOUNT, TestObjectContainer.PERSON_1);
		persistable = saveUserAccount(persistable);
		
		IUserAccount u = securityDao.findUserAccountByUserName(someUserName);
		
		assertNotNull(u);
		assertTrue(UserAccountMapper.domainObjectEqualsToEntity((UserAccount) u, persistable));
		
		u = securityDao.findUserAccountByUserName("someUnknownUserName");
		assertNull(u);
	}

	@After
	@Transactional(readOnly = false)
	public void tearDown()
		throws Exception
	{
		roleRepository.deleteAll();
		permissionRepository.deleteAll();
		userAccountRepository.deleteAll();
		applicationRepository.deleteAll();
	}
	
	@Transactional(readOnly = false)
	private PPermission savePermission(PPermission permission)
	{
		return permissionRepository.saveAndFlush(permission);
	}
	
	@Transactional(readOnly = false)
	private PRole saveRole(PRole role)
	{
		return roleRepository.saveAndFlush(role);
	}
	
	@Transactional(readOnly = false)
	private PUserAccount saveUserAccount(PUserAccount userAccount)
	{
		return userAccountRepository.saveAndFlush(userAccount);
	}
}
