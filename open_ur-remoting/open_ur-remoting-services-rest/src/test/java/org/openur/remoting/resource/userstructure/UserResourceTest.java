package org.openur.remoting.resource.userstructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.openur.remoting.resource.userstructure.UserResource.ALL_PERSONS_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.UserResource.ALL_TECHUSERS_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.UserResource.PERSON_PER_ID_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.UserResource.PERSON_PER_NUMBER_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.UserResource.TECHUSER_PER_ID_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.UserResource.TECHUSER_PER_NUMBER_RESOURCE_PATH;
import static org.openur.remoting.resource.userstructure.UserResource.USER_RESOURCE_PATH;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.AuthorizableTechUser;
import org.openur.module.domain.userstructure.person.Person;
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
	private IUserServices userServicesMock;
	
	@Override
	protected Application configure()
	{
		userServicesMock = Mockito.mock(IUserServices.class);
		
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bind(userServicesMock).to(IUserServices.class);
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
		
		getResourceClient().addProvider(PersonProvider.class);
		getResourceClient().addProvider(TechnicalUserProvider.class);
		getResourceClient().addProvider(IdentifiableEntitySetProvider.class);
	}

	@Test
	public void testGetPersonByIdResource()
	{
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);
		
		Person p = getResourceClient().performRestCall_GET(
					PERSON_PER_ID_RESOURCE_PATH + TestObjectContainer.PERSON_UUID_1, MediaType.APPLICATION_JSON, Person.class);
		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, p));
	}

	@Test
	public void testGetPersonByNumberResource()
	{
		Mockito.when(userServicesMock.findPersonByNumber(TestObjectContainer.PERSON_NUMBER_1)).thenReturn(TestObjectContainer.PERSON_1);
		
		Person p = getResourceClient().performRestCall_GET(
					PERSON_PER_NUMBER_RESOURCE_PATH + TestObjectContainer.PERSON_NUMBER_1, MediaType.APPLICATION_JSON, Person.class);
		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, p));
	}

	@Test
	public void testObtainAllPersonsResource()
	{
		Mockito.when(userServicesMock.obtainAllPersons()).thenReturn(new HashSet<>(Arrays.asList(TestObjectContainer.PERSON_1, TestObjectContainer.PERSON_2, 
					TestObjectContainer.PERSON_3)));
		
		Set<Person> resultSet = getResourceClient().performRestCall_GET(ALL_PERSONS_RESOURCE_PATH, MediaType.APPLICATION_JSON, 
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
		Mockito.when(userServicesMock.findTechnicalUserById(TestObjectContainer.TECH_USER_UUID_1)).thenReturn(TestObjectContainer.TECH_USER_1);
		
		AuthorizableTechUser tu = getResourceClient().performRestCall_GET(
					TECHUSER_PER_ID_RESOURCE_PATH + TestObjectContainer.TECH_USER_UUID_1, MediaType.APPLICATION_JSON, AuthorizableTechUser.class);

		assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.TECH_USER_1, tu));
	}

	@Test
	public void testGetTechUserByNumberResource()
	{
		Mockito.when(userServicesMock.findTechnicalUserByNumber(TestObjectContainer.TECH_USER_NUMBER_1)).thenReturn(TestObjectContainer.TECH_USER_1);
		
		AuthorizableTechUser tu = getResourceClient().performRestCall_GET(
					TECHUSER_PER_NUMBER_RESOURCE_PATH + TestObjectContainer.TECH_USER_NUMBER_1, MediaType.APPLICATION_JSON, AuthorizableTechUser.class);

		assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.TECH_USER_1, tu));
	}

	@Test
	public void testObtainAllTechUsersResource()
	{
		Mockito.when(userServicesMock.obtainAllTechnicalUsers()).thenReturn(new HashSet<>(Arrays.asList(TestObjectContainer.TECH_USER_1, 
					TestObjectContainer.TECH_USER_2, TestObjectContainer.TECH_USER_3)));
		
		Set<AuthorizableTechUser> resultSet = getResourceClient().performRestCall_GET(ALL_TECHUSERS_RESOURCE_PATH, MediaType.APPLICATION_JSON, 
					new GenericType<Set<AuthorizableTechUser>>(new ParameterizedTypeImpl(Set.class, AuthorizableTechUser.class)));

		assertFalse(resultSet.isEmpty());
		assertEquals(3, resultSet.size());
		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_1));
		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_2));
		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_3));
	}

	@Override
	protected String getBaseURI()
	{
		return super.getBaseURI() + USER_RESOURCE_PATH;
	}
}
