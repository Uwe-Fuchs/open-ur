package org.openur.module.domain.security;

import org.openur.module.domain.IIdentifiableEntity;


public interface IPrincipalUser
	extends IIdentifiableEntity
{
	String getUsername();
	
	String getPassword();
}
