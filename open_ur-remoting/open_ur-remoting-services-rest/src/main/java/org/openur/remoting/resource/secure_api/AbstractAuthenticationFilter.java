package org.openur.remoting.resource.secure_api;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.realm.Realm;
import org.openur.module.integration.security.shiro.OpenURAuthenticationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAuthenticationFilter<A extends OpenURAuthenticationInfo>
	extends AbstractSecurityFilterBase
	implements ContainerRequestFilter
{
	private static final Logger LOG = LoggerFactory.getLogger(AbstractAuthenticationFilter.class);
	
	@Context
	private ResourceInfo resourceInfo;

	@Inject
	private Realm realm;

	@Override
	public void filter(ContainerRequestContext requestContext)
		throws IOException
	{
		// try to get user-id from context:
		String userId = (String) requestContext.getProperty(USER_ID_PROPERTY);
		
		// if user-id already in context => authentication already performed, leave:
		if (StringUtils.isNotEmpty(userId))
		{
			LOG.debug("User with ID [{}] already authenticated!", userId);
			
			return;
		}

		// Get request headers
		MultivaluedMap<String, String> headers = requestContext.getHeaders();

		// Verify user access:
		A authenticationInfo = null;

		try
		{
			authenticationInfo = checkAuthentication(headers);
		} catch (AuthenticationException e)
		{
			LOG.info("Authentication failed with message: [{}]!", e.getMessage());

			return;
		}
		
		userId = null;
		
		if (authenticationInfo == null || (userId = authenticationInfo.getIdentifier()) == null)
		{
			abortWithBadRequest(requestContext, "Authentication Service returned empty or invalid authetication-object!");
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
