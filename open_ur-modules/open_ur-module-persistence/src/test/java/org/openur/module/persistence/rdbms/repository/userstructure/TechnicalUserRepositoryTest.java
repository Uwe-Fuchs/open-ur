package org.openur.module.persistence.rdbms.repository.userstructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Title;
import org.openur.module.persistence.rdbms.config.RepositoryConfig;
import org.openur.module.persistence.rdbms.entity.PAddress;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPerson;
import org.openur.module.persistence.rdbms.repository.PersonRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { RepositoryConfig.class })
@Transactional
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class TechnicalUserRepositoryTest
{
	@Inject
	private PersonRepository personRepository;

	@Test
	public void testFindPersonByNumber()
	{
		final String EMPLOYEE_NUMBER = "123abc";
		
		PPerson persistable = new PPerson(EMPLOYEE_NUMBER, "Fuchs");
		
		personRepository.save(persistable);
		
		PPerson p = personRepository.findPersonByNumber(EMPLOYEE_NUMBER);
		
		assertNotNull(p);
		assertEquals(p, persistable);
	}
	
	@Test
	public void testCreate()
	{	
		PPerson persistable = new PPerson("123abc", "Fuchs");

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
		
		PAddress pAddress = new PAddress("11");		
		pAddress.setCareOf("Schmidt");
		pAddress.setCity("city_1");
		pAddress.setPoBox("poBox_1");
		pAddress.setStreet("street_1");
		pAddress.setStreetNo("11");		
		persistable.setHomeAddress(pAddress);
		
		PApplication pApp = new PApplication("applicationName");
		persistable.addApplication(pApp);
		
		personRepository.save(persistable);
		
		List<PPerson> allPersons = personRepository.findAll();
		
		assertNotNull(allPersons);
		assertEquals(allPersons.size(), 1);
		assertEquals(allPersons.get(0), persistable);
		
		assertNotNull(allPersons.get(0).getApplications());
		PApplication pa = allPersons.get(0).getApplications().iterator().next();
		assertEquals(pa, pApp);
		
		assertNotNull(allPersons.get(0).getHomeAddress());
		assertEquals(allPersons.get(0).getHomeAddress(), pAddress);
	}
}
