package org.openur.remoting.resource.secure_api;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSecurityFilter
{
	private static final Logger LOG = LoggerFactory.getLogger(AbstractSecurityFilter.class);
	
	public static final String SECURE_API_SETTINGS = "secureApiSettings";
	public static final String APPLICATION_NAME_PROPERTY = "application-name";
	public static final String AUTHENTICATION_PROPERTY = "Authorization";	
	public static final String USER_ID_PROPERTY = "userId";
	public static final String NO_CREDENTIALS_FOUND_MSG = "No credentials found!";
	public static final String NO_VALID_CREDENTIALS_FOUND_MSG = "No valid credentials found!";

	protected void abortWithBadRequest(ContainerRequestContext requestContext, String msg)
	{
		abortRequest(Response.Status.BAD_REQUEST, requestContext, msg);
	}

	protected void abortWithUnauthorized(ContainerRequestContext requestContext, String msg)
	{
		abortRequest(Response.Status.UNAUTHORIZED, requestContext, msg);
	}

	private void abortRequest(Response.Status status, ContainerRequestContext requestContext, String msg)
	{
		LOG.warn(msg);
		Response response = Response.status(status).entity(msg).build();
		requestContext.abortWith(response);
	}
}
