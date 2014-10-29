package org.openur.module.service.userstructure.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.persistence.dao.IUserStructureDao;
import org.openur.module.service.userstructure.UserStructureTestSpringConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserStructureTestSpringConfig.class})
public class UserServicesTest
{	
	private final String PERSON_NO_1;	
	private final String PERSON_NO_2;
	private final String NUMBER_DIFFERENT_FROM_ALL_OTHERS;
	private final String UUID_1;
	private final String UUID_2;
	private final String OTHER_UUID;	
	
	private final IPerson PERSON_1;
	private final IPerson PERSON_2;
	private final ITechnicalUser TECH_USER_1;
	private final ITechnicalUser TECH_USER_2;
	
	@Inject
	private IUserStructureDao dao;
	
	@Inject
	private IUserServices userServices;	
	
	public UserServicesTest()
	{
		super();
		
		UUID uuidTmp1 = UUID.randomUUID();
		UUID uuidTmp2;
		
		do
		{
			uuidTmp2 = UUID.randomUUID();
		} while (uuidTmp2.equals(uuidTmp1));

		UUID otherUuidTmp;
		
		do
		{
			otherUuidTmp = UUID.randomUUID();
		} while (otherUuidTmp.equals(uuidTmp1) || otherUuidTmp.equals(uuidTmp2));

		UUID_1 = uuidTmp1.toString();
		UUID_2 = uuidTmp2.toString();
		OTHER_UUID = otherUuidTmp.toString();
		
		PERSON_NO_1 = "123";
		PERSON_NO_2 = "456";
		NUMBER_DIFFERENT_FROM_ALL_OTHERS = "numberDifferentFromAllOthers";
		
		PERSON_1 = new MyPerson(UUID_1, PERSON_NO_1);
		PERSON_2 = new MyPerson(UUID_2, PERSON_NO_2);
		
		TECH_USER_1 = new MyTechnicalUser(UUID_1, PERSON_NO_1);
		TECH_USER_2 = new MyTechnicalUser(UUID_2, PERSON_NO_2);
	}

	@Test
	public void testFindPersonById()
	{		
		Mockito.when(dao.findPersonById(UUID_1)).thenReturn(PERSON_1);
		
		IPerson p = userServices.findPersonById(UUID_1);		
		assertNotNull(p);
		assertEquals("identifier", p.getIdentifier(), UUID_1);
		assertEquals("personal number", PERSON_NO_1, p.getNumber());
		
		Mockito.when(dao.findPersonById(UUID_2)).thenReturn(PERSON_2);
		
		p = userServices.findPersonById(UUID_2);		
		assertNotNull(p);
		assertEquals("identifier", p.getIdentifier(), UUID_2);
		assertEquals("personal number", PERSON_NO_2, p.getNumber());
		
		p = userServices.findPersonById(OTHER_UUID);
		assertTrue(p == null || !p.getIdentifier().equals(UUID_1) || !p.getIdentifier().equals(UUID_2));
	}

	@Test
	public void testFindPersonByNumber()
	{
		Mockito.when(dao.findPersonByNumber(PERSON_NO_1)).thenReturn(PERSON_1);
		
		IPerson p = userServices.findPersonByNumber(PERSON_NO_1);		
		assertNotNull(p);
		assertEquals("personal number", PERSON_NO_1, p.getNumber());	
		assertEquals("identifier", p.getIdentifier(), UUID_1);

		Mockito.when(dao.findPersonByNumber(PERSON_NO_2)).thenReturn(PERSON_2);
		
		p = userServices.findPersonByNumber(PERSON_NO_2);		
		assertNotNull(p);
		assertEquals("personal number", PERSON_NO_2, p.getNumber());
		assertEquals("identifier", p.getIdentifier(), UUID_2);

		p = userServices.findPersonByNumber(NUMBER_DIFFERENT_FROM_ALL_OTHERS);
		assertTrue(p == null || !p.getNumber().equals(PERSON_NO_1) || !p.getNumber().equals(PERSON_NO_2));
	}

