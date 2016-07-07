package org.openur.remoting.client.ws.rs.security;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.openur.module.service.security.IAuthenticationServices;
import org.openur.module.util.exception.AuthenticationException;

public class AuthenticationResourceClient
	implements IAuthenticationServices
{
	private String baseUrl;
	private RdbmsRealmResourceClient realmClient;
	
	// TODO: inject other realms (and corresponding services) taking e.g. X509-certificates for authenticating.

	@Inject
	public AuthenticationResourceClient(String baseUrl)
	{
		Validate.notBlank(baseUrl, "Base-URL must not be blank!");
		
		this.baseUrl = baseUrl;
		this.realmClient = new RdbmsRealmResourceClient(this.baseUrl);
	}

	// package-scope for testing-purposes:
	void setRealmClient(RdbmsRealmResourceClient realmClient)
	{
		this.realmClient = realmClient;
	}

	@Override
	public void authenticate(String userName, String passWord)
		throws AuthenticationException
	{
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, passWord);
		
		try
		{
			this.realmClient.getAuthenticationInfo(usernamePasswordToken);
		} catch (org.apache.shiro.authc.AuthenticationException e)
		{
			throw new AuthenticationException(e);
		}
	}
}
