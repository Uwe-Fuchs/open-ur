package org.openur.remoting.resource.secure_api;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.openur.module.integration.security.shiro.UsernamePwAuthenticationInfo;

@Provider
@Priority(value = Priorities.AUTHENTICATION)
public class SecurityFilter_UsernamePw
	extends AbstractSecurityFilter<UsernamePwAuthenticationInfo>
{
	public static final String AUTHENTICATION_PROPERTY = "Authorization";
	public static final String AUTHENTICATION_SCHEME = "Basic";
	
	static final String NO_CREDENTIALS_FOUND_MSG = "No credentials found!";
	static final String NO_VALID_CREDENTIALS_FOUND_MSG = "No valid credentials found!";
	
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
			throw new AuthenticationException(NO_CREDENTIALS_FOUND_MSG);
		}

		// Decode username and password:
		try
		{
			usernameAndPassword = usernameAndPassword.replaceFirst(AUTHENTICATION_SCHEME + " ", "");		
			usernameAndPassword = isHashCredentials() ? new String(DatatypeConverter.parseBase64Binary(usernameAndPassword)) : usernameAndPassword;				
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

		return (UsernamePwAuthenticationInfo) getRealm().getAuthenticationInfo(token);
	}
}
