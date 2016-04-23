package org.openur.module.integration.security.shiro;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.util.ByteSource;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authentication.UserAccount;
import org.openur.module.domain.security.authentication.UserAccountBuilder;

public class OpenUrRdbmsRealmMock
	extends OpenUrRdbmsRealm
{
	public static final String REALM_NAME = "realmName";
	public static final String ERROR_MSG = "No AuthenticationInfo-instance given!";
	public static final String SALT_BASE = "MyVerySecretPersonalSalt";	
	public static final String USER_NAME_1 = "userName_1";
	public static final String PASSWORD_1 = "password_1";
	public static final String USER_NAME_2 = "userName_2";
	public static final String PASSWORD_2 = "password_2";	
	public static final UserAccount PERSON_1_ACCOUNT;
//	public static final UserAccount TECH_USER_2_ACCOUNT;
	public static final UsernamePwAuthenticationInfo AUTH_INFO;
	public static final UsernamePasswordToken USERNAME_PW_TOKEN;
	public static final UsernamePasswordToken TOKEN_WITH_WRONG_PW;
	public static final UsernamePasswordToken TOKEN_WITH_UNKNOWN_USERNAME;
	
	static
	{
		USERNAME_PW_TOKEN = new UsernamePasswordToken(TestObjectContainer.USER_NAME_1, TestObjectContainer.PASSWORD_1);
		TOKEN_WITH_WRONG_PW = new UsernamePasswordToken(TestObjectContainer.USER_NAME_1, "someWrongPw");
		TOKEN_WITH_UNKNOWN_USERNAME = new UsernamePasswordToken("someUnknownUserName", "somePw");
		AUTH_INFO = new UsernamePwAuthenticationInfo(TestObjectContainer.USER_NAME_1, TestObjectContainer.PASSWORD_1.toCharArray(), REALM_NAME);
		AUTH_INFO.setCredentialsSalt(ByteSource.Util.bytes(TestObjectContainer.SALT_BASE));
		
		PERSON_1_ACCOUNT = new UserAccountBuilder(USER_NAME_1, PASSWORD_1)
				.identifier(TestObjectContainer.PERSON_UUID_1)
				.salt(SALT_BASE)
				.build();
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
