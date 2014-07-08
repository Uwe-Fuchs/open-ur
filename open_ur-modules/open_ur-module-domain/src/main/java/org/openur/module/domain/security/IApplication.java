package org.openur.module.domain.security;


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
