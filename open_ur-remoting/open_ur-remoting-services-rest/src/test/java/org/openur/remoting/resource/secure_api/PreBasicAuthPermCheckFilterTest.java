package org.openur.remoting.resource.secure_api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.utils.compare.PersonComparer;
import org.openur.module.integration.security.shiro.testing.OpenUrRdbmsRealmMock;
import org.openur.module.util.exception.EntityNotFoundException;

public class PreBasicAuthPermCheckFilterTest
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
		config.register(AuthorizationFilter.class);
		
		return config;
	}

	@Test
	public void testFilterPreAuthenticatedAndAuthorized()
		throws EntityNotFoundException
	{
		// add security-context to request containing UserPrinicipal-Object:
		dummyPreparePreAuthFilter.setSecurityContextInRequestContext(true);
		
		Mockito.when(userServicesMock.findTechnicalUserById(TestObjectContainer.TECH_USER_UUID_2)).thenReturn(TestObjectContainer.TECH_USER_2);
		Mockito.when(authorizationServicesMock.hasPermissionTechUser(TestObjectContainer.TECH_USER_UUID_2, REMOTE_READ, applicationName))
				.thenReturn(Boolean.TRUE);
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);
		
		Response response = buildInvocationTargetBuilder().get();		
		assertEquals(200, response.getStatus());
		System.out.println(response.getStatus());

		// userServices.findTechnicalUserById() is called to verify found pre-authentication:
		verify(userServicesMock, times(1)).findTechnicalUserById(TestObjectContainer.TECH_USER_UUID_2);

		// basic authentication is NOT called, because user is already pre-authenticed:
		assertEquals(0, realmMock.getAuthCounter());

		// authorization is called:
		verify(authorizationServicesMock, times(1)).hasPermissionTechUser(TestObjectContainer.TECH_USER_UUID_2, REMOTE_READ, applicationName);

		IPerson p = response.readEntity(Person.class);
		assertNotNull(p);
		System.out.println("Result: " + p);		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, (Person) p));
	}

	@Test
	public void testFilterPreAuthenticatedButNotAuthorized()
		throws UnsupportedEncodingException, EntityNotFoundException
	{
		// add security-context to request containing UserPrinicipal-Object:
		dummyPreparePreAuthFilter.setSecurityContextInRequestContext(true);
		
		Mockito.when(userServicesMock.findTechnicalUserById(TestObjectContainer.TECH_USER_UUID_2)).thenReturn(TestObjectContainer.TECH_USER_2);
		
		// reject permission for authorization for this (technical) user:
		Mockito.when(authorizationServicesMock.hasPermissionTechUser(TestObjectContainer.TECH_USER_UUID_2, REMOTE_READ, applicationName))
				.thenReturn(Boolean.FALSE);

		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		
		// add hashed credential to request-headers:
		invocationBuilder.header(AbstractSecurityFilterBase.AUTHENTICATION_PROPERTY, buildHashedAuthString());
		
		Response response = invocationBuilder.get();		
		assertEquals(401, response.getStatus());
		System.out.println(response.getStatus());

		// userServices.findTechnicalUserById() is called to verify found pre-authentication:
		verify(userServicesMock, times(1)).findTechnicalUserById(TestObjectContainer.TECH_USER_UUID_2);

		// basic authentication is NOT called, because user is already pre-authenticed:
		assertEquals(0, realmMock.getAuthCounter());

		// authorization is called:
		verify(authorizationServicesMock, times(1)).hasPermissionTechUser(TestObjectContainer.TECH_USER_UUID_2, REMOTE_READ, applicationName);
		
		// userServices.findPersonById is NOT called because user is not authorized:
		verify(userServicesMock, times(0)).findPersonById(TestObjectContainer.PERSON_UUID_1);
	}

	@Test
	public void testNotPreButBasicAuthenticatedAndAuthorized()
		throws UnsupportedEncodingException, EntityNotFoundException
	{
		// DO NOT ADD security-context containing UserPrinicipal-Object, hence no pre-authenticated user is found in request:
		dummyPreparePreAuthFilter.setSecurityContextInRequestContext(false);
		
		Mockito.when(authorizationServicesMock.hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, REMOTE_READ, applicationName))
			.thenReturn(Boolean.TRUE);
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);
	
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		
		// add hashed credential to request-headers:
		invocationBuilder.header(AbstractSecurityFilterBase.AUTHENTICATION_PROPERTY, buildHashedAuthString());
		
		Response response = invocationBuilder.get();
		assertEquals(200, response.getStatus());
		System.out.println(response.getStatus());

		// userServices.findTechnicalUserById() is NOT called because no pre-authentication was found:
		verify(userServicesMock, times(0)).findTechnicalUserById(anyString());

		// basic authentication is called, because user is NOT pre-authenticed:
		assertEquals(1, realmMock.getAuthCounter());
	
		// authorization is called:
		verify(authorizationServicesMock, times(1)).hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, REMOTE_READ, applicationName);
	
		Person p = response.readEntity(Person.class);
		assertNotNull(p);
		System.out.println(p);		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, p));
	}

	@Test
	public void testNotPreAuthenticatedInvalidBasicCredentials()
		throws UnsupportedEncodingException, EntityNotFoundException
	{
		// DO NOT ADD security-context containing UserPrinicipal-Object, hence no pre-authenticated user is found in request:
		dummyPreparePreAuthFilter.setSecurityContextInRequestContext(false);
		
		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		
		// add INVALID credentials to request-headers:
		invocationBuilder.header(AbstractSecurityFilterBase.AUTHENTICATION_PROPERTY, buildHashedAuthString() + "appendSomeWrongPassword");
		
		Response response = invocationBuilder.get();		
		assertEquals(401, response.getStatus());
		System.out.println(response.getStatus());

		// userServices.findTechnicalUserById() is NOT called because no pre-authentication was found:
		verify(userServicesMock, times(0)).findTechnicalUserById(anyString());

		// basic authentication is called, because user is NOT pre-authenticed:
		assertEquals(1, realmMock.getAuthCounter());

		// authorization is NOT called, because authentication failed:
		verify(authorizationServicesMock, times(0)).hasPermissionTechUser(anyString(), anyString(), anyString());

		// userservie-method is NOT called, because authentication failed:
		verify(userServicesMock, times(0)).findPersonById(TestObjectContainer.PERSON_UUID_1);		
	}
}
