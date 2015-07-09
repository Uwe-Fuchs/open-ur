package org.openur.remoting.resource.userstructure;

import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openur.domain.testfixture.dummyimpl.MyPerson;
import org.openur.domain.testfixture.dummyimpl.MyTechnicalUser;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.service.userstructure.IUserServices;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UserResourceTest_ArbObj
	extends JerseyTest
{
	static String UUID_1;
	static String UUID_2;
	static String OTHER_UUID;	
	static String NO_123;	
	static String NO_456;
	static String NUMBER_DIFFERENT_FROM_ALL_OTHERS;
	
	static IPerson PERSON_1;
	static IPerson PERSON_2;
	static ITechnicalUser TECH_USER_1;
	static ITechnicalUser TECH_USER_2;
	
	@BeforeClass
	public static void init()
	{
		UUID_1 = UUID.randomUUID().toString();
		UUID_2 = UUID.randomUUID().toString();
		OTHER_UUID = UUID.randomUUID().toString();
		
		NO_123 = "123";
		NO_456 = "456";
		NUMBER_DIFFERENT_FROM_ALL_OTHERS = "numberDifferentFromAllOthers";
		
		PERSON_1 = new MyPerson(UUID_1, NO_123);
		PERSON_2 = new MyPerson(UUID_2, NO_456);
		
		TECH_USER_1 = new MyTechnicalUser(UUID_1, NO_123);
		TECH_USER_2 = new MyTechnicalUser(UUID_2, NO_456);
	}
	
	@Override
	protected Application configure()
	{
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bindFactory(MockUserServicesFactory_ArbObj.class).to(IUserServices.class);
			}
		};

		ResourceConfig config = new ResourceConfig(UserResource.class);
		config.register(binder);

		return config;
	}

	@Test
	public void testGetPersonByIdResource()
	{
		Client client = ClientBuilder.newClient();
		Response response = client
				.target("http://localhost:9998/userstructure/person/id/" + UUID_1)
				.request(MediaType.APPLICATION_JSON)
				.get();
		
		assertEquals(200, response.getStatus());
		String result = response.readEntity(String.class);
		System.out.println("Result: " + result);

		IPerson p = new Gson().fromJson(result, MyPerson.class);
		assertTrue(EqualsBuilder.reflectionEquals(PERSON_1, p));

		response.close();
		client.close();
	}

	@Test
	public void testGetPersonByNumberResource()
	{
		Client client = ClientBuilder.newClient();
		Response response = client
				.target("http://localhost:9998/userstructure/person/number/" + NO_123)
				.request(MediaType.APPLICATION_JSON)
				.get();
		
		assertEquals(200, response.getStatus());
		String result = response.readEntity(String.class);
		System.out.println("Result: " + result);

		IPerson p = new Gson().fromJson(result, MyPerson.class);
		assertTrue(EqualsBuilder.reflectionEquals(PERSON_1, p));

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
		Set<? extends IPerson> resultSet = new Gson().fromJson(result, resultType);

		assertFalse(resultSet.isEmpty());
		assertEquals(2, resultSet.size());

		
	}

//	@Test
//	public void testGetTechUserByIdResource()
//	{
//		Client client = ClientBuilder.newClient();
//		Response response = client
//				.target("http://localhost:9998/userstructure/techuser/id/" + TestObjectContainer.TECH_USER_UUID_1)
//				.request(MediaType.APPLICATION_JSON)
//				.get();
//		
//		assertEquals(200, response.getStatus());
//		String result = response.readEntity(String.class);
//		System.out.println("Result: " + result);
//
//		ITechnicalUser tu = new Gson().fromJson(result, TechnicalUser.class);
//		assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.TECH_USER_1, tu));
//
//		response.close();
//		client.close();
//	}
//
//	@Test
//	public void testGetTechUserByNumberResource()
//	{
//		Client client = ClientBuilder.newClient();
//		Response response = client
//				.target("http://localhost:9998/userstructure/techuser/number/" + TestObjectContainer.TECH_USER_NUMBER_1)
//				.request(MediaType.APPLICATION_JSON)
//				.get();
//		
//		assertEquals(200, response.getStatus());
//		String result = response.readEntity(String.class);
//		System.out.println("Result: " + result);
//
//		ITechnicalUser tu = new Gson().fromJson(result, TechnicalUser.class);
//		assertTrue(EqualsBuilder.reflectionEquals(TestObjectContainer.TECH_USER_1, tu));
//
//		response.close();
//		client.close();
//	}
//
//	@Test
//	public void testObtainAllTechUsersResource()
//	{
//		Client client = ClientBuilder.newClient();
//		Response response = client
//				.target("http://localhost:9998/userstructure/techuser/all")
//				.request(MediaType.APPLICATION_JSON)
//				.get();
//		
//		assertEquals(200, response.getStatus());
//		String result = response.readEntity(String.class);
//		System.out.println("Result: " + result);
//
//		Type resultType = new TypeToken<Set<TechnicalUser>>()
//		{
//		}.getType();
//		Set<? extends ITechnicalUser> resultSet = new Gson().fromJson(result, resultType);
//
//		assertFalse(resultSet.isEmpty());
//		assertEquals(3, resultSet.size());
//		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_1));
//		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_2));
//		assertTrue(resultSet.contains(TestObjectContainer.TECH_USER_3));
//	}
}
