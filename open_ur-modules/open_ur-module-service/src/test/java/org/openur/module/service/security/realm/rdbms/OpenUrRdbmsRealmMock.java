package org.openur.module.service.security.realm.rdbms;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.util.ByteSource;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.service.security.realm.rdbms.OpenUrRdbmsRealm;

public class OpenUrRdbmsRealmMock
	extends OpenUrRdbmsRealm
{
	public static final String REALM_NAME = "realmName";
	public static final String SALT_VALUE = "myVerySecretSalt";
	public static final SimpleAuthenticationInfo AUTH_INFO;
	
	static
	{
		AUTH_INFO = new SimpleAuthenticationInfo(TestObjectContainer.USER_NAME_1, TestObjectContainer.PASSWORD_1.toCharArray(), REALM_NAME);
		AUTH_INFO.setCredentialsSalt(ByteSource.Util.bytes(SALT_VALUE));
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
		throws AuthenticationException
	{
		if (areTokensEqual(TestObjectContainer.USERNAME_PW_TOKEN.getDelegate(), (UsernamePasswordToken) token))
		{
			return AUTH_INFO;
		}

		throw new AuthenticationException();
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
