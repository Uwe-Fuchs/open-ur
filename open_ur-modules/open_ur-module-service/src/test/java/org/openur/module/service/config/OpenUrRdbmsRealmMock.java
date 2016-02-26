package org.openur.module.service.config;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;

public class OpenUrRdbmsRealmMock
	extends OpenUrRdbmsRealm
{
	public static final UsernamePasswordToken USERNAME_PW_TOKEN = new UsernamePasswordToken(TestObjectContainer.USER_NAME_1, TestObjectContainer.PASSWORD_1);
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
		throws AuthenticationException
	{
		if (!areTokensEqual(USERNAME_PW_TOKEN, (UsernamePasswordToken) token))
		{
			throw new AuthenticationException();
		}

		return null;
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
