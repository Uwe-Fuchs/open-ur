package org.openur.remoting.resource.secure_api;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Priority;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.module.util.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Priority(value = Priorities.AUTHORIZATION)
public class AuthorizationFilter
	extends AbstractSecurityFilterBase
	implements ContainerRequestFilter
{
	private static final Logger LOG = LoggerFactory.getLogger(AuthorizationFilter.class);
	
	@Context
	private ResourceInfo resourceInfo;

	@Inject
	private IAuthorizationServices authorizationServices;

	@Override
	public void filter(ContainerRequestContext requestContext)
		throws IOException
	{
		String userId = (String) requestContext.getProperty(USER_ID_PROPERTY);
		requestContext.removeProperty(USER_ID_PROPERTY);

		if (StringUtils.isBlank(userId))
		{
			abortWithBadRequest(requestContext, "No User-ID given in context!");
		}

		Method method = resourceInfo.getResourceMethod();

		// Get request headers
		MultivaluedMap<String, String> headers = requestContext.getHeaders();

		// Verify user access:
		List<String> applicationNameHeaders = headers.get(AbstractSecurityFilterBase.APPLICATION_NAME_PROPERTY);

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
			hasPermission = checkPermissions(method, applicationName, userId);
		} catch (EntityNotFoundException e)
		{
			LOG.error(e.getMessage());
			String msg = String.format("Bad request for resource-method: [%s]: [%s]", method, e.getMessage());
			abortWithBadRequest(requestContext, msg);

			return;
		}

		if (!hasPermission)
		{
			String msg = String.format("Access denied for resource-method: [%s]: User #%s doesn't have permission!", method, userId);
			abortWithUnauthorized(requestContext, msg);

			return;
		}
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
}
