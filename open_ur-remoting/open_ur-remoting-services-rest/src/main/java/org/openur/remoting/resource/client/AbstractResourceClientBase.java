package org.openur.remoting.resource.client;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.openur.remoting.xchange.rest.errorhandling.ErrorMessage;
import org.openur.remoting.xchange.rest.providers.json.ErrorMessageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractResourceClientBase
{
	private static final Logger LOG = LoggerFactory.getLogger(AbstractResourceClientBase.class);
	
	private final List<Class<?>> providers = new ArrayList<>();
	private final String baseUrl;

	public AbstractResourceClientBase(String baseUrl, Class<?>... newProviders)
	{
		this(baseUrl);
		
		if (ArrayUtils.isNotEmpty(newProviders) && newProviders[0] != null)
		{
			this.providers.addAll(Arrays.asList(newProviders));
		}
		
		this.providers.add(ErrorMessageProvider.class);
	}

	public AbstractResourceClientBase(String baseUrl)
	{
		super();

		Validate.notBlank(baseUrl, "Base-URL must not be blank!");
		this.baseUrl = baseUrl;
	}

	protected String getBaseUrl()
	{
		return baseUrl;
	}

	public void addProvider(Class<?> newProvider)
	{
		Validate.notNull(newProvider, "new provider must not be null!");
		this.providers.add(newProvider);
	}

	public List<Class<?>> getProviders()
	{
		return providers;
	}

	private Client createJerseyClient()
	{
		ClientBuilder builder = ClientBuilder.newBuilder();

		for (Class<?> clazz : this.providers)
		{
			builder.register(clazz);
		}
		
		setSecurityFilters(builder);

		return builder.build();
	}
	
	protected void setSecurityFilters(ClientBuilder builder)
	{
		// do nothing			
	}

	protected <T> T performRestCall_GET(String url, String acceptMediaType, Class<T> resultType)
	{
		return internalRestCall(url, HttpMethod.GET, acceptMediaType, MediaType.TEXT_PLAIN, resultType, null, null);
	}

	protected <T> T performRestCall_GET(String url, String acceptMediaType, GenericType<T> resultType)
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

	// protected scope for test-purposes!
	protected <E, R> R internalRestCall(String url, String httpMethod, String acceptMediaType, String contentMediaType, Class<R> resultClassType, GenericType<R> genericResultType, E object)
	{
		Client client = createJerseyClient();

		Invocation.Builder builder = client.target(getBaseUrl() + url).request().accept(acceptMediaType);

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
		
		return handleResponse(resultClassType, genericResultType, response);
	}

	// protected scope for test-purposes!
	@SuppressWarnings("unchecked")
	protected <R> R handleResponse(Class<R> resultClassType, GenericType<R> genericResultType, Response response)
	{
		if (response == null)
		{
			throw new WebApplicationException("Server-Response was null!");
		}
		
		if (response.getStatus() >= 400)
		{
			WebApplicationException ex = null;
			
			try
			{
				ErrorMessage errorMessage = response.readEntity(ErrorMessage.class);
				Class<?> clazz = Class.forName(errorMessage.getExceptionClassName());
				Constructor<?> constructor = clazz.getConstructor(String.class);
				Throwable t = (Throwable) constructor.newInstance(errorMessage.getMessage());
				ex = new WebApplicationException(errorMessage.getMessage(), t, response);
			} catch (Exception ignored)
			{
				String reasonPhrase;
				
				try
				{
					reasonPhrase = Status.fromStatusCode(response.getStatus()).getReasonPhrase();
				} catch (Exception alsoIgnored)
				{
					reasonPhrase = "Unrecognized error!";
				}
				
				ex = new WebApplicationException(reasonPhrase, response.getStatus());
			}
			
			LOG.error(ex.toString());
			
			throw ex;
		}
		
		if (!Response.class.equals(genericResultType) && !Response.class.equals(resultClassType))		
		{						
			return resultClassType != null ? response.readEntity(resultClassType) : response.readEntity(genericResultType);
		}
		
		return (R) response;
	}
}