package org.openur.remoting.client.ws.rs.secure_api.checkfilters;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.openur.remoting.client.ws.rs.secure_api.AbstractSecurityClientFilterTest;
import org.openur.remoting.resource.secure_api.AbstractSecurityFilterBase;

public class CheckIfAuthenticationIsPresentInRequestFilter
	extends AbstractCheckIfSecurityIsPresentFilter
{
	public static final String AUTHENTICATION_FOUND_MSG = "Authentication found in request!";

	public CheckIfAuthenticationIsPresentInRequestFilter(ResponseStatus responseStatus)
	{
		super(responseStatus);
		
		setSuccessMessage(AUTHENTICATION_FOUND_MSG);
	}	
	
	protected void doFilter(ContainerRequestContext requestContext)
		throws IOException
	{		
		String user = null;
		String pw = null;
		
		try
		{
			List<String> authentication = requestContext.getHeaders().get(AbstractSecurityFilterBase.AUTHENTICATION_PROPERTY);
			String usernameAndPassword = authentication.get(0);
			usernameAndPassword = new String(DatatypeConverter.parseBase64Binary(usernameAndPassword));
			StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
			user = tokenizer.nextToken();
			pw = tokenizer.nextToken();
		} catch (Exception e)
		{
			requestContext.abortWith(badRequestResponse);
		}

		if (StringUtils.isBlank(user) || StringUtils.isBlank(pw))
		{
			requestContext.abortWith(badRequestResponse);
		}
		
		if (!StringUtils.equals(user, AbstractSecurityClientFilterTest.TEST_USER_NAME) 
				|| !StringUtils.equals(pw, AbstractSecurityClientFilterTest.TEST_PASSWORD))
		{
			requestContext.abortWith(badRequestResponse);
		}
	}
}
