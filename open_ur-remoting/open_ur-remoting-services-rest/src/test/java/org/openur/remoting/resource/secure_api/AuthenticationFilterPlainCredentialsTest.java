package org.openur.remoting.resource.secure_api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.utils.compare.PersonComparer;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;

public class AuthenticationFilterPlainCredentialsTest
	extends AbstractAuthenticationFilterTest
{
	@Override
	protected Application configure()
	{
		settings = SecureApiSettings.PLAIN_CREDENTIALS;
		
		return super.configure();
	}

	@Test
	public void testFilterValidCredentials()
	{
		Mockito.when(authorizationServicesMock.hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, remoteAuthenticationPermissionName, applicationName))
				.thenReturn(Boolean.TRUE);
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);

		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		invocationBuilder.header(AuthenticationFilter.AUTHORIZATION_PROPERTY, OpenUrRdbmsRealmMock.USER_NAME_2 + ":" + OpenUrRdbmsRealmMock.PASSWORD_2);
		Response response = invocationBuilder.get();
		assertEquals(200, response.getStatus());
		System.out.println(response.getStatus());

		Person p = response.readEntity(Person.class);
		assertNotNull(p);
		System.out.println(p);		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, p));

		assertEquals(1, realmMock.getAuthCounter());
		verify(authorizationServicesMock, times(1)).hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, remoteAuthenticationPermissionName, applicationName);
	}

	@Test
	public void testFilterWrongPassword()
	{
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		// set invalid credentials:
		invocationBuilder.header(AuthenticationFilter.AUTHORIZATION_PROPERTY, OpenUrRdbmsRealmMock.USER_NAME_2 + ":" + "someWrongPassword");
		Response response = invocationBuilder.get();
		assertEquals(401, response.getStatus());
		System.out.println(response.getStatus());

		// authentication is called, but authorization isn't (because of authentication-failure):
		assertEquals(1, realmMock.getAuthCounter());
		verify(authorizationServicesMock, times(0)).hasPermissionTechUser(anyString(), anyString(), anyString());
	}

	@Test
	public void testFilterNotAuthorized()
	{
		Mockito.when(authorizationServicesMock.hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, remoteAuthenticationPermissionName, applicationName))
				.thenReturn(Boolean.FALSE);

		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		// set valid credentials:
		invocationBuilder.header(AuthenticationFilter.AUTHORIZATION_PROPERTY, OpenUrRdbmsRealmMock.USER_NAME_2 + ":" + OpenUrRdbmsRealmMock.PASSWORD_2);
		Response response = invocationBuilder.get();
		assertEquals(401, response.getStatus());
		System.out.println(response.getStatus());

		assertEquals(1, realmMock.getAuthCounter());
		verify(authorizationServicesMock, times(1)).hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, remoteAuthenticationPermissionName, applicationName);
	}

	@Test
	public void testFilterNoApplicationName()
	{
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		// set new, empty headers, i.e. no application-name given:
		invocationBuilder.headers(new MultivaluedHashMap<String, Object>());
		// set valid credentials:
		invocationBuilder.header(AuthenticationFilter.AUTHORIZATION_PROPERTY, OpenUrRdbmsRealmMock.USER_NAME_2 + ":" + OpenUrRdbmsRealmMock.PASSWORD_2);
		
		Response response = invocationBuilder.get();
		assertEquals(400, response.getStatus());
		System.out.println(response.getStatus());
		String msg = response.readEntity(String.class);
		assertTrue(msg.contains("No application-name given!"));
	}

	@Test
	public void testFilterNoCredentials()
	{
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		
		Response response = invocationBuilder.get();
		assertEquals(401, response.getStatus());
		System.out.println(response.getStatus());
		String msg = response.readEntity(String.class);
		assertTrue(msg.contains("No credentials found!"));
	}
}
