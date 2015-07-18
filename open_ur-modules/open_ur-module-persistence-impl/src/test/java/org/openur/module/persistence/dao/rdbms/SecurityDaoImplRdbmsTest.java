package org.openur.module.persistence.dao.rdbms;

import static org.junit.Assert.assertEquals;
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
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.persistence.dao.ISecurityDao;
import org.openur.module.persistence.mapper.rdbms.PermissionMapper;
import org.openur.module.persistence.mapper.rdbms.RoleMapper;
import org.openur.module.persistence.rdbms.config.DaoSpringConfig;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.openur.module.persistence.rdbms.entity.PRole;
import org.openur.module.persistence.rdbms.repository.ApplicationRepository;
import org.openur.module.persistence.rdbms.repository.PermissionRepository;
import org.openur.module.persistence.rdbms.repository.RoleRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositorySpringConfig.class, DaoSpringConfig.class })
@ActiveProfiles(profiles={"testRepository", "testDao"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SecurityDaoImplRdbmsTest
{	
	@Inject
	private RoleRepository roleRepository;
	
	@Inject
	private PermissionRepository permissionRepository;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private ISecurityDao securityDao;

	@Test
	public void testFindPermissionById()
	{
		PPermission persistable = PermissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_1_A);		
		persistable = savePermission(persistable);
		
		IPermission p = securityDao.findPermissionById(persistable.getIdentifier());
		
		assertNotNull(p);	
		assertTrue(PermissionMapper.immutableEqualsToEntity((OpenURPermission) p, persistable));
	}

	@Test
	public void testFindPermissionByName()
	{
		PPermission persistable = PermissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_1_A);	
		persistable = savePermission(persistable);
		
		IPermission p = securityDao.findPermissionByName(TestObjectContainer.PERMISSION_1_A.getPermissionName());
		
		assertNotNull(p);	
		assertTrue(PermissionMapper.immutableEqualsToEntity((OpenURPermission) p, persistable));
	}
	
	@Test
	@Transactional(readOnly=false)
	//@Rollback(value=false)
	public void testObtainAllPermissions()
	{
		List<IPermission> allPermissions = securityDao.obtainAllPermissions();
		assertNotNull(allPermissions);
		assertEquals(allPermissions.size(), 0);
		
		PPermission perm1 = PermissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_1_A);	
		perm1 = savePermission(perm1);
		PApplication pApp = perm1.getApplication();
		PPermission perm2 = PermissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_2_A);	
		perm2.setApplication(pApp);
		perm2 = savePermission(perm2);
		
		allPermissions = securityDao.obtainAllPermissions();
		assertNotNull(allPermissions);
		assertEquals(allPermissions.size(), 2);

		Iterator<IPermission> iter = allPermissions.iterator();
		OpenURPermission _p1 = (OpenURPermission) iter.next();
		OpenURPermission _p2 = (OpenURPermission) iter.next();
		OpenURPermission permission_1 = _p1.getPermissionName().equals(perm1.getPermissionName()) ? _p1 : _p2;
		OpenURPermission permission_2 = _p1.getPermissionName().equals(perm1.getPermissionName()) ? _p2 : _p1;

		assertTrue(PermissionMapper.immutableEqualsToEntity(permission_1, perm1));
		assertTrue(PermissionMapper.immutableEqualsToEntity(permission_2, perm2));
	}

	@Test
	@Transactional(readOnly=false)
	public void testObtainPermissionsForApp()
	{		
		PPermission perm_1_A = PermissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_1_A);	
		perm_1_A = savePermission(perm_1_A);
		PApplication app_A = perm_1_A.getApplication();
		PPermission perm_2_A = PermissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_2_A);	
		perm_2_A.setApplication(app_A);
		perm_2_A = savePermission(perm_2_A);
		
		PPermission perm_1_B = PermissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_1_B);	
		perm_1_B = savePermission(perm_1_B);
		PApplication app_B = perm_1_B.getApplication();
		PPermission perm_2_B = PermissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_2_B);	
		perm_2_B.setApplication(app_B);
		perm_2_B = savePermission(perm_2_B);
		
		List<IPermission> permList = securityDao.obtainPermissionsForApp(TestObjectContainer.APP_A.getApplicationName());
		assertNotNull(permList);
		assertEquals(permList.size(), 2);

		Iterator<IPermission> iter = permList.iterator();
		OpenURPermission _p1 = (OpenURPermission) iter.next();
		OpenURPermission _p2 = (OpenURPermission) iter.next();
		OpenURPermission permission_1 = _p1.getPermissionName().equals(perm_1_A.getPermissionName()) ? _p1 : _p2;
		OpenURPermission permission_2 = _p1.getPermissionName().equals(perm_1_A.getPermissionName()) ? _p2 : _p1;

		assertTrue(PermissionMapper.immutableEqualsToEntity(permission_1, perm_1_A));
		assertTrue(PermissionMapper.immutableEqualsToEntity(permission_2, perm_2_A));
	}

	@Test
	@Transactional(readOnly=false)
	public void testFindRoleById()
	{
		PPermission perm1 = PermissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_1_A);	
		PApplication pApp = perm1.getApplication();
		PPermission perm2 = PermissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_2_A);	
		perm2.setApplication(pApp);
		PRole pRole = RoleMapper.mapFromImmutable(TestObjectContainer.ROLE_X);
		pRole.setPermissions(new HashSet<>(Arrays.asList(perm1, perm2)));
		saveRole(pRole);
		
		IRole p = securityDao.findRoleById(pRole.getIdentifier());
		
		assertNotNull(p);	
		assertTrue(RoleMapper.immutableEqualsToEntity((OpenURRole) p, pRole));
	}

	@Test
	@Transactional(readOnly=false)
	public void testFindRoleByName()
	{
		PPermission perm1 = PermissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_1_A);	
		PApplication pApp = perm1.getApplication();
		PPermission perm2 = PermissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_2_A);	
		perm2.setApplication(pApp);
		PRole pRole = RoleMapper.mapFromImmutable(TestObjectContainer.ROLE_X);
		pRole.setPermissions(new HashSet<>(Arrays.asList(perm1, perm2)));
		saveRole(pRole);
		
		IRole p = securityDao.findRoleByName(pRole.getRoleName());	
		
		assertNotNull(p);	
		assertTrue(RoleMapper.immutableEqualsToEntity((OpenURRole) p, pRole));
	}


	@Test
	@Transactional(readOnly=false)
	public void testObtainAllRoles()
	{
		PPermission perm_1_A = PermissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_1_A);	
		PApplication app_A = perm_1_A.getApplication();
		PPermission perm_2_A = PermissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_2_A);	
		perm_2_A.setApplication(app_A);
		PRole pRole_X = RoleMapper.mapFromImmutable(TestObjectContainer.ROLE_X);
		pRole_X.setPermissions(new HashSet<>(Arrays.asList(perm_1_A, perm_2_A)));
		saveRole(pRole_X);

		PPermission perm_1_B = PermissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_1_B);	
		PApplication app_B = perm_1_B.getApplication();
		PPermission perm_2_B = PermissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_2_B);	
		perm_2_B.setApplication(app_B);
		PRole pRole_Y = RoleMapper.mapFromImmutable(TestObjectContainer.ROLE_Y);
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

		assertTrue(RoleMapper.immutableEqualsToEntity(role_X, pRole_X));
		assertTrue(RoleMapper.immutableEqualsToEntity(role_Y, pRole_Y));
	}

	@After
	@Transactional(readOnly = false)
	public void tearDown()
		throws Exception
	{
		roleRepository.deleteAll();
		permissionRepository.deleteAll();
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
}
