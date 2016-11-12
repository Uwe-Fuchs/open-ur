package org.openur.remoting.resource.secure_api;


import static org.openur.module.domain.security.secure_api.PermissionConstraints.REMOTE_READ;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.utils.compare.PersonComparer;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;
import org.openur.module.util.exception.EntityNotFoundException;
import org.openur.remoting.resource.secure_api.testing.DummyAuthenticationFilter;

public class AuthorizationFilterTest
	extends AbstractSecurityFilterTest
{
	private DummyAuthenticationFilter dummyAuthenticationFilter;
	
	@Override
	protected Application configure()
	{
		ResourceConfig config = (ResourceConfig) super.configure();
		dummyAuthenticationFilter = new DummyAuthenticationFilter();
		config.register(dummyAuthenticationFilter);
		config.register(AuthorizationFilter.class);
		
		return config;
	}

	@Test
	public void testFilter()
		throws EntityNotFoundException
	{
		// set user-id in context, i.e. user is authenticated:
		dummyAuthenticationFilter.setUserIdInContext(true);
		
		Mockito.when(authorizationServicesMock.hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, REMOTE_READ, applicationName))
				.thenReturn(Boolean.TRUE);
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);

		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		Response response = invocationBuilder.get();
		assertEquals(200, response.getStatus());
		System.out.println(response.getStatus());

		// authorization is called:
		verify(authorizationServicesMock, times(1)).hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, REMOTE_READ, applicationName);

		Person p = response.readEntity(Person.class);
		assertNotNull(p);
		System.out.println(p);		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, p));
	}

	@Test
	public void testFilterNotAuthorized()
		throws EntityNotFoundException
	{
		// set user-id in context, i.e. user is authenticated:
		dummyAuthenticationFilter.setUserIdInContext(true);
		
		// test-user hasn't got the (global) permission for reading users:
		Mockito.when(authorizationServicesMock.hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, REMOTE_READ, applicationName))
				.thenReturn(Boolean.FALSE);
		
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		Response response = invocationBuilder.get();
		assertEquals(401, response.getStatus());
		System.out.println(response.getStatus());

		// authorization is called:
		verify(authorizationServicesMock, times(1)).hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, REMOTE_READ, applicationName);	
		
		// userServices.findPersonById is NOT called because user is not authorized:
		verify(userServicesMock, times(0)).findPersonById(Mockito.anyString());	
	}

	@Test
	public void testFilterNoApplicationName()
		throws EntityNotFoundException
	{
		// set user-id in context, i.e. user is authenticated:
		dummyAuthenticationFilter.setUserIdInContext(true);
		
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();	
		// set new, empty headers, i.e. no application-name given:
		invocationBuilder.headers(new MultivaluedHashMap<String, Object>());
		
		Response response = invocationBuilder.get();
		System.out.println(response.getStatus());
		String msg = response.readEntity(String.class);
		
		assertEquals(400, response.getStatus());
		assertTrue(msg.contains("No application-name given!"));		

		// authorization is NOT called:
		verify(authorizationServicesMock, times(0)).hasPermissionTechUser(Mockito.anyString(), Mockito.any(), Mockito.anyString());	
	}


	@Test
	public void testFilterNotAuthenticated()
		throws EntityNotFoundException
	{
		// prevent dummyFilter from setting user-id in context, i.e. no user-authentication is registrated:
		dummyAuthenticationFilter.setUserIdInContext(false);
		
		Response response = buildInvocationTargetBuilder().get();
		System.out.println(response.getStatus());		
		assertEquals(401, response.getStatus());		

		// authorization is NOT called because user is not authenticated:
		verify(authorizationServicesMock, times(0)).hasPermissionTechUser(Mockito.anyString(), Mockito.any(), Mockito.anyString());	
	}
}
