package org.openur.remoting.client.ws.rs.secure_api;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Before;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.integration.security.shiro.testing.OpenUrRdbmsRealmMock;
import org.openur.remoting.client.ws.rs.userstructure.UserResourceClient;
import org.openur.remoting.resource.secure_api.SecureApiSettings;

public class AbstractSecuredResourceClientTest
	extends AbstractSecurityClientFilterTest
{
	protected Boolean hashCredentials;
	protected UserResourceClient userResourceClient;
	protected SomeUserBean userBean;
	
	@Override
	protected Application configure()
	{
		ResourceConfig config = (ResourceConfig) super.configure();
		userResourceClient = new UserResourceClient("http://localhost:9998/");	
		userResourceClient.setSecureApiSettings(SecureApiSettings.BASIC_AUTH.name());
		userResourceClient.setUserName(OpenUrRdbmsRealmMock.USER_NAME_2);
		userResourceClient.setPassWord(OpenUrRdbmsRealmMock.PASSWORD_2);

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
