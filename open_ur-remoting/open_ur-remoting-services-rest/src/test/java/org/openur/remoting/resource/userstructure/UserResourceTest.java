package org.openur.remoting.resource.userstructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.domain.utils.common.DomainObjectHelper;
import org.openur.module.domain.utils.compare.PersonComparer;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.remoting.xchange.marshalling.json.PersonSerializer;
import org.openur.remoting.xchange.rest.providers.json.PersonProvider;
import org.openur.remoting.xchange.rest.providers.json.TechnicalUserProvider;
import org.openur.remoting.xchange.rest.providers.json.UserSetProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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
				.register(UserSetProvider.class)
				.register(binder);

		return config;
	}

	@Test
	public void testGetPersonByIdResource()
	{
		String result = performRestCall("userstructure/person/id/" + TestObjectContainer.PERSON_UUID_1);

    Gson gson = new GsonBuilder()
    		.registerTypeAdapter(Person.class, new PersonSerializer())
    		.create();
		Person p = gson.fromJson(result, Person.class);
		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, p));
	}

	@Test
	public void testGetPersonByNumberResource()
	{
		String result = performRestCall("userstructure/person/number/" + TestObjectContainer.PERSON_NUMBER_1);

    Gson gson = new GsonBuilder()
    		.registerTypeAdapter(Person.class, new PersonSerializer())
    		.create();
		Person p = gson.fromJson(result, Person.class);
		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, p));
	}

	@Test
	public void testObtainAllPersonsResource()
	{
		String result = performRestCall("userstructure/person/all");

		Type resultType = new TypeToken<Set<Person>>()
		{
		}.getType();

    Gson gson = new GsonBuilder()
    		.registerTypeAdapter(Person.class, new PersonSerializer())
    		.create();
		Set<Person> resultSet = gson.fromJson(result, resultType);

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
		String result = performRestCall("userstructure/techuser/id/" + TestObjectContainer.TECH_USER_UUID_1);

		TechnicalUser tu = new Gson().fromJson(result, TechnicalUser.class);
		assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.TECH_USER_1, tu));
	}

	@Test
	public void testGetTechUserByNumberResource()
	{
		String result = performRestCall("userstructure/techuser/number/" + TestObjectContainer.TECH_USER_NUMBER_1);

		TechnicalUser tu = new Gson().fromJson(result, TechnicalUser.class);
		assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.TECH_USER_1, tu));
	}

	@Test
	public void testObtainAllTechUsersResource()
	{
		String result = performRestCall("userstructure/techuser/all");

		Type resultType = new TypeToken<Set<TechnicalUser>>()
		{
		}.getType();
		Set<TechnicalUser> resultSet = new Gson().fromJson(result, resultType);

		assertFalse(resultSet.isEmpty());
		assertEquals(3, resultSet.size());
		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_1));
		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_2));
		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_3));
	}
}
