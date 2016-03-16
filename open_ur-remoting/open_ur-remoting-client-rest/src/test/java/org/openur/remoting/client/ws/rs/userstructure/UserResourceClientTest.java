package org.openur.remoting.client.ws.rs.userstructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.core.Application;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.domain.utils.common.DomainObjectHelper;
import org.openur.module.domain.utils.compare.PersonComparer;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.remoting.resource.userstructure.MockUserServicesFactory;
import org.openur.remoting.resource.userstructure.UserResource;

public class UserResourceClientTest
	extends JerseyTest
{	
	private IUserServices userServices;
	
	@Override
	protected Application configure()
	{
		// Http-Testserver:
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bindFactory(MockUserServicesFactory.class).to(IUserServices.class);
			}
		};

		ResourceConfig config = new ResourceConfig(UserResource.class)
				.register(binder);
		
		// Client:
		userServices = new UserResourceClient("http://localhost:9998/");		
		
		for (Class<?> provider : ((UserResourceClient) userServices).getProviders())
		{
			config.register(provider);
		}

		return config;
	}

	@Test
	public void testFindPersonById()
	{
		IPerson p = userServices.findPersonById(TestObjectContainer.PERSON_UUID_1);

		assertNotNull(p);
		System.out.println("Result: " + p);
		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, (Person) p));
	}

	@Test
	public void testFindPersonByNumber()
	{
		IPerson p = userServices.findPersonByNumber(TestObjectContainer.PERSON_NUMBER_1);

		assertNotNull(p);
		System.out.println("Result: " + p);
		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, (Person) p));
	}

	@Test
	public void testObtainAllPersons()
	{
		Set<IPerson> resultSet = userServices.obtainAllPersons();

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
		ITechnicalUser tu = userServices.findTechnicalUserById(TestObjectContainer.TECH_USER_UUID_1);

		assertNotNull(tu);
		System.out.println("Result: " + tu);

		assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.TECH_USER_1, tu));
	}

	@Test
	public void testFindTechnicalUserByNumber()
	{
		ITechnicalUser tu = userServices.findTechnicalUserByNumber(TestObjectContainer.TECH_USER_NUMBER_1);

		assertNotNull(tu);
		System.out.println("Result: " + tu);

		assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.TECH_USER_1, tu));
	}

	@Test
	public void testObtainAllTechnicalUsers()
	{
		Set<ITechnicalUser> resultSet = userServices.obtainAllTechnicalUsers();

		assertFalse(resultSet.isEmpty());
		assertEquals(3, resultSet.size());
		System.out.println("Result: " + resultSet);
		
		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_1));
		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_2));
		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_3));
	}
}
