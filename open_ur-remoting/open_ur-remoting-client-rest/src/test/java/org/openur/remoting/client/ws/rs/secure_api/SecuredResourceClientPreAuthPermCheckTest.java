package org.openur.remoting.client.ws.rs.secure_api;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.utils.compare.PersonComparer;
import org.openur.remoting.resource.secure_api.AuthenticationFilter_J2eePreAuth;
import org.openur.remoting.resource.secure_api.AuthorizationFilter;
import org.openur.remoting.resource.secure_api.SecureApiSettings;
import org.openur.remoting.resource.secure_api.testing.DummyPreparePreAuthFilter;

@Ignore
public class SecuredResourceClientPreAuthPermCheckTest
	extends AbstractSecuredResourceClientTest
{
	@Override
	protected Application configure()
	{
		ResourceConfig config = (ResourceConfig) super.configure();
		
		userResourceClient.setSecureApiSettings(SecureApiSettings.PRE_AUTH_PERMCHECK.name());
		applicationName = "Demo-Application";
		userResourceClient.setApplicationName(applicationName);		
		config.register(AuthorizationFilter.class);
		config.register(new DummyPreparePreAuthFilter(TestObjectContainer.TECH_USER_UUID_2));
		config.register(AuthenticationFilter_J2eePreAuth.class);
		
		return config;
	}

	@Test
	public void testPreAuthenticated()
	{
		Mockito.when(userServicesMock.findTechnicalUserById(TestObjectContainer.TECH_USER_UUID_2)).thenReturn(TestObjectContainer.TECH_USER_2);
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);
		
		IPerson p = userBean.doSomeUserAction();
		verify(userServicesMock, times(1)).findTechnicalUserById(TestObjectContainer.TECH_USER_UUID_2);
		assertEquals(0, realmMock.getAuthCounter());
		verify(userServicesMock, times(1)).findPersonById(TestObjectContainer.PERSON_UUID_1);

		assertNotNull(p);
		System.out.println("Result: " + p);		
		assertTrue(new PersonComparer().objectsAreEqual(TestObjectContainer.PERSON_1, (Person) p));
	}

	@Test
	public void testNotPreAuthenticated()
	{
		
	}
}
