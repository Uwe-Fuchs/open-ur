package org.openur.remoting.resource;

import java.net.URI;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

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

	protected <T> T performRestCall_Get(String url, String mediaType, Class<T> resultType)
	{		
		return performRestCall(url, mediaType, resultType, HttpMethod.GET, null);
	}

	protected <T> T performRestCall_Get(String url, Class<T> resultType)
	{
		return performRestCall(url, resultType, HttpMethod.GET, null);
	}

	protected <T> T performRestCall_Get(String url, String mediaType, GenericType<T> resultType)
	{
		return performRestCall(url, mediaType, resultType, HttpMethod.GET, null);
	}

	protected <T> T performRestCall_Get(String url, GenericType<T> resultType)
	{
		return performRestCall(url, resultType, HttpMethod.GET, null);
	}

	protected <T> T performRestCall(String url, String mediaType, Class<T> resultType, String httpMethod, Entity<?> entity)
	{		
		return internalRestCall(url, mediaType, resultType, null, httpMethod, entity);
	}

	protected <T> T performRestCall(String url, Class<T> resultType, String httpMethod, Entity<?> entity)
	{
		return internalRestCall(url, MediaType.APPLICATION_JSON, resultType, null, httpMethod, entity);
	}

	protected <T> T performRestCall(String url, String mediaType, GenericType<T> resultType, String httpMethod, Entity<?> entity)
	{
		return internalRestCall(url, mediaType, null, resultType, httpMethod, entity);
	}

	protected <T> T performRestCall(String url, GenericType<T> resultType, String httpMethod, Entity<?> entity)
	{
		return internalRestCall(url, MediaType.APPLICATION_JSON, null, resultType, httpMethod, entity);
	}
	
	private <T> T internalRestCall(String url, String mediaType, Class<T> resultClassType, GenericType<T> genericResultType, String httpMethod, Entity<?> entity)
	{
		Invocation.Builder builder = client
				.target("http://localhost:9998/" + url)
				.request(mediaType);
		
		Response response;
		
		switch (httpMethod)
		{
			case HttpMethod.GET:
				response = builder.get();
				break;
			case HttpMethod.DELETE:
				response = builder.delete();
				break;
			case HttpMethod.POST:
				response = builder.post(entity);
				break;
			case HttpMethod.PUT:
				response = builder.put(entity);
				break;
			default:
				response = builder.get();
				break;
		}

		T result = resultClassType != null ? response.readEntity(resultClassType) : response.readEntity(genericResultType);
		System.out.println("Result: " + result);
	
		return result;	
	}

	protected URI getBaseURI()
	{
		return UriBuilder.fromUri("http://localhost:9998/").build();
	}
}
