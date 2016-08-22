package org.openur.remoting.resource.secure_api;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.realm.Realm;
import org.openur.module.integration.security.shiro.OpenURAuthenticationInfo;

public abstract class AbstractAuthenticationFilter<A extends OpenURAuthenticationInfo>
	extends AbstractSecurityFilterBase
	implements ContainerRequestFilter
{
	@Context
	private ResourceInfo resourceInfo;

	@Inject
	private Realm realm;

	@Override
	public void filter(ContainerRequestContext requestContext)
		throws IOException
	{
		Method method = resourceInfo.getResourceMethod();

		// Get request headers
		MultivaluedMap<String, String> headers = requestContext.getHeaders();

		// Verify user access:
		A authenticationInfo = null;

		try
		{
			authenticationInfo = checkAuthentication(headers);
		} catch (AuthenticationException e)
		{
			String msg = String.format("Access denied for resource-method: [%s], Authentication failed with message: [%s]", method, e.getMessage());
			abortWithUnauthorized(requestContext, msg);

			return;
		}
		
		String userId = null;
		
		if (authenticationInfo == null || (userId = authenticationInfo.getIdentifier()) == null)
		{
			abortWithUnauthorized(requestContext, "authenticationInfo-object must not be null!");
		}
		
		requestContext.setProperty(USER_ID_PROPERTY, userId);
	}

	protected Realm getRealm()
	{
		return realm;
	}

	protected abstract A checkAuthentication(MultivaluedMap<String, String> headers)
		throws AuthenticationException;
}
