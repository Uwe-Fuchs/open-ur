package org.openur.module.domain.application;

import org.openur.module.domain.security.authentication.IPrincipalUser;


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
