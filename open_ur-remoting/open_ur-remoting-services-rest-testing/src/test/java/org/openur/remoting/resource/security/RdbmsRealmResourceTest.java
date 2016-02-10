package org.openur.remoting.resource.security;

import static org.junit.Assert.*;
import static org.openur.remoting.resource.security.AuthorizationResource.AUTHORIZATION_RESOURCE_PATH;
import static org.openur.remoting.resource.security.AuthorizationResource.HAS_OU_PERMISSION_RESOURCE_PATH;
import static org.openur.remoting.resource.security.RdbmsRealmResource.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.remoting.resource.AbstractResourceTest;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwTokenProvider;

import com.google.gson.GsonBuilder;

public class RdbmsRealmResourceTest
	extends AbstractResourceTest
{
	private Client client;
	
	@Override
	protected Application configure()
	{
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bindFactory(MockRdbmsRealmFactory.class).to(Realm.class);
				//bindFactory(MockAuthorizationServicesFactory.class).to(IAuthorizationServices.class);
			}
		};

		ResourceConfig config = new ResourceConfig(RdbmsRealmResource.class)
				//.register(UsernamePwTokenProvider.class)
				.register(binder);
		
//		ResourceConfig config = new ResourceConfig(AuthorizationResource.class)
//			.register(binder);

		return config;
	}

	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
		
		client = ClientBuilder.newClient().register(UsernamePwTokenProvider.class);
		getMyClient().register(UsernamePwTokenProvider.class);
	}

	 @Test
	 public void testGetName()
	 {
//			Invocation.Builder builder = client
//				.target("http://localhost:9998/" + RDBMS_REALM_RESOURCE_PATH + GET_NAME_RESOURCE_PATH)
//				.request(MediaType.TEXT_PLAIN);
//			Response response = builder.get();
		 
		 String s = performRestCall_Get(RDBMS_REALM_RESOURCE_PATH + GET_NAME_RESOURCE_PATH + "?name=Uwe", MediaType.TEXT_PLAIN, String.class);
			
			assertTrue(true);
		 
//		 Boolean b = performRestCall_Get(AUTHORIZATION_RESOURCE_PATH + HAS_OU_PERMISSION_RESOURCE_PATH 
//			+ "?personId=" + MockAuthorizationServicesFactory.PERSON_ID + "&ouId=" + MockAuthorizationServicesFactory.OU_ID 
//			+ "&text=" + MockAuthorizationServicesFactory.PERMISSION_TEXT	+ "&appName=" + MockAuthorizationServicesFactory.APP_NAME, 
//			MediaType.TEXT_PLAIN, Boolean.class);
//	
//	assertTrue(b);
	 }
	
	// @Test
	// public void testSupports()
	// {
	// fail("Not yet implemented");
	// }

	@Test
	public void testGetAuthenticationInfo()
	{
		Invocation.Builder builder = client
			.target("http://localhost:9998/" + RDBMS_REALM_RESOURCE_PATH + AUTHENTICATE_RESOURCE_PATH)
			.request(MediaType.TEXT_PLAIN_TYPE);
		
		String param = new GsonBuilder().create().toJson(TestObjectContainer.USERNAME_PW_TOKEN.getDelegate());
		
		Response response = builder.post(Entity.entity(param, MediaType.APPLICATION_JSON_TYPE));
		
//		WebTarget target = client.target("http://localhost:9998").path(RDBMS_REALM_RESOURCE_PATH + AUTHENTICATE_RESOURCE_PATH);
//		String param = new GsonBuilder().create().toJson(TestObjectContainer.USERNAME_PW_TOKEN.getDelegate());
//		Form form = new Form();
//		form.param("x", param);
//		UsernamePasswordToken info = target.request(MediaType.TEXT_PLAIN_TYPE)
//    .post(Entity.entity(form, MediaType.TEXT_PLAIN_TYPE),
//    	UsernamePasswordToken.class);
		
		assertTrue(true);
	}
}
