package org.openur.remoting.resource.secure_api;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSecurityFilterBase
{
	protected static final Logger LOG = LoggerFactory.getLogger(AbstractSecurityFilterBase.class);
	protected static final String USER_ID_PROPERTY = "userId";
	protected static final String NO_CREDENTIALS_FOUND_MSG = "No credentials found!";
	protected static final String NO_VALID_CREDENTIALS_FOUND_MSG = "No valid credentials found!";
	public static final String APPLICATION_NAME_PROPERTY = "application-name";
	public static final String AUTHENTICATION_PROPERTY = "Authorization";
	public static final String SECURE_API_SETTINGS = "secureApiSettings";

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