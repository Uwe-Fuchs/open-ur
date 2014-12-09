package org.openur.module.persistence.rdbms.repository.userstructure;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.persistence.rdbms.config.RepositoryConfig;
import org.openur.module.persistence.rdbms.entity.PAddress;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPerson;
import org.openur.module.persistence.rdbms.repository.PersonRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(classes = { RepositoryConfig.class })
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonRepositoryTest
{
	private final String EMPLOYEE_NUMBER = "123abc";
	
	@Inject
	private PersonRepository personRepository;

	@Test
	public void testFindPersonByNumber()
	{		
		PPerson persistable = new PPerson(EMPLOYEE_NUMBER, "Name of Employee");
		
		persistable = savePerson(persistable);
		
		PPerson p = findPersonByNumber(EMPLOYEE_NUMBER);
		
		assertNotNull(p);
		assertEquals(p, persistable);
	}
	
	@Test
	public void testCreateWithAddressAndApplication()
	{	
		PPerson persistable = new PPerson(EMPLOYEE_NUMBER, "Name of Employee");
		
		PAddress pAddress = new PAddress("11");		
		persistable.setHomeAddress(pAddress);
		
		PApplication pApp = new PApplication("applicationName");
		persistable.addApplication(pApp);
		
		persistable = savePerson(persistable);
		
		List<PPerson> allPersons = findAllPersons();
		
		assertNotNull(allPersons);
		assertEquals(allPersons.size(), 1);
		PPerson p = allPersons.get(0);
		assertEquals(p, persistable);
		
		assertNotNull(p.getApplications());
		assertTrue(CollectionUtils.isNotEmpty(p.getApplications()));
		PApplication pa = p.getApplications().iterator().next();
		assertEquals(pa, pApp);
		
		assertNotNull(p.getHomeAddress());
		PAddress pAdr = p.getHomeAddress();
		assertEquals(pAdr, pAddress);
	}
	
	@Test
	public void testAddApplicationsToPerson()
	{	
		PPerson persistable = new PPerson(EMPLOYEE_NUMBER, "Name of Employee");		
		persistable = savePerson(persistable);
		PPerson p = findPersonByNumber(EMPLOYEE_NUMBER);
		assertEquals(p.getApplications().size(), 0);
		
		PApplication pApp = new PApplication("applicationName");
		persistable.addApplication(pApp);		
		persistable = savePerson(persistable);
		p = findPersonByNumber(EMPLOYEE_NUMBER);
		assertEquals(p.getApplications().size(), 1);
		
		PApplication pApp2 = new PApplication("applicationName2");
		persistable.addApplication(pApp2);		
		persistable = savePerson(persistable);		
		p = findPersonByNumber(EMPLOYEE_NUMBER);
		assertEquals(p.getApplications().size(), 2);
	}
	
	@Test(expected = PersistenceException.class)
	public void testAddPersonWithSameEmployeeNumber()
	{	
		PPerson persistable = new PPerson(EMPLOYEE_NUMBER, "Name of Employee");		
		persistable = savePerson(persistable);
		
		persistable = new PPerson(EMPLOYEE_NUMBER, "Name of Employee with same number");		
		persistable = savePerson(persistable);
	}
	
	@Test
	public void testModify()
	{	
		PPerson persistable = new PPerson(EMPLOYEE_NUMBER, "Name of Employee");
		persistable = savePerson(persistable);
		assertNull(persistable.getLastModifiedDate());
		
		persistable.setStatus(Status.INACTIVE);		
		persistable = savePerson(persistable);		
		assertNotNull(persistable.getLastModifiedDate());
	}
	
	@After
	@Transactional(readOnly = false)
	public void deleteAllEntries()
	{
		personRepository.deleteAll();
	}
	
	@Transactional(readOnly = false)
	private PPerson savePerson(PPerson persistable)
	{
		return personRepository.save(persistable);
	}

	@Transactional(readOnly = true)
	private List<PPerson> findAllPersons()
	{
		return personRepository.findAll();
	}

	@Transactional(readOnly = true)
	private PPerson findPersonByNumber(String employeeNumber)
	{
		return personRepository.findPersonByNumber(employeeNumber);
	}
}
