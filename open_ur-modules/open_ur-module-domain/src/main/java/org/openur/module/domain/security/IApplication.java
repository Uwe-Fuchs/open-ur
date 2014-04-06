package org.openur.module.domain.security;

import org.openur.module.domain.IPrincipalUser;

public interface IApplication
	extends IPrincipalUser, Comparable<IApplication>
{
	/**
	 * the application-literal.
	 * 
	 * @return String
	 */
	String getApplicationName();
}
