package org.openur.remoting.resource.userstructure;

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
import org.junit.Assert;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.service.userstructure.IUserServices;

import com.google.gson.Gson;
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

		ResourceConfig config = new ResourceConfig(UserResource.class);
		config.register(binder);

		return config;
	}

	@Test
	public void testGetItResource()
	{
		Client client = ClientBuilder.newClient();
		Response response = client
				.target("http://localhost:9998/userstructure")
				.request(MediaType.TEXT_PLAIN)
				.get();
		
		Assert.assertEquals(200, response.getStatus());

		String msg = response.readEntity(String.class);
		Assert.assertEquals("Got it!", msg);
		System.out.println("Message: " + msg);

		response.close();
		client.close();
	}

	@Test
	public void testGetPersonByIdResource()
	{
		Client client = ClientBuilder.newClient();
		Response response = client
				.target("http://localhost:9998/userstructure/id/" + TestObjectContainer.PERSON_UUID_1)
				.request(MediaType.APPLICATION_JSON)
				.get();
		
		Assert.assertEquals(200, response.getStatus());
		String result = response.readEntity(String.class);
		System.out.println("Result: " + result);

		IPerson p = new Gson().fromJson(result, Person.class);
		Assert.assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.PERSON_1, p));

		response.close();
		client.close();
	}

	@Test
	public void testGetPersonByNumberResource()
	{
		Client client = ClientBuilder.newClient();
		Response response = client
				.target("http://localhost:9998/userstructure/number/" + TestObjectContainer.PERSON_NUMBER_1)
				.request(MediaType.APPLICATION_JSON)
				.get();
		
		Assert.assertEquals(200, response.getStatus());
		String result = response.readEntity(String.class);
		System.out.println("Result: " + result);

		IPerson p = new Gson().fromJson(result, Person.class);
		Assert.assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.PERSON_1, p));

		response.close();
		client.close();
	}

	@Test
	public void testObtainAllPersonsResource()
	{
		Client client = ClientBuilder.newClient();
		Response response = client
				.target("http://localhost:9998/userstructure/allPersons")
				.request(MediaType.APPLICATION_JSON)
				.get();
		
		Assert.assertEquals(200, response.getStatus());
		String result = response.readEntity(String.class);
		System.out.println("Result: " + result);

		Type resultType = new TypeToken<Set<Person>>()
		{
		}.getType();
		Set<? extends IPerson> resultSet = new Gson().fromJson(result, resultType);

		Assert.assertFalse(resultSet.isEmpty());
		Assert.assertEquals(3, resultSet.size());
		Assert.assertTrue(resultSet.contains(TestObjectContainer.PERSON_1));
		Assert.assertTrue(resultSet.contains(TestObjectContainer.PERSON_2));
		Assert.assertTrue(resultSet.contains(TestObjectContainer.PERSON_3));

		// IPerson p = resultSet.iterator().next();
		// Assert.assertTrue(EqualsBuilder.reflectionEquals(ResourceTestUtils.PERSON_1,
		// p));
	}
}
