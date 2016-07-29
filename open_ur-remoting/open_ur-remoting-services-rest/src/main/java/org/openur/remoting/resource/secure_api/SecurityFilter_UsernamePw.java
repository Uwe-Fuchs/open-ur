package org.openur.remoting.resource.secure_api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Priority;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.module.integration.security.shiro.UsernamePwAuthenticationInfo;
import org.openur.module.service.security.IAuthorizationServices;
import org.openur.module.util.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This filter verifies the access permissions for a user based on username and
 * password provided in request.
 * 
 * @author info@uwefuchs.com
 * */
@Provider
@Priority(value = Priorities.AUTHENTICATION)
public class SecurityFilter_UsernamePw
	implements ContainerRequestFilter
{
	private static final Logger LOG = LoggerFactory.getLogger(SecurityFilter_UsernamePw.class);

	public static final String APPLICATION_NAME_PROPERTY = "application-name";
	public static final String AUTHENTICATION_PROPERTY = "Authentication";
	public static final String AUTHENTICATION_SCHEME = "Basic";

	@Context
	private ResourceInfo resourceInfo;

	@Inject
	private SecureApiSettings settings = SecureApiSettings.NO_SECURITY;

	@Inject
	private OpenUrRdbmsRealm realm;

	@Inject
	private IAuthorizationServices authorizationServices;

	@Override
	public void filter(ContainerRequestContext requestContext)
	{
		Method method = resourceInfo.getResourceMethod();

		if (settings == SecureApiSettings.NO_SECURITY)
		{
			LOG.debug("Security for REST-API switched off, bypassing security-filter!", method);

			return;
		}

		// Get request headers
		MultivaluedMap<String, String> headers = requestContext.getHeaders();

		// Fetch authentication header
		List<String> authentication = headers.get(AUTHENTICATION_PROPERTY);

		// If security is mandatory but no credentials present -> block access
		if (settings != SecureApiSettings.NO_SECURITY && CollectionUtils.isEmpty(authentication))
		{
			String msg = String.format("Access denied for resource-method: [%s]: No credentials found!", method);
			abortWithUnauthorized(requestContext, msg);

			return;
		}

		// Verify user access:
		UsernamePwAuthenticationInfo info = null;

		try
		{
			info = checkAuthentication(authentication);
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
			hasPermission = checkPermissions(method, applicationName, info.getIdentifier());
		} catch (EntityNotFoundException e)
		{
			LOG.error(e.getMessage());
			String msg = String.format("Bad request for resource-method: [%s]: [%s]", method, e.getMessage());
			abortWithBadRequest(requestContext, msg);

			return;
		}

		if (!hasPermission)
		{
			String msg = String.format("Access denied for resource-method: [%s]! User #%s doesn't have permission!", method, info.getIdentifier());
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

	private UsernamePwAuthenticationInfo checkAuthentication(List<String> authentication)
		throws AuthenticationException
	{
		// Get encoded username and password
		String usernameAndPassword = authentication.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

		// Decode username and password
		if (settings == SecureApiSettings.HASHED_CREDENTIIALS)
		{
			usernameAndPassword = new String(DatatypeConverter.parseBase64Binary(usernameAndPassword));
		}

		// Split username and password tokens
		StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		String username = tokenizer.nextToken();
		String password = tokenizer.nextToken();

		// Verify user access:
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);

		return (UsernamePwAuthenticationInfo) realm.getAuthenticationInfo(token);
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