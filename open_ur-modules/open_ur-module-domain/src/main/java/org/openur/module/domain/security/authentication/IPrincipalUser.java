package org.openur.module.domain.security.authentication;

import org.openur.module.domain.IIdentifiableEntity;


public interface IPrincipalUser
	extends IIdentifiableEntity
{
	String getUsername();
	
	String getPassword();
}
