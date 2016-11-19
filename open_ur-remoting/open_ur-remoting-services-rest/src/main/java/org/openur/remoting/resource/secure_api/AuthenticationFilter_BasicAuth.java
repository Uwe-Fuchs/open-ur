package org.openur.remoting.resource.secure_api;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.openur.module.integration.security.shiro.UsernamePwAuthenticationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Priority(value = Priorities.AUTHENTICATION + 10)
public class AuthenticationFilter_BasicAuth
	extends AbstractAuthenticationFilter<UsernamePwAuthenticationInfo>
{
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter_BasicAuth.class);
	public static final String AUTHENTICATION_SCHEME = "Basic";
	
	@Inject
	private Boolean hashCredentials = Boolean.TRUE;

	@Override
	protected UsernamePwAuthenticationInfo checkAuthentication(MultivaluedMap<String, String> headers)
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
			throw new AuthenticationException(NO_VALID_CREDENTIALS_FOUND_MSG);
		}
		
		usernameAndPassword = usernameAndPassword.replaceFirst(AUTHENTICATION_SCHEME + " ", "");

		// Decode username and password (if encoded):
		if (hashCredentials)
		{
			try
			{
				usernameAndPassword = new String(DatatypeConverter.parseBase64Binary(usernameAndPassword));
			} catch (IllegalArgumentException e)
			{
				throw new AuthenticationException(e.getMessage());
			}
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
		try
		{
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			UsernamePwAuthenticationInfo info =  (UsernamePwAuthenticationInfo) getRealm().getAuthenticationInfo(token);
			LOG.debug("Basic authentication successful for user [{}].", info.getUserName());
			
			return info;
		} catch (AuthenticationException e)
		{
			LOG.info("Basic authentication failed with message: [{}]!", e.getMessage());
			
			throw e;
		}
	}
}
