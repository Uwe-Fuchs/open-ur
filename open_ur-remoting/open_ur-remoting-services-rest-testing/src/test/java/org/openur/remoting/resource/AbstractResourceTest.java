package org.openur.remoting.resource;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.Validate;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;

public abstract class AbstractResourceTest
	extends JerseyTest
{
	private final String baseUri = "http://localhost:9998/";	
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
	
	protected <E, R> R performRestCall(String url, String httpMethod, String acceptMediaType, String contentMediaType, Class<R> resultType, E object)
	{
		return internalRestCall(url, httpMethod, acceptMediaType, contentMediaType, resultType, null, object);
	}
	
	protected <E, R> R performRestCall(String url, String httpMethod, String acceptMediaType, String contentMediaType, GenericType<R> genericResultType, E object)
	{
		return internalRestCall(url, httpMethod, acceptMediaType, contentMediaType, null, genericResultType, object);
	}
	
	protected <T> T performRestCall_GET(String url, String acceptMediaType, Class<T> resultType)
	{
		return internalRestCall(url, HttpMethod.GET, acceptMediaType, MediaType.TEXT_PLAIN, resultType, null, (Object) null);
	}
	
	protected <T> T performRestCall_GET(String url, String acceptMediaType, GenericType<T> genericResultType)
	{
		return internalRestCall(url, HttpMethod.GET, acceptMediaType, MediaType.TEXT_PLAIN, null, genericResultType, (Object) null);
	}

	private <E, R> R internalRestCall(String url, String httpMethod, String acceptMediaType, String contentMediaType, Class<R> resultClassType, GenericType<R> genericResultType, E object)
	{
		Invocation.Builder builder = client
				.target(getBaseURI() + url)
				.request()
				.accept(acceptMediaType);
		
		Validate.isTrue(!((httpMethod == HttpMethod.POST || httpMethod == HttpMethod.PUT) && object == null), 
					"object must not be null when calling REST-resource via PUT or POST");
		
		Entity<E> entity = null;
		
		if (object != null)
		{
			entity = Entity.entity(object, contentMediaType);			
		}
		
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

		R result = resultClassType != null ? response.readEntity(resultClassType) : response.readEntity(genericResultType);
		System.out.println("Result: " + result);
	
		return result;	
	}

	protected String getBaseURI()
	{
		return baseUri;
	}
}
