package org.openur.remoting.client.ws.rs.secure_api;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.openur.remoting.resource.secure_api.AbstractSecurityFilterBase;

public class CheckIfAuthenticationIsPresentInRequestFilter
	implements ContainerRequestFilter
{
	static final String AUTHENTICATION_FOUND_MSG = "Authentication found in request!";
	
	protected final Response badRequestResponse = Response
			.status(Response.Status.BAD_REQUEST)
			.build();
	
	private final Response okResponse = Response
			.status(Response.Status.OK)
			.entity(AUTHENTICATION_FOUND_MSG)
			.build();

	@Override
	public void filter(ContainerRequestContext requestContext)
		throws IOException
	{
		doFilter(requestContext);
		requestContext.abortWith(okResponse);
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
