package org.openur.remoting.resource.secure_api;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import org.openur.module.integration.security.shiro.UsernamePwAuthenticationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Priority(value = Priorities.AUTHENTICATION)
public class AuthenticationFilter_BasicAuth
	extends AbstractSecurityFilter
	implements ContainerRequestFilter
{
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter_BasicAuth.class);
	public static final String AUTHENTICATION_SCHEME = "Basic";
	
	@Context
	private ResourceInfo resourceInfo;
	
	@Inject
	private Boolean hashedCredentials = Boolean.TRUE;

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
		UsernamePwAuthenticationInfo authenticationInfo = null;

		try
		{
			authenticationInfo = doAuthentication(headers);
		} catch (AuthenticationException e)
		{
			String msg = String.format("Access denied for resource-method: [%s], Authentication failed with message: [%s]", method, e.getMessage());
			abortWithUnauthorized(requestContext, msg);

			return;
		}
		
		String userId = authenticationInfo.getIdentifier();
		
		if (StringUtils.isBlank(userId))
		{
			LOG.warn("no User-ID found in Authentication-Info [{}]" + authenticationInfo);
			abortWithBadRequest(requestContext, "invalid credentials!");
		}
		
		requestContext.setProperty(USER_ID_PROPERTY, userId);
	}

	private UsernamePwAuthenticationInfo doAuthentication(MultivaluedMap<String, String> headers)
		throws AuthenticationException
	{
		// Fetch authentication header
		List<String> authentication = headers.get(AUTHENTICATION_PROPERTY);

		// If no credentials present -> block access
		if (CollectionUtils.isEmpty(authentication))
		{
			throw new AuthenticationException(NO_CREDENTIALS_FOUND_MSG);
		}

		// Get username and password
		String usernameAndPassword = authentication.get(0);
		
		if (StringUtils.isBlank(usernameAndPassword))
		{
			throw new AuthenticationException(NO_CREDENTIALS_FOUND_MSG);
		}

		// Decode username and password:
		try
		{
			usernameAndPassword = usernameAndPassword.replaceFirst(AUTHENTICATION_SCHEME + " ", "");		
			usernameAndPassword = hashedCredentials ? new String(DatatypeConverter.parseBase64Binary(usernameAndPassword)) : usernameAndPassword;				
		} catch (IllegalArgumentException e)
		{
			throw new AuthenticationException(e.getMessage());
		}

		// Split username and password tokens
		StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		String username = null;
		String password = null;
		
		try
		{
			username = tokenizer.nextToken();
			password = tokenizer.nextToken();			
		} catch (NoSuchElementException e)
		{
			throw new AuthenticationException(NO_VALID_CREDENTIALS_FOUND_MSG);
		}
		
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password))
		{
			throw new AuthenticationException(NO_VALID_CREDENTIALS_FOUND_MSG);
		}

		// Verify user access:
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);

		return (UsernamePwAuthenticationInfo) realm.getAuthenticationInfo(token);
	}
}
