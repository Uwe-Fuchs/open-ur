package org.openur.remoting.resource.secure_api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.utils.compare.PersonComparer;
import org.openur.module.util.exception.EntityNotFoundException;

public class SecurityFilterNoSecurityTest
	extends AbstractSecurityFilterTest
{
	@Override
	protected Application configure()
	{
		return super.configure();
	}

	@Test
	public void testFilter()
		throws EntityNotFoundException
	{
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);

		// do not add user-credentials to REST-request:
		Response response = buildInvocationTargetBuilder().get();
		
		// get data anyway because security is switched off at server-api:
		assertEquals(200, response.getStatus());
		System.out.println(response.getStatus());

		Person p = response.readEntity(Person.class);		
		assertNotNull(p);
		System.out.println(p);
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, p));
		
		// no authentication and authorization is performed:
		assertEquals(0, realmMock.getAuthCounter());
		verify(authorizationServicesMock, times(0)).hasPermission(anyString(), anyString(), anyString());
	}
}
