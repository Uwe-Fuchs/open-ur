package org.openur.module.integration.security.shiro;

import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.util.ByteSource;

/**
 * AuthenticationInfo-Impl which indicates a username for (single) principal and a password for credentials.
 * 
 * @author info@uwefuchs.com
 */
public class UsernamePwAuthenticationInfo
	extends SimpleAuthenticationInfo
{
	private static final long serialVersionUID = 524455112641625935L;

	public UsernamePwAuthenticationInfo(String userName, char[] passWord, String realmName)
	{
		super(userName, passWord, realmName);
	}

	public UsernamePwAuthenticationInfo(String userName, char[] passWord, ByteSource credentialsSalt, String realmName)
	{
		super(userName, passWord, credentialsSalt, realmName);
	}

	public UsernamePwAuthenticationInfo()
	{
		super();
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
