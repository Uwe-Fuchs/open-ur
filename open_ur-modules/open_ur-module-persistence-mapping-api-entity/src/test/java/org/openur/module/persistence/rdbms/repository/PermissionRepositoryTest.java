package org.openur.module.persistence.rdbms.repository;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositorySpringConfig.class })
@ActiveProfiles(profiles = { "testRepository" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PermissionRepositoryTest
{
	@Inject
	private PermissionRepository permissionRepository;

	@Inject
	private ApplicationRepository applicationRepository;

	@Test
	public void testFindPermissionsByApplicationApplicationName()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testFindPermissionByPermissionText()
	{
		fail("Not yet implemented");
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
