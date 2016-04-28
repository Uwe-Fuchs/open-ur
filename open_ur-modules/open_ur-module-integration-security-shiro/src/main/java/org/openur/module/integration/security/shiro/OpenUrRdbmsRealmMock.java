package org.openur.module.integration.security.shiro;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.util.ByteSource;
import org.openur.module.domain.security.authentication.IUserAccount;
import org.openur.module.util.exception.OpenURRuntimeException;

/**
 * Mock-Object for {@link OpenUrRdbmsRealm}, for the {@link OpenUrRdbmsRealm#getAuthenticationInfo()}-Method is final and hence cannot be mocked via Mockito.
 * The Mock is put here so it can be used for testing in every module below the integration-module (and of course within the integration-module itself).
 * 
 * @author info@uwefuchs.com
 */
public class OpenUrRdbmsRealmMock
	extends OpenUrRdbmsRealm
{
	public static final String REALM_NAME = "realmName";
	public static final String AUTH_ERROR_MSG = "No AuthenticationInfo-instance given!";
	public static final String RUNTIME_ERROR_MSG = "Common OpenURRuntimeException!";
	public static final String SALT_BASE = "MyVerySecretPersonalSalt";	
	public static final String PERSON_UUID_1 = UUID.randomUUID().toString();
	public static final String USER_NAME_1 = "userName_1";
	public static final String PASSWORD_1 = "password_1";
	public static final MyUserAccount PERSON_1_ACCOUNT;
	public static final String TECH_USER_UUID_2 = UUID.randomUUID().toString();
	public static final String USER_NAME_2 = "userName_2";
	public static final String PASSWORD_2 = "password_2";	
	public static final MyUserAccount TECH_USER_2_ACCOUNT;
	public static final UsernamePwAuthenticationInfo AUTH_INFO;
	public static final UsernamePasswordToken USERNAME_PW_TOKEN;
	public static final UsernamePasswordToken TOKEN_WITH_WRONG_PW;
	public static final UsernamePasswordToken TOKEN_WITH_UNKNOWN_USERNAME;
	public static final UsernamePasswordToken TOKEN_CAUSING_RUNTIME_EXCEPTION;
	
	static
	{
		USERNAME_PW_TOKEN = new UsernamePasswordToken(USER_NAME_1, PASSWORD_1);
		TOKEN_WITH_WRONG_PW = new UsernamePasswordToken(USER_NAME_1, "someWrongPw");
		TOKEN_WITH_UNKNOWN_USERNAME = new UsernamePasswordToken("someUnknownUserName", "somePw");
		TOKEN_CAUSING_RUNTIME_EXCEPTION = new UsernamePasswordToken("userNameCausingRuntimeException", "somePw");
		AUTH_INFO = new UsernamePwAuthenticationInfo(USER_NAME_1, PASSWORD_1.toCharArray(), REALM_NAME);
		AUTH_INFO.setCredentialsSalt(ByteSource.Util.bytes(SALT_BASE));
		
		PERSON_1_ACCOUNT = new MyUserAccount();
		PERSON_1_ACCOUNT.setUserName(USER_NAME_1);
		PERSON_1_ACCOUNT.setPassWord(PASSWORD_1);
		PERSON_1_ACCOUNT.setSalt(SALT_BASE);
		
		TECH_USER_2_ACCOUNT = new MyUserAccount();
		TECH_USER_2_ACCOUNT.setUserName(USER_NAME_2);
		TECH_USER_2_ACCOUNT.setPassWord(PASSWORD_2);
		TECH_USER_2_ACCOUNT.setSalt(SALT_BASE);
	}

	@Override
	protected UsernamePwAuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
		throws AuthenticationException
	{
		if (EqualsBuilder.reflectionEquals(USERNAME_PW_TOKEN, token))
		{
			return AUTH_INFO;
		} else if (EqualsBuilder.reflectionEquals(TOKEN_CAUSING_RUNTIME_EXCEPTION, token))
		{
			throw new OpenURRuntimeException(RUNTIME_ERROR_MSG);
		}

		throw new AuthenticationException(AUTH_ERROR_MSG);
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
	
	public static class MyUserAccount
		implements IUserAccount, Serializable
	{
		private static final long serialVersionUID = 7165821312250746670L;
		
		// properties:
		private String id = UUID.randomUUID().toString();
		private String userName;
		private String passWord;
		private String salt;

		@Override
		public String getIdentifier()
		{
			return id;
		}

		@Override
		public String getUserName()
		{
			return userName;
		}

		public void setUserName(String userName)
		{
			this.userName = userName;
		}

		@Override
		public String getPassWord()
		{
			return passWord;
		}

		public void setPassWord(String passWord)
		{
			this.passWord = passWord;
		}

		@Override
		public String getSalt()
		{
			return salt;
		}

		public void setSalt(String salt)
		{
			this.salt = salt;
		}

		@Override
		public LocalDateTime getLastModifiedDate()
		{
			return null;
		}

		@Override
		public LocalDateTime getCreationDate()
		{
			return null;
		}
	}
}
