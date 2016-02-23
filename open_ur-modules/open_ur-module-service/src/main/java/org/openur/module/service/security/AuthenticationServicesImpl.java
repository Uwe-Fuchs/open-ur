package org.openur.module.service.security;

import javax.inject.Inject;

import org.openur.module.domain.security.authentication.IUsernamePasswordToken;
import org.openur.module.domain.security.authentication.IUsernamePasswordTokenBuilder;
import org.openur.module.service.security.realm.rdbms.OpenUrRdbmsRealm;
import org.openur.module.util.exception.AuthenticationException;

public class AuthenticationServicesImpl
	implements IAuthenticationServices
{
	@Inject
	private IUsernamePasswordTokenBuilder usernamePasswordTokenBuilder;
	
	@Inject
	private OpenUrRdbmsRealm rdbmsRealm;
	
	// TODO: inject other realms (and corresponding services) taking e.g. X509-certificates for authenticating.

	@Override
	public void authenticate(String userName, String passWord)
		throws AuthenticationException
	{
		IUsernamePasswordToken usernamePasswordToken = usernamePasswordTokenBuilder
					.userName(userName)
					.passWord(passWord)
					.build();
		
		try
		{
			rdbmsRealm.getAuthenticationInfo(usernamePasswordToken.getDelegate());
		} catch (org.apache.shiro.authc.AuthenticationException e)
		{
			throw new AuthenticationException(e);
		}
	}
}
