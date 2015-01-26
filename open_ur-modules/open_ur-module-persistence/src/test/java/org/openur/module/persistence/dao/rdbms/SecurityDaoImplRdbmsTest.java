package org.openur.module.persistence.dao.rdbms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.persistence.dao.ISecurityDao;
import org.openur.module.persistence.mapper.rdbms.ApplicationMapper;
import org.openur.module.persistence.mapper.rdbms.PermissionMapper;
import org.openur.module.persistence.mapper.rdbms.PermissionMapperTest;
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
		PApplication pApp = new PApplication("applicationName");
		
		PPermission persistable = new PPermission("permName", pApp);
		persistable.setDescription("permDescription");		
		persistable = savePermission(persistable);
		
		IPermission p = securityDao.findPermissionById(persistable.getIdentifier());
		
		assertNotNull(p);	
		assertTrue(PermissionMapperTest.immutableEqualsToEntity((OpenURPermission) p, persistable));
	}

	@Test
	public void testFindPermissionByName()
	{
		final String PERMISSION_NAME = "permName";
		
		OpenURApplication app = new OpenURApplicationBuilder("appName").build();
		PApplication pApp = ApplicationMapper.mapFromImmutable(app);
		
		PPermission persistable = new PPermission(PERMISSION_NAME, pApp);
		persistable.setDescription("permDescription");		
		persistable = savePermission(persistable);
		
		IPermission p = securityDao.findPermissionByName(PERMISSION_NAME);
		
		assertNotNull(p);	
		assertTrue(PermissionMapperTest.immutableEqualsToEntity((OpenURPermission) p, persistable));
	}
	
	@Test
	@Transactional(readOnly=false)
	//@Rollback(value=false)
	public void testObtainAllPermissions()
	{
		List<IPermission> allPermissions = securityDao.obtainAllPermissions();
		assertNotNull(allPermissions);
		assertEquals(allPermissions.size(), 0);
		
		PApplication pApp = new PApplication("applicationName");
		
		PPermission perm1 = new PPermission("permName1", pApp);
		perm1 = savePermission(perm1);
		PPermission perm2 = new PPermission("permName2", pApp);
		perm2 = savePermission(perm2);
		
		allPermissions = securityDao.obtainAllPermissions();
		assertNotNull(allPermissions);
		assertEquals(allPermissions.size(), 2);
		assertTrue(allPermissions.contains(PermissionMapper.mapFromEntity(perm1)));
		assertTrue(allPermissions.contains(PermissionMapper.mapFromEntity(perm2)));
	}

	@Test
	@Transactional(readOnly=false)
	public void testObtainPermissionsForApp()
	{
		final String appName1 = "appName1";
		final String appName2 = "appName2";
		
		PApplication pApp1 = new PApplication(appName1);		
		PPermission perm11 = new PPermission("permName11", pApp1);
		perm11 = savePermission(perm11);
		PPermission perm12 = new PPermission("permName12", pApp1);
		perm12 = savePermission(perm12);
		
		PApplication pApp2 = new PApplication(appName2);		
		PPermission perm21 = new PPermission("permName21", pApp2);
		perm21 = savePermission(perm21);
		PPermission perm22 = new PPermission("permName22", pApp2);
		perm22 = savePermission(perm22);
		
		List<IPermission> permList = securityDao.obtainPermissionsForApp(appName1);
		assertNotNull(permList);
		assertEquals(permList.size(), 2);
		assertTrue(permList.contains(PermissionMapper.mapFromEntity(perm11)));
		assertTrue(permList.contains(PermissionMapper.mapFromEntity(perm12)));		
	}

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
