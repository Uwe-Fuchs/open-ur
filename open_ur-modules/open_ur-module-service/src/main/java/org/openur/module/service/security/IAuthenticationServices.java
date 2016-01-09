package org.openur.module.service.security;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.openur.module.domain.security.authentication.IAuthenticationToken;
import org.openur.module.util.exception.AuthenticationException;

/**
 * all services around authentication.
 * 
 * @author info@uwefuchs.com
 */
public interface IAuthenticationServices
{
	/**
	 * Performs a login attempt with a given {@link IAuthenticationToken}. If
	 * unsuccessful, an {@link AuthenticationException} is thrown.
	 * 
	 * @throws AuthenticationException
	 */
	void authenticate(IAuthenticationToken<? extends HostAuthenticationToken> authenticationToken)
		throws AuthenticationException;
}
