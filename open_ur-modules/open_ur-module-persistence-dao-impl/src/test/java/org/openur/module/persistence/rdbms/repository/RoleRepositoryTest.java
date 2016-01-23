package org.openur.module.persistence.rdbms.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.persistence.mapper.rdbms.IPermissionMapper;
import org.openur.module.persistence.mapper.rdbms.IRoleMapper;
import org.openur.module.persistence.rdbms.config.DaoSpringConfig;
import org.openur.module.persistence.rdbms.config.MapperSpringConfig;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.openur.module.persistence.rdbms.entity.PRole;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositorySpringConfig.class, DaoSpringConfig.class, MapperSpringConfig.class })
@ActiveProfiles(profiles = { "testRepository", "testDao", "testMappers" })
@RunWith(SpringJUnit4ClassRunner.class)
public class RoleRepositoryTest
{
	@Inject
	private IPermissionMapper<OpenURPermission> permissionMapper;
	
	@Inject
	private IRoleMapper<OpenURRole> roleMapper;
	
	@Inject
	private RoleRepository roleRepository;
	
	@Inject
	private PermissionRepository permissionRepository;
	
	@Inject
	private ApplicationRepository applicationRepository;

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
		
		PRole resultRole = roleRepository.findOne(pRole.getId());	
		assertNotNull(resultRole);
		assertEquals(resultRole, pRole);
		assertNotNull(resultRole.getPermissions());
		assertEquals(resultRole.getPermissions().size(), 2);
		// put permissions into standard List-Object, for Hibernate's PersistentBag seems not to call equals() in its contains-method (???)
		List<PPermission> permList = new ArrayList<PPermission>(resultRole.getPermissions());
		assertNotNull(permList);
		assertEquals(permList.size(), 2);
		assertTrue(permList.contains(perm1));
		assertTrue(permList.contains(perm2));
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
		
		PRole resultRole = roleRepository.findRoleByRoleName(pRole.getRoleName());	
		assertNotNull(resultRole);
		assertEquals(resultRole, pRole);
		assertNotNull(resultRole.getPermissions());
		assertEquals(resultRole.getPermissions().size(), 2);
		// put permissions into standard List-Object, for Hibernate's PersistentBag seems not to call equals() in its contains-method (???)
		List<PPermission> permList = new ArrayList<PPermission>(resultRole.getPermissions());
		assertNotNull(permList);
		assertEquals(permList.size(), 2);
		assertTrue(permList.contains(perm1));
		assertTrue(permList.contains(perm2));
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
		
		List<PRole> allRoles = roleRepository.findAll();
		
		assertNotNull(allRoles);
		assertEquals(allRoles.size(), 2);
		// put roles into standard List-Object, for Hibernate's PersistentBag seems not to call equals() in its contains-method (???)
		allRoles = new ArrayList<PRole>(allRoles);
		assertTrue(allRoles.contains(pRole_X));
		assertTrue(allRoles.contains(pRole_Y));
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
