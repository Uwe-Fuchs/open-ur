package org.openur.module.service.userstructure.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.module.domain.userstructure.user.person.IPerson;
import org.openur.module.domain.userstructure.user.person.Person;
import org.openur.module.domain.userstructure.user.person.PersonBuilder;
import org.openur.module.domain.userstructure.user.technicaluser.ITechnicalUser;
import org.openur.module.domain.userstructure.user.technicaluser.TechnicalUserBuilder;
import org.openur.module.persistence.userstructure.IUserStructureDao;
import org.openur.module.service.userstructure.UserStructureTestSpringConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserStructureTestSpringConfig.class})
public class UserServicesTest
{
	@Inject
	private IUserStructureDao dao;
	
	@Inject
	private IUserServices userServices;

	@Test
	public void testFindPersonById()
	{		
		String uuid = UUID.randomUUID().toString();
		String uuid2 = UUID.randomUUID().toString();
		
		IPerson person = new PersonBuilder(uuid, "name", "pw").build();
		IPerson person2 = new PersonBuilder(uuid2, "name2", "pw2").build();
		
		Mockito.when(dao.findPersonById(uuid)).thenReturn(person);
		Mockito.when(dao.findPersonById(uuid2)).thenReturn(person2);	
		
		IPerson p = userServices.findPersonById(uuid);		
		assertNotNull(p);
		assertEquals("username", "name", ((Person) p).getUsername());	
		
		IPerson p2 = userServices.findPersonById(uuid2);		
		assertNotNull(p2);
		assertEquals("username", "name2", ((Person) p2).getUsername());
	}

	@Test
	public void testFindPersonByNumber()
	{
		String personalNo1 = "no123";
		String personalNo2 = "no456";
		
		IPerson person = new PersonBuilder("name", "pw").number(personalNo1).build();
		IPerson person2 = new PersonBuilder("name2", "pw2").number(personalNo2).build();
		
		Mockito.when(dao.findPersonByNumber(personalNo1)).thenReturn(person);
		Mockito.when(dao.findPersonByNumber(personalNo2)).thenReturn(person2);
		
		IPerson p = userServices.findPersonByNumber(personalNo1);		
		assertNotNull(p);
		assertEquals("username", "name", ((Person) p).getUsername());	
		
		IPerson p2 = userServices.findPersonByNumber(personalNo2);		
		assertNotNull(p2);
		assertEquals("username", "name2", ((Person) p2).getUsername());
	}

	@Test
	public void testObtainAllPersons()
	{
		String personalNo1 = "no123";
		String personalNo2 = "no456";
		
		IPerson person = new PersonBuilder("name", "pw").number(personalNo1).build();
		IPerson person2 = new PersonBuilder("name2", "pw2").number(personalNo2).build();
		
		Mockito.when(dao.obtainAllPersons()).thenReturn(Arrays.asList(person, person2));
		
		Set<IPerson> personSet = userServices.obtainAllPersons();
		
		assertTrue(CollectionUtils.isNotEmpty(personSet));
		assertEquals(2, personSet.size());
		assertTrue(personSet.contains(person));
		assertTrue(personSet.contains(person2));
		
		Mockito.when(dao.obtainAllPersons()).thenReturn(new ArrayList<IPerson>(0));
		personSet = userServices.obtainAllPersons();
		assertNotNull(personSet);
		assertTrue(personSet.isEmpty());
	}

	@Test
	public void testFindTechnicalUserById()
	{
		String uuid = UUID.randomUUID().toString();
		String uuid2 = UUID.randomUUID().toString();
		
		ITechnicalUser techUser = new TechnicalUserBuilder(uuid, "name", "pw").build();
		ITechnicalUser techUser2 = new TechnicalUserBuilder(uuid2, "name2", "pw2").build();
		
		Mockito.when(dao.findTechnicalUserById(uuid)).thenReturn(techUser);
		Mockito.when(dao.findTechnicalUserById(uuid2)).thenReturn(techUser2);	
		
		ITechnicalUser tu = userServices.findTechnicalUserById(uuid);		
		assertNotNull(tu);
		assertEquals(tu, techUser);	
		
		ITechnicalUser tu2 = userServices.findTechnicalUserById(uuid2);		
		assertNotNull(tu2);
		assertEquals(tu2, techUser2);
	}

	@Test
	public void testFindTechnicalUserByNumber()
	{
		String personalNo1 = "no123";
		String personalNo2 = "no456";
		
		ITechnicalUser techUser = new TechnicalUserBuilder("name", "pw").number(personalNo1).build();
		ITechnicalUser techUser2 = new TechnicalUserBuilder("name2", "pw2").number(personalNo2).build();
		
		Mockito.when(dao.findTechnicalUserByNumber(personalNo1)).thenReturn(techUser);
		Mockito.when(dao.findTechnicalUserByNumber(personalNo2)).thenReturn(techUser2);	
		
		ITechnicalUser tu = userServices.findTechnicalUserByNumber(personalNo1);		
		assertNotNull(tu);
		assertEquals(tu, techUser);	
		
		ITechnicalUser tu2 = userServices.findTechnicalUserByNumber(personalNo2);		
		assertNotNull(tu2);
		assertEquals(tu2, techUser2);
	}

	@Test
	public void testObtainAllTechnicalUsers()
	{
		String personalNo1 = "no123";
		String personalNo2 = "no456";
		
		ITechnicalUser techUser = new TechnicalUserBuilder("name", "pw").number(personalNo1).build();
		ITechnicalUser techUser2 = new TechnicalUserBuilder("name2", "pw2").number(personalNo2).build();
		
		Mockito.when(dao.obtainAllTechnicalUsers()).thenReturn(Arrays.asList(techUser, techUser2));
		
		Set<ITechnicalUser> techUsersSet = userServices.obtainAllTechnicalUsers();
		
		assertTrue(CollectionUtils.isNotEmpty(techUsersSet));
		assertEquals(2, techUsersSet.size());
		assertTrue(techUsersSet.contains(techUser));
		assertTrue(techUsersSet.contains(techUser2));
		
		Mockito.when(dao.obtainAllTechnicalUsers()).thenReturn(new ArrayList<ITechnicalUser>(0));
		techUsersSet = userServices.obtainAllTechnicalUsers();
		assertNotNull(techUsersSet);
		assertTrue(techUsersSet.isEmpty());
	}
}
