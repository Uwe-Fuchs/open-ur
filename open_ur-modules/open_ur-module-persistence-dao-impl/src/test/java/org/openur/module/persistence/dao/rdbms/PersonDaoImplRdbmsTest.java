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
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.persistence.dao.IPersonDao;
import org.openur.module.persistence.mapper.rdbms.IEntityDomainObjectMapper;
import org.openur.module.persistence.mapper.rdbms.PersonMapper;
import org.openur.module.persistence.rdbms.config.DaoSpringConfig;
import org.openur.module.persistence.rdbms.config.MapperSpringConfig;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPerson;
import org.openur.module.persistence.rdbms.repository.AddressRepository;
import org.openur.module.persistence.rdbms.repository.PersonRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositorySpringConfig.class, DaoSpringConfig.class, MapperSpringConfig.class })
@ActiveProfiles(profiles = { "testRepository", "testDao", "testMappers" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonDaoImplRdbmsTest
{
	@Inject
	private IEntityDomainObjectMapper<PApplication, OpenURApplication> applicationMapper;
	
	@Inject
	private IEntityDomainObjectMapper<PPerson, Person> personMapper;
	
	@Inject
	private PersonRepository personRepository;

	@Inject
	private AddressRepository addressRepository;

	@Inject
	private IPersonDao personDao;

	@Test
	public void testFindPersonByNumber()
	{
		PPerson persistable = personMapper.mapFromDomainObject(TestObjectContainer.PERSON_1);
		persistable = savePerson(persistable);

		IPerson p = personDao.findPersonByNumber(TestObjectContainer.PERSON_NUMBER_1);

		assertNotNull(p);
		assertTrue(PersonMapper.domainObjectEqualsToEntity((Person) p, persistable));
	}

	@Test
	public void testFindPersonById()
	{
		PPerson persistable = personMapper.mapFromDomainObject(TestObjectContainer.PERSON_1);
		persistable = savePerson(persistable);

		IPerson p = personDao.findPersonById(persistable.getIdentifier());

		assertNotNull(p);
		assertTrue(PersonMapper.domainObjectEqualsToEntity((Person) p, persistable));
	}

	@Test(expected = NumberFormatException.class)
	public void testFindPersonWithNonParseableId()
	{
		personDao.findPersonById("idNonParseable");
	}

	@Test
	@Transactional(readOnly=false)
	public void testObtainAllPersons()
	{
		List<IPerson> allPersons = personDao.obtainAllPersons();
		assertNotNull(allPersons);
		assertEquals(allPersons.size(), 0);
		
		PApplication pApp_A = applicationMapper.mapFromDomainObject(TestObjectContainer.APP_A);
		PApplication pApp_B = applicationMapper.mapFromDomainObject(TestObjectContainer.APP_B);
		PApplication pApp_C = applicationMapper.mapFromDomainObject(TestObjectContainer.APP_C);

		PPerson person_1 = personMapper.mapFromDomainObject(TestObjectContainer.PERSON_1);
		person_1.setApplications(new HashSet<>(Arrays.asList(pApp_A, pApp_B)));
		person_1 = savePerson(person_1);

		PPerson person_2 = personMapper.mapFromDomainObject(TestObjectContainer.PERSON_2);
		person_2.setApplications(new HashSet<>(Arrays.asList(pApp_B, pApp_C)));
		person_2 = savePerson(person_2);

		allPersons = personDao.obtainAllPersons();
		assertNotNull(allPersons);
		assertEquals(allPersons.size(), 2);

		Iterator<IPerson> iter = allPersons.iterator();
		Person _p1 = (Person) iter.next();
		Person _p2 = (Person) iter.next();
		Person p = _p1.getPersonalNumber().equals(person_1.getPersonalNumber()) ? _p1 : _p2;
		Person p2 = _p1.getPersonalNumber().equals(person_1.getPersonalNumber()) ? _p2 : _p1;

		assertTrue(PersonMapper.domainObjectEqualsToEntity(p, person_1));
		assertTrue(PersonMapper.domainObjectEqualsToEntity(p2, person_2));
	}

	@After
	public void tearDown()
		throws Exception
	{
		personRepository.deleteAll();
		addressRepository.deleteAll();
	}

	@Transactional(readOnly = false)
	private PPerson savePerson(PPerson persistable)
	{
		return personRepository.save(persistable);
	}
}
