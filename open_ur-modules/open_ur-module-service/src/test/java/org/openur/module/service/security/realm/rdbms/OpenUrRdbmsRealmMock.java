package org.openur.module.service.security.realm.rdbms;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;

public class OpenUrRdbmsRealmMock
	extends OpenUrRdbmsRealm
{
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
		throws AuthenticationException
	{
		if (!areTokensEqual(TestObjectContainer.USERNAME_PW_TOKEN.getDelegate(), (UsernamePasswordToken) token))
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
