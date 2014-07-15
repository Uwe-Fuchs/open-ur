package org.openur.module.service.security.authentication;

import org.openur.module.domain.security.authentication.IAuthenticationToken;
import org.openur.module.domain.security.authentication.IPrincipalUser;

public interface IAuthenticationServices
{
	/**
	 * authenticates a user based on the given token.
	 * 
	 * @param authenticationToken
	 * 
	 * @return the user-object if authentication was successful, null otherwise.
	 */ 
	IPrincipalUser authenticate(IAuthenticationToken authenticationToken);	
}
