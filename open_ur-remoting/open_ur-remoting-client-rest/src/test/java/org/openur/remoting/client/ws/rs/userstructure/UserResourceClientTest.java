package org.openur.remoting.client.ws.rs.userstructure;

import static org.junit.Assert.*;

import javax.ws.rs.core.Application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.utils.compare.PersonComparer;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.remoting.resource.userstructure.UserResource;

public class UserResourceClientTest
	extends JerseyTest
{	
	private IUserServices userServices;
	
	@Override
	protected Application configure()
	{
		userServices = new UserResourceClient("http://localhost:9998/");	
		
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

//	@Test
//	public void testFindPersonByNumber()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testObtainAllPersons()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindTechnicalUserById()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindTechnicalUserByNumber()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testObtainAllTechnicalUsers()
//	{
//		fail("Not yet implemented");
//	}
}
