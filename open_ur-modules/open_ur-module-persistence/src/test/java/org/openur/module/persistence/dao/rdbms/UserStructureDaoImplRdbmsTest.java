package org.openur.module.persistence.dao.rdbms;

import static org.junit.Assert.*;

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
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.persistence.dao.IUserStructureDao;
import org.openur.module.persistence.mapper.rdbms.PersonMapperTest;
import org.openur.module.persistence.mapper.rdbms.TechnicalUserMapperTest;
import org.openur.module.persistence.rdbms.config.RepositoryConfig;
import org.openur.module.persistence.rdbms.entity.PAddress;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPerson;
import org.openur.module.persistence.rdbms.entity.PTechnicalUser;
import org.openur.module.persistence.rdbms.repository.PersonRepository;
import org.openur.module.persistence.rdbms.repository.TechnicalUserRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositoryConfig.class })
@ActiveProfiles(profiles={"testRepository", "testUserStructureDao"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserStructureDaoImplRdbmsTest
{
	private final String EMPLOYEE_NUMBER = "123abc";
	
	@Inject
	private PersonRepository personRepository;
	
	@Inject
	private TechnicalUserRepository technicalUserRepository;
	
	@Inject
	private IUserStructureDao userStructureDao;

	@After
	public void tearDown()
		throws Exception
	{
		personRepository.deleteAll();
		technicalUserRepository.deleteAll();
	}

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
		
		PPerson persistable = new PPerson(EMPLOYEE_NUMBER, "Name of Employee");		

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
		
		IPerson p = userStructureDao.findPersonByNumber(EMPLOYEE_NUMBER);
		
		assertNotNull(p);
		assertTrue(PersonMapperTest.immutableEqualsToEntity((Person) p, persistable));
	}

	@Test
	public void testFindPersonById()
	{
		PPerson persistable = new PPerson(EMPLOYEE_NUMBER, "Name of Employee");		
		persistable = savePerson(persistable);
		
		IPerson p = userStructureDao.findPersonById(persistable.getIdentifier());
		
		assertNotNull(p);
		assertTrue(PersonMapperTest.immutableEqualsToEntity((Person) p, persistable));
	}

	@Test(expected=NumberFormatException.class)
	public void testFindPersonWithNonParseableId()
	{		
		userStructureDao.findPersonById("dasdasdasdasd");
	}

	@Test
	public void testObtainAllPersons()
	{
		PPerson persistable1 = new PPerson(EMPLOYEE_NUMBER, "Name of Employee");		
		persistable1 = savePerson(persistable1);
		
		PPerson persistable2 = new PPerson("456xyz", "employeeNo2");		
		persistable2 = savePerson(persistable2);
		
		List<IPerson> allPersons = userStructureDao.obtainAllPersons();
		assertNotNull(allPersons);
		assertEquals(allPersons.size(), 2);
		
		Iterator<IPerson> iter = allPersons.iterator();
		IPerson _p1 = iter.next();
		IPerson _p2 = iter.next();		
		Person p = (Person) (_p1.getIdentifier().equals(persistable1.getIdentifier()) ? _p1 : _p2);
		Person p2 = (Person) (_p1.getIdentifier().equals(persistable1.getIdentifier()) ? _p2 : _p1);
		
		assertTrue(PersonMapperTest.immutableEqualsToEntity(p, persistable1));
		assertTrue(PersonMapperTest.immutableEqualsToEntity(p2, persistable2));
	}

	@Test
	public void testFindTechnicalUserById()
	{
		PTechnicalUser persistable = new PTechnicalUser(EMPLOYEE_NUMBER);
		persistable = saveTechnicalUser(persistable);
		
		ITechnicalUser tu = userStructureDao.findTechnicalUserById(persistable.getIdentifier());
		
		assertNotNull(tu);
		assertTrue(TechnicalUserMapperTest.immutableEqualsToEntity((TechnicalUser) tu, persistable));		
	}
	
	@Test
	public void testFindTechnicalUserByNumber()
	{
		PTechnicalUser persistable = new PTechnicalUser(EMPLOYEE_NUMBER);
		persistable.setStatus(Status.INACTIVE);
		persistable = saveTechnicalUser(persistable);
		
		ITechnicalUser tu = userStructureDao.findTechnicalUserByNumber(EMPLOYEE_NUMBER);
		
		assertNotNull(tu);
		assertTrue(TechnicalUserMapperTest.immutableEqualsToEntity((TechnicalUser) tu, persistable));		
	}
	
	@Test
	public void testObtainAllTechnicalUsers()
	{
		fail("Not yet implemented");
	}

//	@Test
//	public void testFindOrgUnitById()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindOrgUnitByNumber()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testObtainAllOrgUnits()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testObtainSubOrgUnitsForOrgUnit()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testObtainRootOrgUnits()
//	{
//		fail("Not yet implemented");
//	}
	
	@Transactional(readOnly = false)
	private PPerson savePerson(PPerson persistable)
	{
		return personRepository.save(persistable);
	}
	
	@Transactional(readOnly = false)
	private PTechnicalUser saveTechnicalUser(PTechnicalUser persistable)
	{
		return technicalUserRepository.save(persistable);
	}
}
