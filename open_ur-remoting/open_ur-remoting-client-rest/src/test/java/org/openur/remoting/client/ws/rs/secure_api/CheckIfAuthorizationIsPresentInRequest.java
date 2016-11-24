package org.openur.remoting.client.ws.rs.secure_api;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.openur.remoting.resource.secure_api.AbstractSecurityFilterBase;

public class CheckIfAuthorizationIsPresentInRequest
	implements ContainerRequestFilter
{
	static final String AUTHORIZATION_FOUND_MSG = "Authorization found in request!";

	@Override
	public void filter(ContainerRequestContext requestContext)
		throws IOException
	{
		Response badRequestResponse = Response
					.status(Response.Status.BAD_REQUEST)
					.build();
		
		Response okResponse = Response
					.status(Response.Status.OK)
					.entity(AUTHORIZATION_FOUND_MSG)
					.build();

		String appName = null;
		
		try
		{
			List<String> authorization = requestContext.getHeaders().get(AbstractSecurityFilterBase.APPLICATION_NAME_PROPERTY);
			appName = authorization.get(0);
		} catch (Exception e)
		{
			requestContext.abortWith(badRequestResponse);
		}

		if (StringUtils.isBlank(appName) || !StringUtils.equals(appName, AbstractSecurityClientFilterTest.TEST_APPLICATION_NAME))
		{
			requestContext.abortWith(badRequestResponse);
		}

		requestContext.abortWith(okResponse);
	}
}
