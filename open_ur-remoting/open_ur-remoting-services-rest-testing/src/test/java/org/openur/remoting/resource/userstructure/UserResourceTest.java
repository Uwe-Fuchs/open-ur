package org.openur.remoting.resource.userstructure;

import static org.junit.Assert.*;

import static org.openur.remoting.resource.userstructure.UserResource.*;

import java.util.Set;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Before;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.domain.utils.common.DomainObjectHelper;
import org.openur.module.domain.utils.compare.PersonComparer;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.remoting.resource.AbstractResourceTest;
import org.openur.remoting.xchange.rest.providers.json.IdentifiableEntitySetProvider;
import org.openur.remoting.xchange.rest.providers.json.PersonProvider;
import org.openur.remoting.xchange.rest.providers.json.TechnicalUserProvider;

public class UserResourceTest
	extends AbstractResourceTest
{
	@Override
	protected Application configure()
	{
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bindFactory(MockUserServicesFactory.class).to(IUserServices.class);
			}
		};

		ResourceConfig config = new ResourceConfig(UserResource.class)
				.register(PersonProvider.class)
				.register(TechnicalUserProvider.class)
				.register(IdentifiableEntitySetProvider.class)
				.register(binder);

		return config;
	}
	
	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
		
		getMyClient().register(PersonProvider.class);
		getMyClient().register(TechnicalUserProvider.class);
		getMyClient().register(IdentifiableEntitySetProvider.class);
	}

	@Test
	public void testGetPersonByIdResource()
	{
		Person p = performRestCall(USER_RESOURCE_PATH + PERSON_PER_ID_RESOURCE_PATH + TestObjectContainer.PERSON_UUID_1, Person.class);
		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, p));
	}

	@Test
	public void testGetPersonByNumberResource()
	{
		Person p = performRestCall(USER_RESOURCE_PATH + PERSON_PER_NUMBER_RESOURCE_PATH + TestObjectContainer.PERSON_NUMBER_1, Person.class);
		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, p));
	}

	@Test
	public void testObtainAllPersonsResource()
	{
		Set<Person> resultSet = performRestCall(USER_RESOURCE_PATH + ALL_PERSONS_RESOURCE_PATH, 
				new GenericType<Set<Person>>(new ParameterizedTypeImpl(Set.class, Person.class)));

		assertFalse(resultSet.isEmpty());
		assertEquals(3, resultSet.size());
		
		Person p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.PERSON_UUID_1);
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.PERSON_UUID_2);
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_2, p));
		p = DomainObjectHelper.findIdentifiableEntityInCollection(resultSet, TestObjectContainer.PERSON_UUID_3);
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_3, p));
	}

	@Test
	public void testGetTechUserByIdResource()
	{
		TechnicalUser tu = performRestCall(
				USER_RESOURCE_PATH + TECHUSER_PER_ID_RESOURCE_PATH + TestObjectContainer.TECH_USER_UUID_1, TechnicalUser.class);

		assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.TECH_USER_1, tu));
	}

	@Test
	public void testGetTechUserByNumberResource()
	{
		TechnicalUser tu = performRestCall(
				USER_RESOURCE_PATH + TECHUSER_PER_NUMBER_RESOURCE_PATH + TestObjectContainer.TECH_USER_NUMBER_1, TechnicalUser.class);

		assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.TECH_USER_1, tu));
	}

	@Test
	public void testObtainAllTechUsersResource()
	{
		Set<TechnicalUser> resultSet = performRestCall(USER_RESOURCE_PATH + ALL_TECHUSERS_RESOURCE_PATH, 
				new GenericType<Set<TechnicalUser>>(new ParameterizedTypeImpl(Set.class, TechnicalUser.class)));

		assertFalse(resultSet.isEmpty());
		assertEquals(3, resultSet.size());
		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_1));
		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_2));
		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_3));
	}
}
