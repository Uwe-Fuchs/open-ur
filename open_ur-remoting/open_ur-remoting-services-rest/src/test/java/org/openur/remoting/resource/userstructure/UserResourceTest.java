package org.openur.remoting.resource.userstructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.remoting.xchange.marshalling.json.PersonSerializer;
import org.openur.remoting.xchange.rest.providers.json.PersonProvider;
import org.openur.remoting.xchange.rest.providers.json.TechnicalUserProvider;
import org.openur.remoting.xchange.rest.providers.json.UserSetProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class UserResourceTest
	extends JerseyTest
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
		Client client = ClientBuilder.newClient();
		Response response = client
				.target("http://localhost:9998/userstructure/person/id/" + TestObjectContainer.PERSON_UUID_1)
				.request(MediaType.APPLICATION_JSON)
				.get();
		
		assertEquals(200, response.getStatus());
		String result = response.readEntity(String.class);
		System.out.println("Result: " + result);

    Gson gson = new GsonBuilder()
    		.registerTypeAdapter(Person.class, new PersonSerializer())
    		.create();
		Person p = gson.fromJson(result, Person.class);
		assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.PERSON_1, p));

		response.close();
		client.close();
	}

	@Test
	public void testGetPersonByNumberResource()
	{
		Client client = ClientBuilder.newClient();
		Response response = client
				.target("http://localhost:9998/userstructure/person/number/" + TestObjectContainer.PERSON_NUMBER_1)
				.request(MediaType.APPLICATION_JSON)
				.get();
		
		assertEquals(200, response.getStatus());
		String result = response.readEntity(String.class);
		System.out.println("Result: " + result);

    Gson gson = new GsonBuilder()
    		.registerTypeAdapter(Person.class, new PersonSerializer())
    		.create();
		Person p = gson.fromJson(result, Person.class);
		assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.PERSON_1, p));

		response.close();
		client.close();
	}

	@Test
	public void testObtainAllPersonsResource()
	{
		Client client = ClientBuilder.newClient();
		Response response = client
				.target("http://localhost:9998/userstructure/person/all")
				.request(MediaType.APPLICATION_JSON)
				.get();
		
		assertEquals(200, response.getStatus());
		String result = response.readEntity(String.class);
		System.out.println("Result: " + result);

		Type resultType = new TypeToken<Set<Person>>()
		{
		}.getType();

    Gson gson = new GsonBuilder()
    		.registerTypeAdapter(Person.class, new PersonSerializer())
    		.create();
		Set<Person> resultSet = gson.fromJson(result, resultType);

		assertFalse(resultSet.isEmpty());
		assertEquals(3, resultSet.size());
		assertTrue(resultSet.contains(TestObjectContainer.PERSON_1));
		assertTrue(resultSet.contains(TestObjectContainer.PERSON_2));
		assertTrue(resultSet.contains(TestObjectContainer.PERSON_3));
	}

	@Test
	public void testGetTechUserByIdResource()
	{
		Client client = ClientBuilder.newClient();
		Response response = client
				.target("http://localhost:9998/userstructure/techuser/id/" + TestObjectContainer.TECH_USER_UUID_1)
				.request(MediaType.APPLICATION_JSON)
				.get();
		
		assertEquals(200, response.getStatus());
		String result = response.readEntity(String.class);
		System.out.println("Result: " + result);

		TechnicalUser tu = new Gson().fromJson(result, TechnicalUser.class);
		assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.TECH_USER_1, tu));

		response.close();
		client.close();
	}

	@Test
	public void testGetTechUserByNumberResource()
	{
		Client client = ClientBuilder.newClient();
		Response response = client
				.target("http://localhost:9998/userstructure/techuser/number/" + TestObjectContainer.TECH_USER_NUMBER_1)
				.request(MediaType.APPLICATION_JSON)
				.get();
		
		assertEquals(200, response.getStatus());
		String result = response.readEntity(String.class);
		System.out.println("Result: " + result);

		TechnicalUser tu = new Gson().fromJson(result, TechnicalUser.class);
		assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.TECH_USER_1, tu));

		response.close();
		client.close();
	}

	@Test
	public void testObtainAllTechUsersResource()
	{
		Client client = ClientBuilder.newClient();
		Response response = client
				.target("http://localhost:9998/userstructure/techuser/all")
				.request(MediaType.APPLICATION_JSON)
				.get();
		
		assertEquals(200, response.getStatus());
		String result = response.readEntity(String.class);
		System.out.println("Result: " + result);

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
