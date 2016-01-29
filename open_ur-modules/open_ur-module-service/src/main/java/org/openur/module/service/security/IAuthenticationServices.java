package org.openur.module.service.security;

import org.openur.module.util.exception.AuthenticationException;

/**
 * all services around authentication.
 * 
 * @author info@uwefuchs.com
 */
public interface IAuthenticationServices
{
	/**
	 * Performs a login attempt via given userName/passWord-combination. If
	 * unsuccessful, an {@link AuthenticationException} is thrown.
	 * 
	 * @param userName
	 * @param passWord
	 * 
	 * @throws {@link AuthenticationException}
	 */
	void authenticate(String userName, String passWord)
		throws AuthenticationException;
}
