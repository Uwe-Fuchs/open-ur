package org.openur.remoting.resource.secure_api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.openur.remoting.resource.secure_api.PermissionConstraints.REMOTE_READ;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import org.junit.Test;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.utils.compare.PersonComparer;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;
import org.openur.module.util.exception.EntityNotFoundException;

public class SecurityFilterHashedUsernamePwTest
	extends AbstractSecurityFilterTest
{
	@Override
	protected Application configure()
	{
		settings = SecureApiSettings.HASHED_CREDENTIIALS;
		
		return super.configure();
	}

	@Test
	public void testFilter()
		throws UnsupportedEncodingException, EntityNotFoundException
	{
		Mockito.when(authorizationServicesMock.hasPermissionTechUser(OpenUrRdbmsRealmMock.TECH_USER_UUID_2, REMOTE_READ, applicationName))
				.thenReturn(Boolean.TRUE);
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);

		Invocation.Builder invocationBuilder = buildInvocationTargetBuilder();
		String token = OpenUrRdbmsRealmMock.USER_NAME_2 + ":" + OpenUrRdbmsRealmMock.PASSWORD_2;
		
		// add hashed credential to request-headers:
		invocationBuilder.header(SecurityFilter_UsernamePw.AUTHENTICATION_PROPERTY, DatatypeConverter.printBase64Binary(token.getBytes("UTF-8")));
		
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
}
