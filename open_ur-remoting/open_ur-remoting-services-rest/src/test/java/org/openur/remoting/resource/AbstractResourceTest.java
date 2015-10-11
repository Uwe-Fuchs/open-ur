package org.openur.remoting.resource;

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
		return internalRestCall(url, mediaType, resultType, null);
	}

	protected <T> T performRestCall(String url, Class<T> resultType)
	{
		return internalRestCall(url, MediaType.APPLICATION_JSON, resultType, null);
	}

	protected <T> T performRestCall(String url, String mediaType, GenericType<T> resultType)
	{
		return internalRestCall(url, mediaType, null, resultType);
	}

	protected <T> T performRestCall(String url, GenericType<T> resultType)
	{
		return internalRestCall(url, MediaType.APPLICATION_JSON, null, resultType);
	}

	private <T> T internalRestCall(String url, String mediaType, Class<T> resultClassType, GenericType<T> genericResultType)
	{
		Response response = client
			.target("http://localhost:9998/" + url)
			.request(mediaType)
			.get();
		
		T result = resultClassType != null ? response.readEntity(resultClassType) : response.readEntity(genericResultType);
	
		return result;		
	}
}
