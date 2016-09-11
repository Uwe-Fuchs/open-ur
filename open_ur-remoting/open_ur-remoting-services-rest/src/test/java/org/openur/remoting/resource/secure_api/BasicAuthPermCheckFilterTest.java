package org.openur.remoting.resource.secure_api;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.openur.module.domain.security.secure_api.PermissionConstraints.REMOTE_READ;

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
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;
import org.openur.module.util.exception.EntityNotFoundException;

public class BasicAuthPermCheckFilterTest
	extends AbstractSecurityFilterTest
{
	@Override
	protected Application configure()
	{
		ResourceConfig config = (ResourceConfig) super.configure();
		config.register(AuthenticationFilter_BasicAuth.class);
		config.register(AuthorizationFilter.class);
		
		return config;
	}
	
	@Test
	public void testFilter()
		throws UnsupportedEncodingException, EntityNotFoundException
	{
		Mockito.when(authorizationServicesMock.hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, REMOTE_READ, applicationName))
			.thenReturn(Boolean.TRUE);
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
		verify(authorizationServicesMock, times(1)).hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, REMOTE_READ, applicationName);
	}

	@Test
	public void testFilterWrongPassword()
		throws UnsupportedEncodingException, EntityNotFoundException
	{
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		// set invalid credentials:
		invocationBuilder.header(AbstractSecurityFilterBase.AUTHENTICATION_PROPERTY, buildHashedAuthString() + "appendSomeWrongPassword");
		Response response = invocationBuilder.get();
		assertEquals(401, response.getStatus());
		System.out.println(response.getStatus());

		// authentication is called, but authorization isn't (because of authentication-failure):
		assertEquals(1, realmMock.getAuthCounter());
		verify(authorizationServicesMock, times(0)).hasPermissionTechUser(anyString(), anyString(), anyString());
	}

	@Test
	public void testFilterEmptyCredentials()
		throws EntityNotFoundException
	{
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		// set empty credentials:
		invocationBuilder.header(AbstractSecurityFilterBase.AUTHENTICATION_PROPERTY, " ");
		
		Response response = invocationBuilder.get();
		assertEquals(401, response.getStatus());
		System.out.println(response.getStatus());
		String msg = response.readEntity(String.class);
		assertTrue(msg.contains(AbstractSecurityFilterBase.NO_CREDENTIALS_FOUND_MSG));

		// neither authentication nor authorization is called:
		assertEquals(0, realmMock.getAuthCounter());
		verify(authorizationServicesMock, times(0)).hasPermissionTechUser(anyString(), anyString(), anyString());
	}
}
