package org.openur.remoting.resource.secure_api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.utils.compare.PersonComparer;

public class PreBasicAuthFilterTest
	extends AbstractSecurityFilterTest
{
	@Override
	protected Application configure()
	{
		ResourceConfig config = (ResourceConfig) super.configure();
		config.register(dummyPreparePreAuthFilter);
		config.register(AuthenticationFilter_J2eePreAuth.class);
		config.register(AuthenticationFilter_BasicAuth.class);
		config.register(AuthenticationResultCheckFilter.class);
		
		return config;
	}

	@Test
	public void testPreAuthenticated()
	{
		dummyPreparePreAuthFilter.setSecurityContextInRequestContext(true);
		Mockito.when(userServicesMock.findTechnicalUserById(TestObjectContainer.TECH_USER_UUID_2)).thenReturn(TestObjectContainer.TECH_USER_2);
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);
		
		Response response = buildInvocationTargetBuilder().get();
		
		assertEquals(200, response.getStatus());
		System.out.println(response.getStatus());
		verify(userServicesMock, times(1)).findTechnicalUserById(TestObjectContainer.TECH_USER_UUID_2);
		assertEquals(0, realmMock.getAuthCounter());

		IPerson p = response.readEntity(Person.class);
		assertNotNull(p);
		System.out.println("Result: " + p);		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, (Person) p));
	}

	@Test
	public void testNotPreButBasicAuthenticated()
		throws UnsupportedEncodingException
	{
		dummyPreparePreAuthFilter.setSecurityContextInRequestContext(false);
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);

		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		
		// add hashed credential to request-headers:
		invocationBuilder.header(AbstractSecurityFilterBase.AUTHENTICATION_PROPERTY, buildHashedAuthString());
		
		Response response = invocationBuilder.get();
		
		assertEquals(200, response.getStatus());
		System.out.println(response.getStatus());
		assertEquals(1, realmMock.getAuthCounter());

		IPerson p = response.readEntity(Person.class);
		assertNotNull(p);
		System.out.println("Result: " + p);		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, (Person) p));	
	}

	@Test
	public void testNotPreAuthenticatedInvalidBasicCredentials()
		throws UnsupportedEncodingException
	{
		dummyPreparePreAuthFilter.setSecurityContextInRequestContext(false);
		
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		
		// add hashed credential to request-headers:
		invocationBuilder.header(AbstractSecurityFilterBase.AUTHENTICATION_PROPERTY, buildHashedAuthString() + "appendSomeWrongPassword");
		
		Response response = invocationBuilder.get();
		
		assertEquals(401, response.getStatus());
		System.out.println(response.getStatus());
		assertEquals(1, realmMock.getAuthCounter());
		verify(userServicesMock, times(0)).findPersonById(TestObjectContainer.PERSON_UUID_1);		
	}
}
