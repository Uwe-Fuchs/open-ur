package org.openur.remoting.client.ws.rs;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.Validate;
import org.glassfish.jersey.client.ClientConfig;

public abstract class AbstractResourceClient
{
	private final List<Class<?>> providers;
	private final String baseUrl;

	public AbstractResourceClient(String baseUrl, Class<?>... newProviders)
	{
		super();

		Validate.notEmpty(baseUrl, "Base-URL must not be empty!");
		this.baseUrl = baseUrl;

		newProviders = newProviders != null ? newProviders : new Class<?>[] {};
		List<Class<?>> providersTmp = Arrays.asList(newProviders);
		this.providers = Collections.unmodifiableList(providersTmp);
	}

	protected String getBaseUrl()
	{
		return baseUrl;
	}

	private Client createJerseyClient()
	{
		ClientConfig clientConfig = new ClientConfig();

		for (Class<?> clazz : this.providers)
		{
			clientConfig.register(clazz);
		}

		return ClientBuilder.newClient(clientConfig);
	}

	private <E, R> R internalRestCall(String url, String httpMethod, String acceptMediaType, String contentMediaType, Class<R> resultClassType, GenericType<R> genericResultType, E object)
	{
		Client client = createJerseyClient();

		Invocation.Builder builder = client.target(url).request().accept(acceptMediaType);

		Validate.isTrue(!((httpMethod == HttpMethod.POST || httpMethod == HttpMethod.PUT) && object == null), "object must not be null when calling REST-resource via PUT or POST");

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

		return result;
	}

	protected <T> T performRestCall(String url, String acceptMediaType, Class<T> resultType)
	{
		return internalRestCall(url, HttpMethod.GET, acceptMediaType, MediaType.TEXT_PLAIN, resultType, null, null);
	}

	protected <T> T performRestCall(String url, String acceptMediaType, GenericType<T> resultType)
	{
		return internalRestCall(url, HttpMethod.GET, acceptMediaType, MediaType.TEXT_PLAIN, null, resultType, null);
	}
	
	protected <E, R> R performRestCall(String url, String httpMethod, String acceptMediaType, String contentMediaType, Class<R> resultType, E object)
	{
		return internalRestCall(url, httpMethod, acceptMediaType, contentMediaType, resultType, null, object);
	}
	
	protected <E, R> R performRestCall(String url, String httpMethod, String acceptMediaType, String contentMediaType, GenericType<R> genericResultType, E object)
	{
		return internalRestCall(url, httpMethod, acceptMediaType, contentMediaType, null, genericResultType, object);
	}

	public List<Class<?>> getProviders()
	{
		return providers;
	}
}
