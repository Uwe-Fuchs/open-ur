package org.openur.module.integration.security.shiro;

import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.util.ByteSource;

/**
 * AuthenticationInfo-Impl which indicates an identifier.
 * 
 * @author info@uwefuchs.com
 */
public class OpenURAuthenticationInfo
	extends SimpleAuthenticationInfo
{
	private static final long serialVersionUID = 9116114660582203066L;
	
	private String identifier;

	public OpenURAuthenticationInfo(Object principal, Object credentials, String realmName, String identifier)
	{
		super(principal, credentials, realmName);

		this.identifier = identifier;
	}

	public OpenURAuthenticationInfo(Object principal, Object credentials, ByteSource credentialsSalt, String realmName, String identifier)
	{
		super(principal, credentials, credentialsSalt, realmName);

		this.identifier = identifier;
	}

	public OpenURAuthenticationInfo(String identifier)
	{
		super();

		this.identifier = identifier;
	}

	public String getIdentifier()
	{
		return identifier;
	}
}
