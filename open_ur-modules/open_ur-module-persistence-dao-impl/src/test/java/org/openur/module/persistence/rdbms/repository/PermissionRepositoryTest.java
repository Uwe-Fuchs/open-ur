package org.openur.module.persistence.rdbms.repository;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.persistence.mapper.rdbms.IPermissionMapper;
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
	private IPermissionMapper<OpenURPermission> permissionMapper;
	
	@Inject
	private PermissionRepository permissionRepository;

	@Inject
	private ApplicationRepository applicationRepository;

	@Test
	@Transactional(readOnly=false)
	public void testFindPermissionsByApplicationApplicationName()
	{
		PPermission perm1 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_A);
		savePermission(perm1);		

		PApplication pApp = perm1.getApplication();
		PPermission perm2 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_2_A);
		perm2.setApplication(pApp);
		savePermission(perm2);
		
		List<PPermission> resultList = permissionRepository.findPermissionsByApplicationApplicationName(TestObjectContainer.APP_A.getApplicationName());
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
		PPermission perm1 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_A);
		savePermission(perm1);		

		PApplication pApp = perm1.getApplication();
		PPermission perm2 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_2_A);
		perm2.setApplication(pApp);
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
