package org.openur.remoting.resource;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;

public abstract class AbstractResourceTest
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

	protected Client getMyClient()
	{
		return client;
	}

	protected <T> T performRestCall(String url, String mediaType, Class<T> resultType)
	{
		Response response = internalRestCall(url, mediaType);
		T result = response.readEntity(resultType);

		System.out.println("Result: " + result);

		return result;
	}

	protected <T> T performRestCall(String url, Class<T> resultType)
	{
		return performRestCall(url, MediaType.APPLICATION_JSON, resultType);
	}

	protected <T> T performRestCall(String url, String mediaType, GenericType<T> resultType)
	{
		Response response = internalRestCall(url, mediaType);
		T result = response.readEntity(resultType);

		System.out.println("Result: " + result);

		return result;
	}

	protected <T> T performRestCall(String url, GenericType<T> resultType)
	{
		return performRestCall(url, MediaType.APPLICATION_JSON, resultType);
	}
	
	private Response internalRestCall(String url, String mediaType)
	{
		Response response = client
				.target("http://localhost:9998/" + url)
				.request(mediaType)
				.get();
	
		assertEquals(200, response.getStatus());
		
		return response;
	}
}
