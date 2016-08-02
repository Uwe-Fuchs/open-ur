package org.openur.module.integration.security.shiro;

import org.apache.shiro.util.ByteSource;

/**
 * AuthenticationInfo-Impl which indicates a username for (single) principal and
 * a password for credentials.
 * 
 * @author info@uwefuchs.com
 */
public class UsernamePwAuthenticationInfo
	extends OpenURAuthenticationInfo
{
	private static final long serialVersionUID = -1122447661960862747L;

	public UsernamePwAuthenticationInfo(String userName, char[] passWord, String realmName, String identifier)
	{
		super(userName, passWord, realmName, identifier);
	}

	public UsernamePwAuthenticationInfo(String userName, char[] passWord, ByteSource credentialsSalt, String realmName, String identifier)
	{
		super(userName, passWord, credentialsSalt, realmName, identifier);
	}

	public UsernamePwAuthenticationInfo(String identifier)
	{
		super(identifier);
	}

	public String getUserName()
	{
		return getPrincipals().getPrimaryPrincipal().toString();
	}

	public char[] getPassWord()
	{
		return getCredentials();
	}

	@Override
	public char[] getCredentials()
	{
		return (char[]) super.getCredentials();
	}
}
