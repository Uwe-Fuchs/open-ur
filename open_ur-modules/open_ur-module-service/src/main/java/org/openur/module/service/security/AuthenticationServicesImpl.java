package org.openur.module.service.security;

import javax.inject.Inject;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.module.util.exception.AuthenticationException;

public class AuthenticationServicesImpl
	implements IAuthenticationServices
{
	@Inject
	private OpenUrRdbmsRealm rdbmsRealm;
	
	// TODO: inject other realms (and corresponding services) taking e.g. X509-certificates for authenticating.

	@Override
	public void authenticate(String userName, String passWord)
		throws AuthenticationException
	{
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, passWord);
		
		try
		{
			rdbmsRealm.getAuthenticationInfo(usernamePasswordToken);
		} catch (org.apache.shiro.authc.AuthenticationException e)
		{
			throw new AuthenticationException(e);
		}
	}
}
