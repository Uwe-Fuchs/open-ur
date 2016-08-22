package org.openur.remoting.client.ws.rs.userstructure;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.openur.module.domain.security.secure_api.PermissionConstraints.REMOTE_READ;

import javax.ws.rs.core.Application;

import org.apache.shiro.realm.Realm;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.utils.compare.PersonComparer;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.module.util.exception.EntityNotFoundException;
import org.openur.remoting.resource.errorhandling.EntityNotFoundExceptionMapper;
import org.openur.remoting.resource.secure_api.AuthenticationFilter_BasicAuth;
import org.openur.remoting.resource.secure_api.AuthorizationFilter;
import org.openur.remoting.resource.secure_api.SecureApiSettings;
import org.openur.remoting.resource.userstructure.UserResource;
import org.openur.remoting.xchange.rest.providers.json.PersonProvider;

public class SecuredUserResourceClientTest
	extends JerseyTest
{
	private Boolean hashCredentials = Boolean.TRUE;
	private String applicationName;
	private UserResourceClient userResourceClient;
	private OpenUrRdbmsRealmMock realmMock;
	private IAuthorizationServices authorizationServicesMock;
	private IUserServices userServicesMock;
	private SomeUserBean userBean;

	@Override
	protected Application configure()
	{
		applicationName = "Demo-Application";
		realmMock = new OpenUrRdbmsRealmMock();
		authorizationServicesMock = Mockito.mock(IAuthorizationServices.class);
		userServicesMock = Mockito.mock(IUserServices.class);
		userResourceClient = new UserResourceClient("http://localhost:9998/");
		userResourceClient.setApplicationName(applicationName);
		userResourceClient.setUserName(OpenUrRdbmsRealmMock.USER_NAME_2);
		userResourceClient.setPassWord(OpenUrRdbmsRealmMock.PASSWORD_2);
		userResourceClient.setSecureApiSettings(SecureApiSettings.BASIC_AUTH_PERMCHECK.name());

		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bind(realmMock).to(Realm.class);
				bind(hashCredentials).to(Boolean.class);
				bind(authorizationServicesMock).to(IAuthorizationServices.class);
				bind(userServicesMock).to(IUserServices.class);		
			}
		};

		ResourceConfig config = new ResourceConfig(UserResource.class)
				.register(PersonProvider.class)
				.register(EntityNotFoundExceptionMapper.class)
				.register(AuthenticationFilter_BasicAuth.class)
				.register(AuthorizationFilter.class)
				.register(binder);

		return config;
	}
	
	@Before
	public void startUp()
	{
		userBean = new SomeUserBean(userResourceClient);
	}
	
	@Test
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
	
	private static class SomeUserBean
	{
		private final UserResourceClient myUserResourceClient;

		public SomeUserBean(UserResourceClient myUserResourceClient)
		{
			this.myUserResourceClient = myUserResourceClient;
		}
		
		public IPerson doSomeUserAction()
		{
			return myUserResourceClient.findPersonById(TestObjectContainer.PERSON_UUID_1);
		}
	}
}
