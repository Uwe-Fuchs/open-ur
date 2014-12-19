package org.openur.module.persistence.dao.rdbms;

import static org.junit.Assert.*;

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
import org.openur.module.persistence.mapper.rdbms.PermissionMapperTest;
import org.openur.module.persistence.rdbms.config.DaoSpringConfig;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.openur.module.persistence.rdbms.repository.PermissionRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositorySpringConfig.class, DaoSpringConfig.class })
@ActiveProfiles(profiles={"testRepository", "testDao"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SecurityDaoImplRdbmsTest
{
	private final String PERMISSION_NAME = "permName";
	
	@Inject
	private PermissionRepository permissionRepository;
	
	@Inject
	private ISecurityDao securityDao;

	@Test
	public void testFindPermissionById()
	{
		PApplication pApp = new PApplication("applicationName");
		PPermission persistable = new PPermission(PERMISSION_NAME, pApp);
		persistable.setDescription("permDescription");
		
		persistable = savePermission(persistable);
		
		IPermission p = securityDao.findPermissionById(persistable.getIdentifier());
		
		assertNotNull(p);	
		assertTrue(PermissionMapperTest.immutableEqualsToEntity((OpenURPermission) p, persistable));
	}

	@Test
	public void testFindPermissionByName()
	{
		OpenURApplication app = new OpenURApplicationBuilder("appName").build();
		PApplication pApp = ApplicationMapper.mapFromImmutable(app);
		PPermission persistable = new PPermission(PERMISSION_NAME, pApp);
		persistable.setDescription("permDescription");
		
		persistable = savePermission(persistable);
		pApp = persistable.getApplication();
		
		IPermission p = securityDao.findPermissionByName(PERMISSION_NAME, app);
		
		assertNotNull(p);	
		assertTrue(PermissionMapperTest.immutableEqualsToEntity((OpenURPermission) p, persistable));
	}

//	@Test
//	public void testObtainPermissionsForApp()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testObtainAllPermissions()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSecurityDaoImplRdbms()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testObtainAllRoles()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindRoleById()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindRoleByName()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindAuthOrgUnitById()
//	{
//		fail("Not yet implemented");
//	}

	@After
	public void tearDown()
		throws Exception
	{
		permissionRepository.deleteAll();
	}
	
	@Transactional(readOnly = false)
	private PPermission savePermission(PPermission permission)
	{
		return permissionRepository.save(permission);
	}
}
