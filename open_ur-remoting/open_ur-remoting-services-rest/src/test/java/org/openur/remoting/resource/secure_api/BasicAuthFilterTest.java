package org.openur.remoting.resource.secure_api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.utils.compare.PersonComparer;

public class BasicAuthFilterTest
	extends AbstractSecurityFilterTest
{
	@Override
	protected Application configure()
	{
		ResourceConfig config = (ResourceConfig) super.configure();
		config.register(AuthenticationFilter_BasicAuth.class);
		
		return config;
	}

	@Test
	public void testFilterValidCredentials()
		throws UnsupportedEncodingException
	{
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);

		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		
		// add hashed credential to request-headers:
		invocationBuilder.header(AbstractSecurityFilterBase.AUTHENTICATION_PROPERTY, buildHashedAuthString());
		
		Response response = invocationBuilder.get();
		assertEquals(200, response.getStatus());
		System.out.println(response.getStatus());

		Person p = response.readEntity(Person.class);
		assertNotNull(p);
		System.out.println(p);		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, p));

		assertEquals(1, realmMock.getAuthCounter());
	}

	@Test
	public void testFilterWrongPassword()
		throws UnsupportedEncodingException
	{
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		// set invalid credentials:
		invocationBuilder.header(AbstractSecurityFilterBase.AUTHENTICATION_PROPERTY, buildHashedAuthString() + "appendSomeWrongPassword");
		Response response = invocationBuilder.get();
		assertEquals(401, response.getStatus());
		System.out.println(response.getStatus());

		// authentication is called:
		assertEquals(1, realmMock.getAuthCounter());
	}

	@Test
	public void testFilterInvalidCredentials()
	{
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		
		// add e.g. UNHASHED credentials to request-headers:
		invocationBuilder.header(AbstractSecurityFilterBase.AUTHENTICATION_PROPERTY, buildAuthString());
		
		Response response = invocationBuilder.get();
		assertEquals(401, response.getStatus());
		System.out.println(response.getStatus());
		String msg = response.readEntity(String.class);
		assertTrue(msg.contains(AbstractSecurityFilterBase.NO_VALID_CREDENTIALS_FOUND_MSG));
	}

	@Test
	public void testFilterEmptyCredentials()
	{
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		// set empty credentials:
		invocationBuilder.header(AbstractSecurityFilterBase.AUTHENTICATION_PROPERTY, " ");
		
		Response response = invocationBuilder.get();
		assertEquals(401, response.getStatus());
		System.out.println(response.getStatus());
		String msg = response.readEntity(String.class);
		assertTrue(msg.contains(AbstractSecurityFilterBase.NO_CREDENTIALS_FOUND_MSG));
	}

	@Test
	public void testFilterNoCredentials()
	{
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		
		Response response = invocationBuilder.get();
		assertEquals(401, response.getStatus());
		System.out.println(response.getStatus());
		String msg = response.readEntity(String.class);
		assertTrue(msg.contains(AbstractSecurityFilterBase.NO_CREDENTIALS_FOUND_MSG));
	}
}
