package org.openur.remoting.resource.userstructure;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.openur.module.service.userstructure.IUserServices;

public class UserResourceTest
	extends JerseyTest
{
	@Override
	protected Application configure()
	{
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bindFactory(MockUserServicesFactory.class).to(IUserServices.class);
			}
		};
		
		ResourceConfig config = new ResourceConfig(UserResource.class);
		config.register(binder);
		
		return config;
	}

	@Test
  public void testMockedGreetingService() {
      Client client = ClientBuilder.newClient();
      Response response = client.target("http://localhost:9998/userstructure")
              .request(MediaType.TEXT_PLAIN).get();
      Assert.assertEquals(200, response.getStatus());

      String msg = response.readEntity(String.class);
      Assert.assertEquals("Got it!", msg);
      System.out.println("Message: " + msg);

      response.close();
      client.close();
  }
}
