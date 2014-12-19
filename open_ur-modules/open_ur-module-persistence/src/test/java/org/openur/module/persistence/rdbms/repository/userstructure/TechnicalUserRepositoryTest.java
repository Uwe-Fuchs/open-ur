package org.openur.module.persistence.rdbms.repository.userstructure;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.openur.module.persistence.rdbms.entity.PTechnicalUser;
import org.openur.module.persistence.rdbms.repository.TechnicalUserRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositorySpringConfig.class })
@ActiveProfiles("testRepository")
@RunWith(SpringJUnit4ClassRunner.class)
public class TechnicalUserRepositoryTest
{
	private final String TECH_USER_NUMBER = "123abc";
	
	@Inject
	private TechnicalUserRepository technicalUserRepository;
	
	@Test
	public void testCreate()
	{	
		PTechnicalUser persistable = new PTechnicalUser(TECH_USER_NUMBER);		
		persistable = saveTechnicalUser(persistable);
		
		List<PTechnicalUser> allTechUsers = findAllTechUsers();		
		assertNotNull(allTechUsers);
		assertEquals(allTechUsers.size(), 1);
		assertEquals(allTechUsers.get(0), persistable);
		
		PTechnicalUser tu = findTechnicalUserByNumber(TECH_USER_NUMBER);
		assertNotNull(tu);
		assertEquals(tu, persistable);
	}
	
	@After
	@Transactional(readOnly = false)
	public void cleanUpDatabase()
	{
		technicalUserRepository.deleteAll();
	}

	@Transactional(readOnly = true)
	private PTechnicalUser findTechnicalUserByNumber(String techUserNumber)
	{
		return technicalUserRepository.findTechnicalUserByNumber(techUserNumber);
	}

	@Transactional(readOnly = true)
	private List<PTechnicalUser> findAllTechUsers()
	{
		return technicalUserRepository.findAll();
	}
	
	@Transactional(readOnly = false)
	private PTechnicalUser saveTechnicalUser(PTechnicalUser persistable)
	{
		return technicalUserRepository.save(persistable);
	}
}
