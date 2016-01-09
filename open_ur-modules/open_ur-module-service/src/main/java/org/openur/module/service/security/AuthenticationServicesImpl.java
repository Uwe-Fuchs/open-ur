package org.openur.module.service.security;

import javax.inject.Inject;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.realm.Realm;
import org.openur.module.domain.security.authentication.IAuthenticationToken;
import org.openur.module.util.exception.AuthenticationException;

public class AuthenticationServicesImpl
	implements IAuthenticationServices
{
	@Inject
	private Realm realm;

	@Override
	public void authenticate(IAuthenticationToken<? extends HostAuthenticationToken> authenticationToken)
		throws AuthenticationException
	{
		try
		{
			realm.getAuthenticationInfo(authenticationToken.getDelegate());
		} catch (org.apache.shiro.authc.AuthenticationException e)
		{
			throw new AuthenticationException(e);
		}
	}
}
