package org.openur.module.domain;


public interface IPrincipalUser
	extends IIdentifiableEntity
{
	String getUsername();
	
	String getPassword();
}
