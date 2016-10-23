package org.openur.remoting.client.ws.rs.secure_api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.Validate;
import org.openur.remoting.resource.secure_api.AbstractSecurityFilterBase;

/**
 * This filter adds a header containing a basic authentication to a client-request to an Open-UR-REST-Resource.
 * 
 * @author info@uwefuchs.com
 * */
public class AuthenticationClientFilter_BasicAuth
	implements ClientRequestFilter
{
	private final String user;
	private final String password;
	
	/**
	 * initalizing the client-filter, set username and password.
	 * 
	 * @param user
	 * @param password
	 */
	public AuthenticationClientFilter_BasicAuth(String user, String password)
	{
		super();
		
		Validate.notBlank(user, "user-name must not be blank!");
		Validate.notBlank(password, "password must not be blank!");
		
		this.user = user;
		this.password = password;

	}

	@Override
	public void filter(ClientRequestContext requestContext)
		throws IOException
	{
		MultivaluedMap<String, Object> headers = requestContext.getHeaders();
		
		// get basic authentication-string:
		final String basicAuthentication = getBasicAuthentication();	
		
		// add authentication to request-headers:
		headers.add(AbstractSecurityFilterBase.AUTHENTICATION_PROPERTY, basicAuthentication);
	}

	/**
	 * build basic authentication containing username and password.
	 * 
	 * @return String containing basic authentication for adding to request-headers.
	 */
	private String getBasicAuthentication()
	{
		String token = this.user + ":" + this.password;
		
		try
		{
			return DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException ex)
		{
			throw new IllegalStateException("Cannot encode with UTF-8", ex);
		}
	}
}
