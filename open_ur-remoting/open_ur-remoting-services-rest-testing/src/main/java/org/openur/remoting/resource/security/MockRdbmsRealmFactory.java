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
	public static final UsernamePasswordToken TOKEN_WITH_WRONG_PW = new UsernamePasswordToken(TestObjectContainer.USER_NAME_1, "someWrongPw");
		
	@Override
	public Realm provide()
	{
		final Realm realm = Mockito.mock(Realm.class);
		
		// Mockito.when(authorizationServices.hasPermission(PERSON_ID, OU_ID, PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.TRUE);		
		
		Mockito.doThrow(new AuthenticationException("wrong credentials"))
			.when(realm).getAuthenticationInfo(TOKEN_WITH_WRONG_PW);
		
		return realm;
	}

	@Override
	public void dispose(Realm instance)
	{
	}	
}
