package org.openur.remoting.client.ws.rs.secure_api.checkfilters;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import org.apache.commons.lang3.StringUtils;
import org.openur.remoting.client.ws.rs.secure_api.AbstractSecurityClientFilterTest;
import org.openur.remoting.resource.secure_api.AbstractSecurityFilterBase;

public class CheckIfAuthorizationIsPresentInRequest
	extends AbstractCheckIfSecurityIsPresentFilter
{
	public static final String AUTHORIZATION_FOUND_MSG = "Authorization found in request!";

	public CheckIfAuthorizationIsPresentInRequest(ResponseStatus responseStatus)
	{
		super(responseStatus);
		
		setSuccessMessage(AUTHORIZATION_FOUND_MSG);
	}
	
	@Override
	protected void doFilter(ContainerRequestContext requestContext)
		throws IOException
	{
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
	}
}
