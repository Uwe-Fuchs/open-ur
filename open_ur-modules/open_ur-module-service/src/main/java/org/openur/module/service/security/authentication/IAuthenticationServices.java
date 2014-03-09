package org.openur.module.service.security.authentication;

import org.openur.module.domain.IPrincipalUser;

public interface IAuthenticationServices	
{
	IPrincipalUser getAuthenticationInfo(String userName);
}
