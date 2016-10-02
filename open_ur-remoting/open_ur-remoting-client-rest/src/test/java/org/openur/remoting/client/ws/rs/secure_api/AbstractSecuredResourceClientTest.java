package org.openur.remoting.client.ws.rs.secure_api;

import javax.ws.rs.core.Application;

import org.apache.shiro.realm.Realm;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealmMock;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.module.service.userstructure.IUserServices;
import org.openur.remoting.client.ws.rs.userstructure.UserResourceClient;
import org.openur.remoting.resource.errorhandling.EntityNotFoundExceptionMapper;
import org.openur.remoting.resource.userstructure.UserResource;
import org.openur.remoting.xchange.rest.providers.json.PersonProvider;

public abstract class AbstractSecuredResourceClientTest
	extends JerseyTest
{
	protected Boolean hashCredentials = Boolean.TRUE;
	protected String applicationName;
	protected UserResourceClient userResourceClient;
	protected OpenUrRdbmsRealmMock realmMock;
	protected IAuthorizationServices authorizationServicesMock;
	protected IUserServices userServicesMock;
	protected SomeUserBean userBean;

	@Override
	protected Application configure()
	{		
		realmMock = new OpenUrRdbmsRealmMock();
		authorizationServicesMock = Mockito.mock(IAuthorizationServices.class);
		userServicesMock = Mockito.mock(IUserServices.class);
		userResourceClient = new UserResourceClient("http://localhost:9998/");

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
				.register(binder);

		return config;
	}
	
	@Before
	public void startUp()
	{
		userBean = new SomeUserBean(userResourceClient);
	}
	
	static class SomeUserBean
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
