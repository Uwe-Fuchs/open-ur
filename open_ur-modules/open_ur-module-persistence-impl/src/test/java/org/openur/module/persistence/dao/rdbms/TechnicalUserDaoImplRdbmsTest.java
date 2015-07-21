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
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.persistence.dao.ITechnicalUserDao;
import org.openur.module.persistence.mapper.rdbms.TechnicalUserMapper;
import org.openur.module.persistence.rdbms.config.DaoSpringConfig;
import org.openur.module.persistence.rdbms.config.MapperSpringConfig;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.openur.module.persistence.rdbms.entity.PTechnicalUser;
import org.openur.module.persistence.rdbms.repository.TechnicalUserRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositorySpringConfig.class, DaoSpringConfig.class, MapperSpringConfig.class })
@ActiveProfiles(profiles = { "testRepository", "testDao", "testMappers" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TechnicalUserDaoImplRdbmsTest
{
	@Inject
	private TechnicalUserMapper technicalUserMapper;
	
	@Inject
	private TechnicalUserRepository technicalUserRepository;

	@Inject
	private ITechnicalUserDao technicalUserDao;

	@Test
	public void testFindTechnicalUserById()
	{
		PTechnicalUser persistable = technicalUserMapper.mapFromImmutable(TestObjectContainer.TECH_USER_1);
		persistable = saveTechnicalUser(persistable);

		ITechnicalUser tu = technicalUserDao.findTechnicalUserById(persistable.getIdentifier());

		assertNotNull(tu);
		assertTrue(TechnicalUserMapper.immutableEqualsToEntity((TechnicalUser) tu, persistable));
	}

	@Test
	public void testFindTechnicalUserByNumber()
	{
		PTechnicalUser persistable = technicalUserMapper.mapFromImmutable(TestObjectContainer.TECH_USER_1);
		persistable = saveTechnicalUser(persistable);

		ITechnicalUser tu = technicalUserDao.findTechnicalUserByNumber(TestObjectContainer.TECH_USER_NUMBER_1);

		assertNotNull(tu);
		assertTrue(TechnicalUserMapper.immutableEqualsToEntity((TechnicalUser) tu, persistable));
	}

	@Test
	public void testObtainAllTechnicalUsers()
	{
		List<ITechnicalUser> allTechUsers = technicalUserDao.obtainAllTechnicalUsers();
		assertNotNull(allTechUsers);
		assertEquals(allTechUsers.size(), 0);

		PTechnicalUser persistable1 = technicalUserMapper.mapFromImmutable(TestObjectContainer.TECH_USER_1);
		persistable1 = saveTechnicalUser(persistable1);

		PTechnicalUser persistable2 = technicalUserMapper.mapFromImmutable(TestObjectContainer.TECH_USER_2);
		persistable2 = saveTechnicalUser(persistable2);

		allTechUsers = technicalUserDao.obtainAllTechnicalUsers();
		assertNotNull(allTechUsers);
		assertEquals(allTechUsers.size(), 2);

		Iterator<ITechnicalUser> iter = allTechUsers.iterator();
		TechnicalUser _tu1 = (TechnicalUser) iter.next();
		TechnicalUser _tu2 = (TechnicalUser) iter.next();
		TechnicalUser tu1 = _tu1.getTechUserNumber().equals(persistable1.getTechUserNumber()) ? _tu1 : _tu2;
		TechnicalUser tu2 = _tu1.getTechUserNumber().equals(persistable1.getTechUserNumber()) ? _tu2 : _tu1;

		assertTrue(TechnicalUserMapper.immutableEqualsToEntity(tu1, persistable1));
		assertTrue(TechnicalUserMapper.immutableEqualsToEntity(tu2, persistable2));
	}

	@After
	public void tearDown()
		throws Exception
	{
		technicalUserRepository.deleteAll();
	}

	@Transactional(readOnly = false)
	private PTechnicalUser saveTechnicalUser(PTechnicalUser persistable)
	{
		return technicalUserRepository.save(persistable);
	}
}
