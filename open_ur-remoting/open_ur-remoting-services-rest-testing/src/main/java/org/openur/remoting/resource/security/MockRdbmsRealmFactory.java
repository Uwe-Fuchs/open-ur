package org.openur.remoting.resource.security;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.ByteSource;
import org.glassfish.hk2.api.Factory;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;

public class MockRdbmsRealmFactory
	implements Factory<Realm>
{
	public static final String REALM_NAME = "realmName";
	public static final String SALT_VALUE = "myVerySecretSalt";
	public static final UsernamePasswordToken TOKEN_WITH_UNKNOWN_USERNAME = new UsernamePasswordToken("someUnknownUserName", "somePw");
	public static final UsernamePasswordToken TOKEN_WITH_WRONG_PW = new UsernamePasswordToken(TestObjectContainer.USER_NAME_1, "someWrongPw");
	public static final SimpleAuthenticationInfo AUTH_INFO;
	
	static
	{
		AUTH_INFO = new SimpleAuthenticationInfo(TestObjectContainer.USER_NAME_1, TestObjectContainer.PASSWORD_1.toCharArray(), REALM_NAME);
		AUTH_INFO.setCredentialsSalt(ByteSource.Util.bytes(SALT_VALUE));
	}
	
	@Override
	public Realm provide()
	{
		final Realm realm = Mockito.mock(Realm.class);

		Mockito.when(realm.getName()).thenReturn(REALM_NAME);
		Mockito.when(realm.supports(Mockito.argThat(new TokenEqualToUsernamePwToken()))).thenReturn(Boolean.TRUE);
		Mockito.when(realm.getAuthenticationInfo(Mockito.argThat(new TokenEqualToUsernamePwToken()))).thenReturn(AUTH_INFO);
		Mockito.doThrow(new AuthenticationException())
			.when(realm).getAuthenticationInfo(Mockito.argThat(new TokenEqualToTokenWithWrongPw()));
		
		return realm;
	}

	@Override
	public void dispose(Realm instance)
	{
	}
	
	private class TokenEqualToUsernamePwToken extends ArgumentMatcher<UsernamePasswordToken>
	{
		@Override
		public boolean matches(Object argument)
		{
			UsernamePasswordToken thisToken = TestObjectContainer.USERNAME_PW_TOKEN.getDelegate();
			UsernamePasswordToken otherToken = (UsernamePasswordToken) argument;
			
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
	
	private class TokenEqualToTokenWithWrongPw extends ArgumentMatcher<UsernamePasswordToken>
	{
		@Override
		public boolean matches(Object argument)
		{
			UsernamePasswordToken otherToken = (UsernamePasswordToken) argument;
			
			if (!TOKEN_WITH_WRONG_PW.getUsername().equals(otherToken.getUsername()))
			{
				return false;
			}
			
			if (!ArrayUtils.toString(TOKEN_WITH_WRONG_PW.getPassword()).equals(ArrayUtils.toString(otherToken.getPassword(), "")))
			{
				return false;
			}
			
			return true;
		}		
	}
}
