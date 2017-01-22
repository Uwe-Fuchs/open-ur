package org.openur.remoting.client.ws.rs.secure_api.checkfilters;

import static org.openur.remoting.client.ws.rs.secure_api.checkfilters.CheckIfAuthorizationIsPresentInRequest.AUTHORIZATION_FOUND_MSG;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.apache.commons.lang3.StringUtils;
import org.openur.remoting.client.ws.rs.secure_api.AbstractSecurityClientFilterTest;
import org.openur.remoting.resource.secure_api.AbstractSecurityFilterBase;

public class CheckIfSecurityIsPresentInRequestFilter
	extends CheckIfAuthenticationIsPresentInRequestFilter
	implements ContainerRequestFilter
{	
	public CheckIfSecurityIsPresentInRequestFilter(AbstractCheckIfSecurityIsPresentFilter.ResponseStatus responseStatus)
	{
		super(responseStatus);
		
		setSuccessMessage(AUTHENTICATION_FOUND_MSG + AUTHORIZATION_FOUND_MSG);
	}

	@Override
	protected void doFilter(ContainerRequestContext requestContext)
		throws IOException
	{
		super.doFilter(requestContext);
		
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
