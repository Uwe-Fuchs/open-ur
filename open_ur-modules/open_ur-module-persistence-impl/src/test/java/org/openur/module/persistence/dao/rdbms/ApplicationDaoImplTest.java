package org.openur.module.persistence.dao.rdbms;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.module.domain.application.IApplication;
import org.openur.module.persistence.dao.IApplicationDao;
import org.openur.module.persistence.mapper.rdbms.ApplicationMapper;
import org.openur.module.persistence.rdbms.config.DaoSpringConfig;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.repository.ApplicationRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositorySpringConfig.class, DaoSpringConfig.class })
@ActiveProfiles(profiles = { "testRepository", "testDao" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationDaoImplTest
{
	private static final String APP_NAME = "nameOfApplication";
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private IApplicationDao applicationDao;

	@Test
	public void testFindApplicationById()
	{
		PApplication persistable = new PApplication(APP_NAME);
		persistable = saveApplication(persistable);

		IApplication tu = applicationDao.findApplicationById(persistable.getIdentifier());

		assertNotNull(tu);
		assertEquals(ApplicationMapper.mapFromEntity(persistable), tu);
	}

	@Test
	public void testFindApplicationByName()
	{
		PApplication persistable = new PApplication(APP_NAME);
		persistable = saveApplication(persistable);

		IApplication tu = applicationDao.findApplicationByName(APP_NAME);

		assertNotNull(tu);
		assertEquals(ApplicationMapper.mapFromEntity(persistable), tu);
	}

	@Test
	public void testObtainAllApplications()
	{
		List<IApplication> allApps = applicationDao.obtainAllApplications();
		assertNotNull(allApps);
		assertEquals(allApps.size(), 0);
		
		PApplication persistable = new PApplication(APP_NAME);
		persistable = saveApplication(persistable);
		PApplication persistable2 = new PApplication("appName2");
		persistable2 = saveApplication(persistable2);
		
		allApps = applicationDao.obtainAllApplications();
		assertNotNull(allApps);
		assertEquals(allApps.size(), 2);
		assertTrue(allApps.contains(ApplicationMapper.mapFromEntity(persistable)));
		assertTrue(allApps.contains(ApplicationMapper.mapFromEntity(persistable2)));
	}

	@After
	public void tearDown()
		throws Exception
	{
		applicationRepository.deleteAll();
	}

	@Transactional(readOnly = false)
	private PApplication saveApplication(PApplication persistable)
	{
		return applicationRepository.save(persistable);
	}
}
