package org.openur.remoting.resource.secure_api;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.apache.commons.lang3.StringUtils;

@Priority(value = Priorities.AUTHORIZATION - 10)
public class AuthenticationResultCheckFilter
	extends AbstractSecurityFilterBase
	implements ContainerRequestFilter
{
	@Override
	public void filter(ContainerRequestContext requestContext)
		throws IOException
	{
		String userId = (String) requestContext.getProperty(USER_ID_PROPERTY);

		if (StringUtils.isBlank(userId))
		{
			abortWithUnauthorized(requestContext, NOT_AUTHENTICATED_MSG);

			return;
		}
	}
}
