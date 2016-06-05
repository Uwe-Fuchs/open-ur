package org.openur.module.persistence.dao.rdbms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.persistence.dao.ITechnicalUserDao;
import org.openur.module.persistence.mapper.rdbms.IEntityDomainObjectMapper;
import org.openur.module.persistence.mapper.rdbms.TechnicalUserMapper;
import org.openur.module.persistence.rdbms.config.DaoSpringConfig;
import org.openur.module.persistence.rdbms.config.MapperSpringConfig;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.openur.module.persistence.rdbms.entity.PTechnicalUser;
import org.openur.module.persistence.rdbms.repository.ApplicationRepository;
import org.openur.module.persistence.rdbms.repository.PermissionRepository;
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
	private IEntityDomainObjectMapper<PPermission, OpenURPermission> permissionMapper;
	
	@Inject
	private IEntityDomainObjectMapper<PTechnicalUser, TechnicalUser> technicalUserMapper;
	
	@Inject
	private TechnicalUserRepository technicalUserRepository;
	
	@Inject
	private PermissionRepository permissionRepository;
	
	@Inject
	private ApplicationRepository applicationRepository;

	@Inject
	private ITechnicalUserDao technicalUserDao;

	@Test
	public void testFindTechnicalUserById()
	{
		PPermission perm1 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_A);	
		PApplication pApp = perm1.getApplication();
		PPermission perm2 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_2_A);	
		perm2.setApplication(pApp);
		PTechnicalUser persistable = technicalUserMapper.mapFromDomainObject(TestObjectContainer.TECH_USER_1);
		persistable.setPermissions(new HashSet<>(Arrays.asList(perm1, perm2)));
		persistable = saveTechnicalUser(persistable);

		ITechnicalUser tu = technicalUserDao.findTechnicalUserById(persistable.getIdentifier());

		assertNotNull(tu);
		assertTrue(TechnicalUserMapper.domainObjectEqualsToEntity((TechnicalUser) tu, persistable));
	}

	@Test
	public void testFindTechnicalUserByNumber()
	{
		PPermission perm1 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_B);	
		PApplication pApp = perm1.getApplication();
		PPermission perm2 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_2_B);	
		perm2.setApplication(pApp);
		PTechnicalUser persistable = technicalUserMapper.mapFromDomainObject(TestObjectContainer.TECH_USER_2);
		persistable.setPermissions(new HashSet<>(Arrays.asList(perm1, perm2)));
		persistable = saveTechnicalUser(persistable);

		ITechnicalUser tu = technicalUserDao.findTechnicalUserByNumber(TestObjectContainer.TECH_USER_NUMBER_2);

		assertNotNull(tu);
		assertTrue(TechnicalUserMapper.domainObjectEqualsToEntity((TechnicalUser) tu, persistable));
	}

	@Test
	public void testObtainAllTechnicalUsers()
	{
		List<ITechnicalUser> allTechUsers = technicalUserDao.obtainAllTechnicalUsers();
		assertNotNull(allTechUsers);
		assertEquals(allTechUsers.size(), 0);

		PPermission perm1 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_A);	
		PApplication pApp = perm1.getApplication();
		PPermission perm2 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_2_A);	
		perm2.setApplication(pApp);
		PTechnicalUser persistable1 = technicalUserMapper.mapFromDomainObject(TestObjectContainer.TECH_USER_1);
		persistable1.setPermissions(new HashSet<>(Arrays.asList(perm1, perm2)));
		persistable1 = saveTechnicalUser(persistable1);

		perm1 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_1_B);	
		pApp = perm1.getApplication();
		perm2 = permissionMapper.mapFromDomainObject(TestObjectContainer.PERMISSION_2_B);	
		perm2.setApplication(pApp);
		PTechnicalUser persistable2 = technicalUserMapper.mapFromDomainObject(TestObjectContainer.TECH_USER_2);
		persistable2.setPermissions(new HashSet<>(Arrays.asList(perm1, perm2)));
		persistable2 = saveTechnicalUser(persistable2);

		allTechUsers = technicalUserDao.obtainAllTechnicalUsers();
		assertNotNull(allTechUsers);
		assertEquals(allTechUsers.size(), 2);

		Iterator<ITechnicalUser> iter = allTechUsers.iterator();
		TechnicalUser _tu1 = (TechnicalUser) iter.next();
		TechnicalUser _tu2 = (TechnicalUser) iter.next();
		TechnicalUser tu1 = _tu1.getTechUserNumber().equals(persistable1.getTechUserNumber()) ? _tu1 : _tu2;
		TechnicalUser tu2 = _tu1.getTechUserNumber().equals(persistable1.getTechUserNumber()) ? _tu2 : _tu1;

		assertTrue(TechnicalUserMapper.domainObjectEqualsToEntity(tu1, persistable1));
		assertTrue(TechnicalUserMapper.domainObjectEqualsToEntity(tu2, persistable2));
	}

	@After
	@Transactional(readOnly=false)
	public void tearDown()
		throws Exception
	{
		technicalUserRepository.deleteAll();
		permissionRepository.deleteAll();
		applicationRepository.deleteAll();
	}

	@Transactional(readOnly = false)
	private PTechnicalUser saveTechnicalUser(PTechnicalUser persistable)
	{
		return technicalUserRepository.save(persistable);
	}
}