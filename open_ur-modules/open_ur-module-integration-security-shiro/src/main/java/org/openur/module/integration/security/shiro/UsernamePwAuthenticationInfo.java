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
	
	private String identifier;

	public UsernamePwAuthenticationInfo(String userName, char[] passWord, String realmName, String identifier)
	{
		super(userName, passWord, realmName);
		
		this.identifier = identifier;
	}

	public UsernamePwAuthenticationInfo(String userName, char[] passWord, ByteSource credentialsSalt, String realmName, String identifier)
	{
		super(userName, passWord, credentialsSalt, realmName);
		
		this.identifier = identifier;
	}

	public UsernamePwAuthenticationInfo(String identifier)
	{
		super();
		
		this.identifier = identifier;
	}

	public String getUserName()
	{
		return getPrincipals().getPrimaryPrincipal().toString();
	}

	public char[] getPassWord()
	{
		return getCredentials();
	}

	public String getIdentifier()
	{
		return identifier;
	}

	@Override
	public char[] getCredentials()
	{
		return (char[]) super.getCredentials();
	}	
}
