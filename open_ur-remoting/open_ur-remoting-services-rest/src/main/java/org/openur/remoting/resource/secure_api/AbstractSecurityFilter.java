package org.openur.remoting.resource.secure_api;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.realm.Realm;
import org.openur.module.integration.security.shiro.OpenURAuthenticationInfo;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.module.util.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSecurityFilter<A extends OpenURAuthenticationInfo>
	implements ContainerRequestFilter
{
	private static final Logger LOG = LoggerFactory.getLogger(AbstractSecurityFilter.class);
	public static final String APPLICATION_NAME_PROPERTY = "application-name";
	
	@Context
	private ResourceInfo resourceInfo;
	
	@Inject
	private Boolean hashCredentials = Boolean.FALSE;

	@Inject
	private Realm realm;

	@Inject
	private IAuthorizationServices authorizationServices;

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

		List<String> applicationNameHeaders = headers.get(APPLICATION_NAME_PROPERTY);

		if (CollectionUtils.isEmpty(applicationNameHeaders))
		{
			String msg = String.format("Bad request for resource-method: [%s]: No application-name given!", method);
			abortWithBadRequest(requestContext, msg);

			return;
		}

		String applicationName = applicationNameHeaders.get(0);

		// Permission-check:
		boolean hasPermission = false;

		try
		{
			hasPermission = checkPermissions(method, applicationName, authenticationInfo.getIdentifier());
		} catch (EntityNotFoundException e)
		{
			LOG.error(e.getMessage());
			String msg = String.format("Bad request for resource-method: [%s]: [%s]", method, e.getMessage());
			abortWithBadRequest(requestContext, msg);

			return;
		}

		if (!hasPermission)
		{
			String msg = String.format("Access denied for resource-method: [%s]: User #%s doesn't have permission!", method, authenticationInfo.getIdentifier());
			abortWithUnauthorized(requestContext, msg);

			return;
		}
	}

	private void abortWithBadRequest(ContainerRequestContext requestContext, String msg)
	{
		abortRequest(Response.Status.BAD_REQUEST, requestContext, msg);
	}

	private void abortWithUnauthorized(ContainerRequestContext requestContext, String msg)
	{
		abortRequest(Response.Status.UNAUTHORIZED, requestContext, msg);
	}

	private void abortRequest(Response.Status status, ContainerRequestContext requestContext, String msg)
	{
		LOG.warn(msg);
		Response response = Response.status(status).entity(msg).build();
		requestContext.abortWith(response);
	}

	private boolean checkPermissions(Method method, String applicationName, String userId)
		throws EntityNotFoundException
	{
		RolesAllowed ra = null;
		Annotation[] methodAnnotations = method.getAnnotations();

		for (final Annotation ma : methodAnnotations)
		{
			if (ma.annotationType() == RolesAllowed.class)
			{
				ra = RolesAllowed.class.cast(ma);

				break;
			}
		}

		// if no roles are declared on the method => skip permission-check:
		if (ra == null)
		{
			return true;
		}

		for (String roleAllowed : ra.value())
		{
			if (authorizationServices.hasPermissionTechUser(userId, roleAllowed, applicationName))
			{
				return true;
			}
		}

		return false;
	}

	protected Boolean isHashCredentials()
	{
		return hashCredentials;
	}

	protected Realm getRealm()
	{
		return realm;
	}

	protected abstract A checkAuthentication(MultivaluedMap<String, String> headers)
		throws AuthenticationException;
}
