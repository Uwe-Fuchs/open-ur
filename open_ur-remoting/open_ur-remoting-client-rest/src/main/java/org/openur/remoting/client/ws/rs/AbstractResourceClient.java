package org.openur.remoting.client.ws.rs;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
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

	private <T> T internalRestCall(String url, String mediaType, Class<T> resultClassType, GenericType<T> genericResultType)
	{
		Client client = createJerseyClient();
		
		Response response = client
			.target(url)
			.request(mediaType)
			.get();
		
		T result = resultClassType != null ? response.readEntity(resultClassType) : response.readEntity(genericResultType);

		client.close();
	
		return result;		
	}

	protected <T> T performRestCall(String url, Class<T> resultType)
	{
		return internalRestCall(url, MediaType.APPLICATION_JSON, resultType, null);
	}

	protected <T> T performRestCall(String url, GenericType<T> resultType)
	{
		return internalRestCall(url, MediaType.APPLICATION_JSON, null, resultType);
	}

	protected <T> T performRestCall(String url, String mediaType, Class<T> resultType)
	{
		return internalRestCall(url, mediaType, resultType, null);
	}

	protected <T> T performRestCall(String url, String mediaType, GenericType<T> resultType)
	{
		return internalRestCall(url, mediaType, null, resultType);
	}

	public List<Class<?>> getProviders()
	{
		return providers;
	}
}
