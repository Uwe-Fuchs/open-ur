package org.openur.module.persistence.rdbms.repository;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.module.persistence.rdbms.config.DaoSpringConfig;
import org.openur.module.persistence.rdbms.config.MapperSpringConfig;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositorySpringConfig.class, DaoSpringConfig.class, MapperSpringConfig.class })
@ActiveProfiles(profiles = { "testRepository", "testDao", "testMappers" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PermissionRepositoryTest
{
	@Inject
	private PermissionRepository permissionRepository;

	@Inject
	private ApplicationRepository applicationRepository;

	@Test
	@Transactional(readOnly=false)
	public void testFindPermissionsByApplicationApplicationName()
	{
		PApplication pApp = new PApplication("applicationName");	
		
		PPermission perm1 = new PPermission("permText1", pApp);
		savePermission(perm1);		

		PPermission perm2 = new PPermission("permText2", pApp);
		savePermission(perm2);
		
		List<PPermission> resultList = permissionRepository.findPermissionsByApplicationApplicationName(pApp.getApplicationName());
		assertNotNull(resultList);
		assertEquals(resultList.size(), 2);
		assertTrue(resultList.contains(perm1));
		assertTrue(resultList.contains(perm2));
		
		resultList = permissionRepository.findPermissionsByApplicationApplicationName("someApplicationName");
		assertTrue(resultList.isEmpty());
	}

	@Test
	@Transactional(readOnly=false)
	public void testFindPermission()
	{
		PApplication pApp = new PApplication("applicationName");	
		
		PPermission perm1 = new PPermission("permText1", pApp);
		savePermission(perm1);		

		PPermission perm2 = new PPermission("permText2", pApp);
		savePermission(perm2);
		
		PPermission result = permissionRepository.findPermission(perm1.getPermissionText(), pApp.getApplicationName());
		assertNotNull(result);
		assertEquals(perm1.getPermissionText(), result.getPermissionText());
		assertEquals(perm1.getApplication(), result.getApplication());
		assertEquals(pApp, result.getApplication());
		
		result = permissionRepository.findPermission(perm2.getPermissionText(), pApp.getApplicationName());
		assertNotNull(result);
		assertEquals(perm2.getPermissionText(), result.getPermissionText());
		assertEquals(perm2.getApplication(), result.getApplication());
		
		result = permissionRepository.findPermission(perm2.getPermissionText(), "someApplicationName");
		assertNull(result);
	}

	@After
	public void tearDown()
		throws Exception
	{
		permissionRepository.deleteAll();
		applicationRepository.deleteAll();
	}

	@Transactional(readOnly = false)
	private PPermission savePermission(PPermission permission)
	{
		return permissionRepository.saveAndFlush(permission);
	}
}
