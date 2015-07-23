package org.openur.module.persistence.rdbms.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.openur.module.persistence.rdbms.entity.PRole;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositorySpringConfig.class })
@ActiveProfiles(profiles = { "testRepository" })
@RunWith(SpringJUnit4ClassRunner.class)
public class RoleRepositoryTest
{
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
		PApplication pApp = new PApplication("applicationName");		
		PPermission perm1 = new PPermission("permName1", pApp);
		PPermission perm2 = new PPermission("permName2", pApp);		
		PRole pRole = new PRole("roleName");
		pRole.addPermssion(perm1);
		pRole.addPermssion(perm2);		
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
		PApplication pApp = new PApplication("applicationName");		
		PPermission perm1 = new PPermission("permName1", pApp);
		PPermission perm2 = new PPermission("permName2", pApp);		
		PRole pRole = new PRole("roleName");
		pRole.addPermssion(perm1);
		pRole.addPermssion(perm2);		
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
		PApplication pApp = new PApplication("appName");		
		PPermission perm1_A = new PPermission("permName1_A", pApp);
		PPermission perm2_A = new PPermission("permName2_A", pApp);		
		PRole pRoleA = new PRole("roleNameA");
		pRoleA.addPermssion(perm1_A);
		pRoleA.addPermssion(perm2_A);		
		saveRole(pRoleA);

		PPermission perm1_B = new PPermission("permName1_B", pApp);
		PPermission perm2_B = new PPermission("permName2_B", pApp);		
		PRole pRoleB = new PRole("roleNameB");
		pRoleB.addPermssion(perm1_B);
		pRoleB.addPermssion(perm2_B);		
		saveRole(pRoleB);
		
		//List<IRole> securityDao.obtainAllRoles();
		
		List<PRole> allRoles = roleRepository.findAll();
		
		assertNotNull(allRoles);
		assertEquals(allRoles.size(), 2);
		// put roles into standard List-Object, for Hibernate's PersistentBag seems not to call equals() in its contains-method (???)
		allRoles = new ArrayList<PRole>(allRoles);
		assertTrue(allRoles.contains(pRoleA));
		assertTrue(allRoles.contains(pRoleB));
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