	@Test
	public void testObtainAllPersons()
	{
		Mockito.when(dao.obtainAllPersons()).thenReturn(Arrays.asList(PERSON_1, PERSON_2));
		
		Set<IPerson> personSet = userServices.obtainAllPersons();
		
		assertTrue(personSet != null);
		assertEquals(2, personSet.size());
		
		for (IPerson p : personSet)
		{
			assertTrue(UUID_1.equals(p.getIdentifier()) || UUID_2.equals(p.getIdentifier()));
			assertTrue(PERSON_NO_1.equals(p.getNumber()) || PERSON_NO_2.equals(p.getNumber()));
		}
		
		for (IPerson p : personSet)
		{
			assertFalse(OTHER_UUID.equals(p.getIdentifier()));
			assertFalse(NUMBER_DIFFERENT_FROM_ALL_OTHERS.equals(p.getNumber()));
		}
	}

	@Test
	public void testFindTechnicalUserById()
	{
		Mockito.when(dao.findTechnicalUserById(UUID_1)).thenReturn(TECH_USER_1);
		
		ITechnicalUser tu = userServices.findTechnicalUserById(UUID_1);		
		assertNotNull(tu);
		assertEquals(tu.getIdentifier(), TECH_USER_1.getIdentifier());
		assertEquals(tu.getNumber(), TECH_USER_1.getNumber());
		
		Mockito.when(dao.findTechnicalUserById(UUID_2)).thenReturn(TECH_USER_2);
		
		tu = userServices.findTechnicalUserById(UUID_2);		
		assertNotNull(tu);
		assertEquals(tu.getIdentifier(), TECH_USER_2.getIdentifier());
		assertEquals(tu.getNumber(), TECH_USER_2.getNumber());
		
		tu = userServices.findTechnicalUserById(OTHER_UUID);	
		assertTrue(tu == null || !tu.getIdentifier().equals(UUID_1) || !tu.getIdentifier().equals(UUID_2));
	}

	@Test
	public void testFindTechnicalUserByNumber()
	{
		Mockito.when(dao.findTechnicalUserByNumber(PERSON_NO_1)).thenReturn(TECH_USER_1);
		
		ITechnicalUser tu = userServices.findTechnicalUserByNumber(PERSON_NO_1);		
		assertNotNull(tu);
		assertEquals(tu.getNumber(), TECH_USER_1.getNumber());
		assertEquals(tu.getIdentifier(), TECH_USER_1.getIdentifier());
		
		Mockito.when(dao.findTechnicalUserByNumber(PERSON_NO_2)).thenReturn(TECH_USER_2);
		
		tu = userServices.findTechnicalUserByNumber(PERSON_NO_2);		
		assertNotNull(tu);
		assertEquals(tu.getNumber(), TECH_USER_2.getNumber());
		assertEquals(tu.getIdentifier(), TECH_USER_2.getIdentifier());
		
		tu = userServices.findTechnicalUserByNumber(NUMBER_DIFFERENT_FROM_ALL_OTHERS);	
		assertTrue(tu == null || !tu.getNumber().equals(PERSON_NO_1) || !tu.getNumber().equals(PERSON_NO_2));
	}

	@Test
	public void testObtainAllTechnicalUsers()
	{
		Mockito.when(dao.obtainAllTechnicalUsers()).thenReturn(Arrays.asList(TECH_USER_1, TECH_USER_2));
		
		Set<ITechnicalUser> techUsersSet = userServices.obtainAllTechnicalUsers();
		
		assertTrue(techUsersSet != null);
		assertEquals(2, techUsersSet.size());
		
		for (ITechnicalUser tu : techUsersSet)
		{
			assertTrue(UUID_1.equals(tu.getIdentifier()) || UUID_2.equals(tu.getIdentifier()));
			assertTrue(PERSON_NO_1.equals(tu.getNumber()) || PERSON_NO_2.equals(tu.getNumber()));
		}
		
		for (ITechnicalUser p : techUsersSet)
		{
			assertFalse(OTHER_UUID.equals(p.getIdentifier()));
			assertFalse(NUMBER_DIFFERENT_FROM_ALL_OTHERS.equals(p.getNumber()));
		}
	}
}
