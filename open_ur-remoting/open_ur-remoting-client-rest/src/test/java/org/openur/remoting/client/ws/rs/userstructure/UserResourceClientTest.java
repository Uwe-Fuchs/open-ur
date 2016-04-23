package org.openur.remoting.client.ws.rs.userstructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.core.Application;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.domain.utils.common.DomainObjectHelper;
import org.openur.module.domain.utils.compare.PersonComparer;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.remoting.resource.userstructure.UserResource;

public class UserResourceClientTest
	extends JerseyTest
{		
	private UserResourceClient userResourceClient;
	private IUserServices userServicesMock;
	
	@Override
	protected Application configure()
	{
		// mocked service:
		userServicesMock = Mockito.mock(IUserServices.class);
		
		// Http-Testserver:
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bind(userServicesMock).to(IUserServices.class);
			}
		};

		ResourceConfig config = new ResourceConfig(UserResource.class)
				.register(binder);
		
		// Client:
		userResourceClient = new UserResourceClient("http://localhost:9998/");		
		
		for (Class<?> provider : ((UserResourceClient) userResourceClient).getProviders())
		{
			config.register(provider);
		}

		return config;
	}

	@Test
	public void testFindPersonById()
	{
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);
		
		IPerson p = userResourceClient.findPersonById(TestObjectContainer.PERSON_UUID_1);

		assertNotNull(p);
		System.out.println("Result: " + p);
		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, (Person) p));
	}

	@Test
	public void testFindPersonByNumber()
	{
		Mockito.when(userServicesMock.findPersonByNumber(TestObjectContainer.PERSON_NUMBER_1)).thenReturn(TestObjectContainer.PERSON_1);
		
		IPerson p = userResourceClient.findPersonByNumber(TestObjectContainer.PERSON_NUMBER_1);

		assertNotNull(p);
		System.out.println("Result: " + p);
		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, (Person) p));
	}

	@Test
	public void testObtainAllPersons()
	{
		Mockito.when(userServicesMock.obtainAllPersons()).thenReturn(new HashSet<>(Arrays.asList(TestObjectContainer.PERSON_1, TestObjectContainer.PERSON_2, 
					TestObjectContainer.PERSON_3)));
		
		Set<IPerson> resultSet = userResourceClient.obtainAllPersons();

		assertFalse(resultSet.isEmpty());
		assertEquals(3, resultSet.size());
		System.out.println("Result: " + resultSet);
		
		Set<Person> persons = new HashSet<Person>(resultSet.stream().map(p -> (Person) p).collect(Collectors.toSet()));
		
		Person p = DomainObjectHelper.findIdentifiableEntityInCollection(persons, TestObjectContainer.PERSON_UUID_1);
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(persons, TestObjectContainer.PERSON_UUID_2);
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_2, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(persons, TestObjectContainer.PERSON_UUID_3);
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_3, p));
	}

	@Test
	public void testFindTechnicalUserById()
	{
		Mockito.when(userServicesMock.findTechnicalUserById(TestObjectContainer.TECH_USER_UUID_1)).thenReturn(TestObjectContainer.TECH_USER_1);
		
		ITechnicalUser tu = userResourceClient.findTechnicalUserById(TestObjectContainer.TECH_USER_UUID_1);

		assertNotNull(tu);
		System.out.println("Result: " + tu);

		assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.TECH_USER_1, tu));
	}

	@Test
	public void testFindTechnicalUserByNumber()
	{
		Mockito.when(userServicesMock.findTechnicalUserByNumber(TestObjectContainer.TECH_USER_NUMBER_1)).thenReturn(TestObjectContainer.TECH_USER_1);
		
		ITechnicalUser tu = userResourceClient.findTechnicalUserByNumber(TestObjectContainer.TECH_USER_NUMBER_1);

		assertNotNull(tu);
		System.out.println("Result: " + tu);

		assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.TECH_USER_1, tu));
	}

	@Test
	public void testObtainAllTechnicalUsers()
	{
		Mockito.when(userServicesMock.obtainAllTechnicalUsers()).thenReturn(new HashSet<>(Arrays.asList(TestObjectContainer.TECH_USER_1, 
					TestObjectContainer.TECH_USER_2, TestObjectContainer.TECH_USER_3)));
		
		Set<ITechnicalUser> resultSet = userResourceClient.obtainAllTechnicalUsers();

		assertFalse(resultSet.isEmpty());
		assertEquals(3, resultSet.size());
		System.out.println("Result: " + resultSet);
		
		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_1));
		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_2));
		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_3));
	}
}
