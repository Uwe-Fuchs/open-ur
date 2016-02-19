package org.openur.remoting.resource.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import org.glassfish.hk2.api.Factory;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;

public class MockRdbmsRealmFactory
	implements Factory<Realm>
{
	public static final String REALM_NAME = "realmName";
	public static final UsernamePasswordToken TOKEN_WITH_WRONG_PW = new UsernamePasswordToken(TestObjectContainer.USER_NAME_1, "someWrongPw");
		
	@Override
	public Realm provide()
	{
		final Realm realm = Mockito.mock(Realm.class);

		Mockito.when(realm.getName()).thenReturn(REALM_NAME);
		Mockito.when(realm.supports(Mockito.any(UsernamePasswordToken.class))).thenReturn(Boolean.TRUE);
		Mockito.when(realm.getAuthenticationInfo(TOKEN_WITH_WRONG_PW)).thenThrow(new AuthenticationException("wrong credentials"));
//		Mockito.doThrow(new AuthenticationException("wrong credentials"))
//			.when(realm).getAuthenticationInfo(TOKEN_WITH_WRONG_PW);
		
		return realm;
	}

	@Override
	public void dispose(Realm instance)
	{
	}
}
