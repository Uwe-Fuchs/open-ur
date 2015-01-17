package org.openur.module.service.security;

import org.openur.module.domain.security.authentication.IAuthenticationToken;
import org.openur.module.domain.security.authentication.IPrincipalUser;

public interface IAuthenticationServices<T>
{
	/**
	 * authenticates a user based on the given token.
	 * 
	 * @param authenticationToken
	 * 
	 * @return the user-object if authentication was successful, null otherwise.
	 */ 
	IPrincipalUser<T> authenticate(IAuthenticationToken<T> authenticationToken);	
}
