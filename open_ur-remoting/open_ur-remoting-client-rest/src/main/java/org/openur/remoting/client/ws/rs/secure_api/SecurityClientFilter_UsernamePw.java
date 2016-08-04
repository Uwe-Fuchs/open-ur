package org.openur.remoting.client.ws.rs.secure_api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.Validate;
import org.openur.remoting.resource.secure_api.AbstractSecurityFilter;

/**
 * This filter adds username and password to a client-request.
 * 
 * @author info@uwefuchs.com
 * */
public class SecurityClientFilter_UsernamePw
	implements ClientRequestFilter
{
	private final String user;
	private final String password;
	private final boolean hashCredentials;

	public SecurityClientFilter_UsernamePw(String user, String password, boolean hashCredentials)
	{
		super();
		
		Validate.notBlank(user, "user-name must not be blank!");
		Validate.notBlank(password, "password must not be blank!");
		
		this.user = user;
		this.password = password;
		this.hashCredentials = hashCredentials;
	}
	
	public SecurityClientFilter_UsernamePw(String user, String password)
	{
		this(user, password, false);
	}

	@Override
	public void filter(ClientRequestContext requestContext)
		throws IOException
	{
		MultivaluedMap<String, Object> headers = requestContext.getHeaders();
		final String basicAuthentication = getBasicAuthentication();
		headers.add(AbstractSecurityFilter.AUTHENTICATION_PROPERTY, basicAuthentication);		
	}

	private String getBasicAuthentication()
	{
		String token = this.user + ":" + this.password;
		
		try
		{
			return hashCredentials ? DatatypeConverter.printBase64Binary(token.getBytes("UTF-8")) : token;
		} catch (UnsupportedEncodingException ex)
		{
			throw new IllegalStateException("Cannot encode with UTF-8", ex);
		}
	}
}
