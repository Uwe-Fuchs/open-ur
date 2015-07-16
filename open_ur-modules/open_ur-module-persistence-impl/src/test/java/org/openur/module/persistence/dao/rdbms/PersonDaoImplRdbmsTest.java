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
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.Title;
import org.openur.module.persistence.dao.IPersonDao;
import org.openur.module.persistence.mapper.rdbms.PersonMapper;
import org.openur.module.persistence.rdbms.config.DaoSpringConfig;
import org.openur.module.persistence.rdbms.config.RepositorySpringConfig;
import org.openur.module.persistence.rdbms.entity.PAddress;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPerson;
import org.openur.module.persistence.rdbms.repository.AddressRepository;
import org.openur.module.persistence.rdbms.repository.PersonRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositorySpringConfig.class, DaoSpringConfig.class })
@ActiveProfiles(profiles = { "testRepository", "testDao" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonDaoImplRdbmsTest
{
	private static final String USER_NUMBER = "123abc";

	@Inject
	private PersonRepository personRepository;

	@Inject
	private AddressRepository addressRepository;

	@Inject
	private IPersonDao personDao;

	@Test
	public void testFindPersonByNumber()
	{
		PAddress pAddress = new PAddress("11");
		pAddress.setCountryCode("DE");
		pAddress.setCity("city_1");
		pAddress.setStreet("street_1");
		pAddress.setStreetNo("11");
		pAddress.setPoBox("poBox_1");
		PApplication pApp = new PApplication("applicationName");

		PPerson persistable = new PPerson(USER_NUMBER, "Name of Employee");

		persistable.setGender(Gender.MALE);
		persistable.setTitle(Title.NONE);
		persistable.setFirstName("Uwe");
		persistable.setStatus(Status.ACTIVE);
		persistable.setEmailAdress("office@uwefuchs.com");
		persistable.setPhoneNumber("0049123456789");
		persistable.setFaxNumber("0049987654321");
		persistable.setMobileNumber("0049111222333");
		persistable.setHomePhoneNumber("0049444555666");
		persistable.setHomeEmailAdress("home@uwefuchs.com");
		persistable.setHomeAddress(pAddress);
		persistable.setApplications(new HashSet<PApplication>(Arrays.asList(pApp)));
		persistable = savePerson(persistable);

		IPerson p = personDao.findPersonByNumber(USER_NUMBER);

		assertNotNull(p);
		assertTrue(PersonMapper.immutableEqualsToEntity((Person) p, persistable));
	}

	@Test
	public void testFindPersonById()
	{
		PPerson persistable = new PPerson(USER_NUMBER, "Name of Employee");
		persistable = savePerson(persistable);

		IPerson p = personDao.findPersonById(persistable.getIdentifier());

		assertNotNull(p);
		assertTrue(PersonMapper.immutableEqualsToEntity((Person) p, persistable));
	}

	@Test(expected = NumberFormatException.class)
	public void testFindPersonWithNonParseableId()
	{
		personDao.findPersonById("idNonParseable");
	}

	@Test
	public void testObtainAllPersons()
	{
		List<IPerson> allPersons = personDao.obtainAllPersons();
		assertNotNull(allPersons);
		assertEquals(allPersons.size(), 0);

		PPerson persistable1 = new PPerson(USER_NUMBER, "Name of Employee");
		persistable1 = savePerson(persistable1);

		PPerson persistable2 = new PPerson("456xyz", "employeeNo2");
		persistable2 = savePerson(persistable2);

		allPersons = personDao.obtainAllPersons();
		assertNotNull(allPersons);
		assertEquals(allPersons.size(), 2);

		Iterator<IPerson> iter = allPersons.iterator();
		Person _p1 = (Person) iter.next();
		Person _p2 = (Person) iter.next();
		Person p = _p1.getPersonalNumber().equals(persistable1.getPersonalNumber()) ? _p1 : _p2;
		Person p2 = _p1.getPersonalNumber().equals(persistable1.getPersonalNumber()) ? _p2 : _p1;

		assertTrue(PersonMapper.immutableEqualsToEntity(p, persistable1));
		assertTrue(PersonMapper.immutableEqualsToEntity(p2, persistable2));
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
