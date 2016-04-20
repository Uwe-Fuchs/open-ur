package org.openur.remoting.resource.security;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.util.ByteSource;
import org.glassfish.hk2.api.Factory;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.integration.security.shiro.OpenUrRdbmsRealm;
import org.openur.module.integration.security.shiro.UsernamePwAuthenticationInfo;

public class MockRdbmsRealmFactory
	implements Factory<OpenUrRdbmsRealm>
{
	@Override
	public OpenUrRdbmsRealm provide()
	{
		return new OpenUrRdbmsRealmMock();
	}

	@Override
	public void dispose(OpenUrRdbmsRealm instance)
	{
	}
	
	public static class OpenUrRdbmsRealmMock
		extends OpenUrRdbmsRealm
	{
		public static final String REALM_NAME = "realmName";
		public static final UsernamePwAuthenticationInfo AUTH_INFO;
		public static final UsernamePasswordToken USERNAME_PW_TOKEN;
		public static final UsernamePasswordToken TOKEN_WITH_WRONG_PW;
		public static final UsernamePasswordToken TOKEN_WITH_UNKNOWN_USERNAME;
		public static final String ERROR_MSG = "No AuthenticationInfo-instance given!";
		
		static
		{
			USERNAME_PW_TOKEN = new UsernamePasswordToken(TestObjectContainer.USER_NAME_1, TestObjectContainer.PASSWORD_1);
			TOKEN_WITH_WRONG_PW = new UsernamePasswordToken(TestObjectContainer.USER_NAME_1, "someWrongPw");
			TOKEN_WITH_UNKNOWN_USERNAME = new UsernamePasswordToken("someUnknownUserName", "somePw");
			AUTH_INFO = new UsernamePwAuthenticationInfo(TestObjectContainer.USER_NAME_1, TestObjectContainer.PASSWORD_1.toCharArray(), REALM_NAME);
			AUTH_INFO.setCredentialsSalt(ByteSource.Util.bytes(TestObjectContainer.SALT_BASE));
		}
	
		@Override
		protected UsernamePwAuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException
		{
			if (EqualsBuilder.reflectionEquals(USERNAME_PW_TOKEN, token))
			{
				return AUTH_INFO;
			}
	
			throw new AuthenticationException(ERROR_MSG);
		}
	
		@Override
		public boolean supports(AuthenticationToken token)
		{
			return EqualsBuilder.reflectionEquals(USERNAME_PW_TOKEN, token);
		}
	
		@Override
		public String getName()
		{
			return REALM_NAME;
		}
	}
}
