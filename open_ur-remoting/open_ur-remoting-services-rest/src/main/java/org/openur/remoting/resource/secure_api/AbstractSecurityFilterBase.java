package org.openur.remoting.resource.secure_api;

import java.security.Principal;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSecurityFilterBase
{
	private static final Logger LOG = LoggerFactory.getLogger(AbstractSecurityFilterBase.class);
	
	protected static final String NO_CREDENTIALS_FOUND_MSG = "No credentials found!";
	protected static final String NO_VALID_CREDENTIALS_FOUND_MSG = "No valid credentials found!";
	protected static final String NOT_AUTHENTICATED_MSG = "Not authenticated!";
	
	public static final String APPLICATION_NAME_PROPERTY = "application-name";
	public static final String AUTHENTICATION_PROPERTY = "Authorization";
	public static final String SECURE_API_SETTINGS = "secureApiSettings";
	public static final String USER_ID_PROPERTY = "requesterId";
	
	protected void addUserPrincipalToSecurityContext(ContainerRequestContext requestContext, String userId)
	{
		Principal principal = new OpenUrPrincipal(userId);
		SecurityContext securityContext = new OpenUrSecurityContext(principal);
		requestContext.setSecurityContext(securityContext);
	}
	
	protected String getUserPrincipalFromSecurityContext(ContainerRequestContext requestContext)
	{
		SecurityContext securityContext = requestContext.getSecurityContext();
		
		if (securityContext == null)
		{
			return null;
		}
		
		Principal principal = securityContext.getUserPrincipal();
		
		if (principal == null || StringUtils.isEmpty(principal.getName()))
		{			
			return null;
		}
		
		return principal.getName();
	}

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