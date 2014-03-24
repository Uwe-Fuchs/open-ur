package org.openur.module.domain.security;

import org.openur.module.domain.IPrincipalUser;

public interface IApplication
	extends IPrincipalUser, Comparable<IApplication>
{
	String getApplication();
}
