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

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.utils.compare.PersonComparer;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;
import org.openur.module.util.exception.EntityNotFoundException;
import org.openur.remoting.resource.secure_api.AuthenticationFilter_BasicAuth;
import org.openur.remoting.resource.secure_api.SecureApiSettings;

public class SecuredResourceClientBasicAuthTest
	extends AbstractSecuredResourceClientTest
{
	@Override
	protected Application configure()
	{		
		ResourceConfig config = (ResourceConfig) super.configure();
		
		userResourceClient.setSecureApiSettings(SecureApiSettings.BASIC_AUTH.name());
		userResourceClient.setUserName(OpenUrRdbmsRealmMock.USER_NAME_2);
		userResourceClient.setPassWord(OpenUrRdbmsRealmMock.PASSWORD_2);
		
		config.register(AuthenticationFilter_BasicAuth.class);

		return config;
	}

	@Test
	@Override
	public void testFilterValidCredentials()
		throws EntityNotFoundException
	{
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);
		
		IPerson p = userBean.doSomeUserAction();
		assertEquals(1, realmMock.getAuthCounter());
		verify(authorizationServicesMock, times(0)).hasPermissionTechUser(Mockito.eq(OpenUrRdbmsRealmMock.TECH_USER_UUID_2), Mockito.eq(REMOTE_READ), 
				Mockito.anyString());
		verify(userServicesMock, times(1)).findPersonById(TestObjectContainer.PERSON_UUID_1);

		assertNotNull(p);
		System.out.println("Result: " + p);		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, (Person) p));
	}

	@Test
	@Override
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
			assertEquals(1, realmMock.getAuthCounter());
			verify(authorizationServicesMock, times(0)).hasPermissionTechUser(Mockito.eq(OpenUrRdbmsRealmMock.TECH_USER_UUID_2), Mockito.eq(REMOTE_READ), 
					Mockito.anyString());
			verify(userServicesMock, times(0)).findPersonById(TestObjectContainer.PERSON_UUID_1);
		}
	}
}
