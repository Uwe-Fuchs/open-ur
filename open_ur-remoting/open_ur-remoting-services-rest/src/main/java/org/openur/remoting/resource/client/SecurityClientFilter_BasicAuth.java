package org.openur.remoting.resource.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.openur.remoting.resource.secure_api.AbstractSecurityFilterBase;

/**
 * This filter adds username and password to a client-request.
 * 
 * @author info@uwefuchs.com
 * */
public class SecurityClientFilter_BasicAuth
	implements ClientRequestFilter
{
	private final String user;
	private final String password;
	private final String applicationName;
	
	public SecurityClientFilter_BasicAuth(String user, String password)
	{
		this(user, password, "");
	}

	public SecurityClientFilter_BasicAuth(String user, String password, String applicationName)
	{
		super();
		
		Validate.notBlank(user, "user-name must not be blank!");
		Validate.notBlank(password, "password must not be blank!");
		Validate.notNull(applicationName, "application-name must not be null!");
		
		this.user = user;
		this.password = password;
		this.applicationName = applicationName;
	}

	@Override
	public void filter(ClientRequestContext requestContext)
		throws IOException
	{
		MultivaluedMap<String, Object> headers = requestContext.getHeaders();
		final String basicAuthentication = getBasicAuthentication();
		headers.add(AbstractSecurityFilterBase.AUTHENTICATION_PROPERTY, basicAuthentication);	
		
		if (StringUtils.isNotEmpty(applicationName))
		{
			headers.add(AbstractSecurityFilterBase.APPLICATION_NAME_PROPERTY, applicationName);
		}
	}

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
