package org.openur.remoting.client.ws.rs.secure_api;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

public class CheckIfSecurityIsPresentInRequestFilter
	extends CheckIfAuthenticationIsPresentInRequestFilter
	implements ContainerRequestFilter
{	
	@SuppressWarnings("unused")
	private final Response okResponse = Response
			.status(Response.Status.OK)
			.entity(AUTHENTICATION_FOUND_MSG + CheckIfAuthorizationIsPresentInRequest.AUTHORIZATION_FOUND_MSG)
			.build();

	@Override
	public void filter(ContainerRequestContext requestContext)
		throws IOException
	{
		doFilter(requestContext);
		requestContext.abortWith(okResponse);
	}
}
