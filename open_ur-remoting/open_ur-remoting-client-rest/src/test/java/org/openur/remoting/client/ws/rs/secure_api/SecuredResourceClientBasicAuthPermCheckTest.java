package org.openur.remoting.client.ws.rs.secure_api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.openur.module.domain.security.secure_api.PermissionConstraints.REMOTE_READ;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.utils.compare.PersonComparer;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;
import org.openur.module.util.exception.EntityNotFoundException;
import org.openur.remoting.resource.secure_api.AuthorizationFilter;
import org.openur.remoting.resource.secure_api.SecureApiSettings;

public class SecuredResourceClientBasicAuthPermCheckTest
	extends SecuredResourceClientBasicAuthTest
{
	@Override
	protected Application configure()
	{		
		ResourceConfig config = (ResourceConfig) super.configure();
		
		userResourceClient.setSecureApiSettings(SecureApiSettings.BASIC_AUTH_PERMCHECK.name());
		config.register(AuthorizationFilter.class);

		return config;
	}

	@Test
	@Override
	public void test()
		throws EntityNotFoundException
	{
		Mockito.when(authorizationServicesMock.hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, REMOTE_READ, applicationName)).thenReturn(Boolean.TRUE);
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);
		
		IPerson p = userBean.doSomeUserAction();
		assertEquals(1, realmMock.getAuthCounter());
		verify(authorizationServicesMock, times(1)).hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, REMOTE_READ, applicationName);
		verify(userServicesMock, times(1)).findPersonById(TestObjectContainer.PERSON_UUID_1);

		assertNotNull(p);
		System.out.println("Result: " + p);		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, (Person) p));
	}

	@Test
	public void testFilterInvalidCredentials()
		throws EntityNotFoundException
	{
		try
		{
			userResourceClient.setPassWord("someWrongPassword");
			userBean.doSomeUserAction();
			fail("expected WebApplicationException not thrown!");
		} catch (WebApplicationException e)
		{
			assertEquals(401, e.getResponse().getStatus());
			assertEquals(Status.fromStatusCode(401).getReasonPhrase(), e.getMessage());
			assertEquals(1, realmMock.getAuthCounter());
			verify(authorizationServicesMock, times(0)).hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, REMOTE_READ, applicationName);
			verify(userServicesMock, times(0)).findPersonById(TestObjectContainer.PERSON_UUID_1);
		}
	}
}
