package org.openur.module.persistence.rdbms.repository;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.persistence.mapper.rdbms.IPersonMapper;
import org.openur.module.persistence.rdbms.config.DaoSpringConfig;
import org.openur.module.persistence.rdbms.config.MapperSpringConfig;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
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
	@Inject
	private IPersonMapper<Person> personMapper;
	
	@Inject
	private UserAccountRepository userAccountRepository;
	
	@Inject
	private PersonRepository personRepository;
	
	@Inject
	private AddressRepository addressRepository;

	@Test
	@Transactional(readOnly=false)
	//@Rollback(value=false)
	public void testFindUserAccountByUserName()
	{
		PPerson pPerson = personMapper.mapFromDomainObject(TestObjectContainer.PERSON_1);
		
		PUserAccount pUserAccount = new PUserAccount(pPerson, TestObjectContainer.USER_NAME, TestObjectContainer.PASSWORD);
		pUserAccount = saveUserAccount(pUserAccount);
		
		pUserAccount = userAccountRepository.findUserAccountByUserName(TestObjectContainer.USER_NAME);
		
		assertNotNull(pUserAccount);
		assertEquals(TestObjectContainer.PASSWORD, pUserAccount.getPassWord());
		
		pUserAccount = userAccountRepository.findUserAccountByUserName("someUnknownUserName");
		assertNull(pUserAccount);
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
