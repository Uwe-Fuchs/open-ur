package org.openur.remoting.resource.security;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.util.ByteSource;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.module.integration.security.shiro.UsernamePwAuthenticationInfo;

public class OpenUrRdbmsRealmMock
	extends OpenUrRdbmsRealm
{
	public static final String REALM_NAME = "realmName";
	public static final UsernamePwAuthenticationInfo AUTH_INFO;
	public static final UsernamePasswordToken USERNAME_PW_TOKEN;
	public static final String ERROR_MSG = "No AuthenticationInfo-instance given!";
	
	static
	{
		USERNAME_PW_TOKEN = new UsernamePasswordToken(TestObjectContainer.USER_NAME_1, TestObjectContainer.PASSWORD_1);
		AUTH_INFO = new UsernamePwAuthenticationInfo(TestObjectContainer.USER_NAME_1, TestObjectContainer.PASSWORD_1.toCharArray(), REALM_NAME);
		AUTH_INFO.setCredentialsSalt(ByteSource.Util.bytes(TestObjectContainer.SALT_BASE));
	}

	@Override
	protected UsernamePwAuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
		throws AuthenticationException
	{
		if (areTokensEqual(USERNAME_PW_TOKEN, (UsernamePasswordToken) token))
		{
			return AUTH_INFO;
		}

		throw new AuthenticationException(ERROR_MSG);
	}

	@Override
	public boolean supports(AuthenticationToken token)
	{
		return areTokensEqual(USERNAME_PW_TOKEN, (UsernamePasswordToken) token);
	}

	@Override
	public String getName()
	{
		return REALM_NAME;
	}
	
	private boolean areTokensEqual(UsernamePasswordToken thisToken, UsernamePasswordToken otherToken)
	{
		if (!thisToken.getUsername().equals(otherToken.getUsername()))
		{
			return false;
		}
		
		if (!ArrayUtils.toString(thisToken.getPassword()).equals(ArrayUtils.toString(otherToken.getPassword(), "")))
		{
			return false;
		}
		
		return true;
	}		
}
