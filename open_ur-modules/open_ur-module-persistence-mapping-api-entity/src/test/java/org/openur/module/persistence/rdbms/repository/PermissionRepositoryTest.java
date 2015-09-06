package org.openur.module.persistence.rdbms.repository;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { RepositorySpringConfig.class })
@ActiveProfiles(profiles = { "testRepository" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PermissionRepositoryTest
{
	

	@After
	public void tearDown()
		throws Exception
	{
	}

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

}
