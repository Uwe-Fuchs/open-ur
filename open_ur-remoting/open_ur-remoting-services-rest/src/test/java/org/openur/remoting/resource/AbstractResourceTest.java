package org.openur.remoting.resource;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;

public class AbstractResourceTest
	extends JerseyTest
{
	private Client client;
	
	@Before
	public void setUp()
		throws Exception
	{
		super.setUp();
		
		client = ClientBuilder.newClient();
	}
	
	@After
	public void tearDown()
		throws Exception
	{
		super.tearDown();
		
		client.close();
	}
	
	protected String performRestCall(String url)
	{
		Response response = client
				.target("http://localhost:9998/" + url)
				.request(MediaType.APPLICATION_JSON)
				.get();
		
		assertEquals(200, response.getStatus());
		String result = response.readEntity(String.class);
		response.close();
		
		System.out.println("Result: " + result);		
		
		return result;
	}
}
