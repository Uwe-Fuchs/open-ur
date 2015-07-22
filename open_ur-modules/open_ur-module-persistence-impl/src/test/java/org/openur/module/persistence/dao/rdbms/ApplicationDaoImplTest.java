package org.openur.module.persistence.dao.rdbms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.persistence.dao.IApplicationDao;
import org.openur.module.persistence.mapper.rdbms.ApplicationMapper;
import org.openur.module.persistence.mapper.rdbms.IApplicationMapper;
import org.openur.module.persistence.rdbms.config.DaoSpringConfig;
import org.openur.module.persistence.rdbms.config.MapperSpringConfig;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.repository.ApplicationRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositorySpringConfig.class, DaoSpringConfig.class, MapperSpringConfig.class })
@ActiveProfiles(profiles = { "testRepository", "testDao", "testMappers" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationDaoImplTest
{
	@Inject
	private IApplicationMapper<OpenURApplication> applicationMapper;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private IApplicationDao applicationDao;

	@Test
	public void testFindApplicationById()
	{
		PApplication persistable = applicationMapper.mapFromDomainObject(TestObjectContainer.APP_A);
		persistable = saveApplication(persistable);

		IApplication immutable = applicationDao.findApplicationById(persistable.getIdentifier());

		assertNotNull(immutable);
		assertTrue(ApplicationMapper.domainObjectEqualsToEntity((OpenURApplication) immutable, persistable));
	}

	@Test
	public void testFindApplicationByName()
	{
		PApplication persistable = applicationMapper.mapFromDomainObject(TestObjectContainer.APP_A);
		persistable = saveApplication(persistable);

		IApplication immutable = applicationDao.findApplicationByName(TestObjectContainer.APP_A.getApplicationName());

		assertNotNull(immutable);
		assertTrue(ApplicationMapper.domainObjectEqualsToEntity((OpenURApplication) immutable, persistable));
	}

	@Test
	public void testObtainAllApplications()
	{
		List<IApplication> allApps = applicationDao.obtainAllApplications();
		assertNotNull(allApps);
		assertEquals(allApps.size(), 0);
		
		PApplication persistable = applicationMapper.mapFromDomainObject(TestObjectContainer.APP_A);
		persistable = saveApplication(persistable);
		PApplication persistable2 = applicationMapper.mapFromDomainObject(TestObjectContainer.APP_B);
		persistable2 = saveApplication(persistable2);
		
		allApps = applicationDao.obtainAllApplications();
		assertNotNull(allApps);
		assertEquals(allApps.size(), 2);

		Iterator<IApplication> iter = allApps.iterator();
		OpenURApplication _app1 = (OpenURApplication) iter.next();
		OpenURApplication _app2 = (OpenURApplication) iter.next();
		OpenURApplication app1 = _app1.getApplicationName().equals(persistable.getApplicationName()) ? _app1 : _app2;
		OpenURApplication app2 = _app2.getApplicationName().equals(persistable2.getApplicationName()) ? _app2 : _app1;

		assertTrue(ApplicationMapper.domainObjectEqualsToEntity(app1, persistable));
		assertTrue(ApplicationMapper.domainObjectEqualsToEntity(app2, persistable2));
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
