package org.openur.module.persistence.rdbms.repository;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.module.persistence.rdbms.config.DaoSpringConfig;
import org.openur.module.persistence.rdbms.config.MapperSpringConfig;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.openur.module.persistence.rdbms.entity.PAddress;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPerson;
import org.openur.module.persistence.rdbms.entity.PUserAccount;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositorySpringConfig.class, DaoSpringConfig.class, MapperSpringConfig.class })
@ActiveProfiles(profiles = { "testRepository", "testDao", "testMappers" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserAccountRepositoryTest
{
	private static final String EMPLOYEE_NUMBER = "123abc";
	private static final String USER_NAME = "testUser";
	private static final String PASSWORD = "secret";
	
	@Inject
	private UserAccountRepository userAccountRepository;
	
	@Inject
	private PersonRepository personRepository;
	
	@Inject
	private AddressRepository addressRepository;

	@Test
	@Transactional(readOnly=false)
	public void testFindUserAccountByUserName()
	{
		PPerson pPerson = new PPerson(EMPLOYEE_NUMBER, "Name of Employee");
		
		PAddress pAddress = new PAddress("11");	
		pPerson.setHomeAddress(pAddress);
		
		PApplication pApp = new PApplication("applicationName");
		pPerson.addApplication(pApp);
		
		pPerson = savePerson(pPerson);
		
		PUserAccount pUserAccount = new PUserAccount(pPerson, USER_NAME, PASSWORD);
		pUserAccount = saveUserAccount(pUserAccount);
		
		pUserAccount = userAccountRepository.findUserAccountByUserName(USER_NAME);
		
		assertNotNull(pUserAccount);
		assertEquals(PASSWORD, pUserAccount.getPassWord());
	}
	
	@Transactional(readOnly = false)
	private PPerson savePerson(PPerson pPerson)
	{
		return personRepository.save(pPerson);
	}
	
	@Transactional(readOnly = false)
	private PUserAccount saveUserAccount(PUserAccount userAccount)
	{
		return userAccountRepository.save(userAccount);
	}
	
	@After
	@Transactional(readOnly = false)
	public void tearDown()
		throws Exception
	{
		userAccountRepository.deleteAll();
		personRepository.deleteAll();
		addressRepository.deleteAll();
	}
}
