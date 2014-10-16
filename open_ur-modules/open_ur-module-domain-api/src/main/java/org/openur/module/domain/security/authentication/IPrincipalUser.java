package org.openur.module.domain.security.authentication;

import org.openur.module.domain.IIdentifiableEntity;


public interface IPrincipalUser<T>
	extends IIdentifiableEntity
{
	IAuthenticationToken<T> getAuthenticationToken();
}
