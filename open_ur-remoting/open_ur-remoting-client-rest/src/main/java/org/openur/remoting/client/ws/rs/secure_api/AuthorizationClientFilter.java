package org.openur.remoting.client.ws.rs.secure_api;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.Validate;
import org.openur.remoting.resource.secure_api.AbstractSecurityFilterBase;

/**
 * This filter adds a header containing the application-name to a client-request to an Open-UR-REST-Resource.
 * 
 * @author info@uwefuchs.com
 */
public class AuthorizationClientFilter
	implements ClientRequestFilter
{
	private final String applicationName;

	/**
	 * initalizing the client-filter, set name of the application (is mandatory
	 * for authorization).
	 * 
	 * @param applicationName
	 */
	public AuthorizationClientFilter(String applicationName)
	{
		super();

		Validate.notNull(applicationName, "application-name must not be null!");
		this.applicationName = applicationName;
	}

	@Override
	public void filter(ClientRequestContext requestContext)
		throws IOException
	{
		MultivaluedMap<String, Object> headers = requestContext.getHeaders();

		// add application-namec to request-headers:
		headers.add(AbstractSecurityFilterBase.APPLICATION_NAME_PROPERTY, applicationName);
	}

}
