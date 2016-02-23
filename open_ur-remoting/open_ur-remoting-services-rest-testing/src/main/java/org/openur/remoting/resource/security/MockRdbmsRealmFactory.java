package org.openur.remoting.resource.security;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.glassfish.hk2.api.Factory;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.service.security.realm.rdbms.OpenUrRdbmsRealm;
import org.openur.module.service.security.realm.rdbms.mock.OpenUrRdbmsRealmMock;

public class MockRdbmsRealmFactory
	implements Factory<OpenUrRdbmsRealm>
{
	public static final UsernamePasswordToken TOKEN_WITH_UNKNOWN_USERNAME = new UsernamePasswordToken("someUnknownUserName", "somePw");
	public static final UsernamePasswordToken TOKEN_WITH_WRONG_PW = new UsernamePasswordToken(TestObjectContainer.USER_NAME_1, "someWrongPw");
	
	@Override
	public OpenUrRdbmsRealm provide()
	{
		return new OpenUrRdbmsRealmMock();
	}

	@Override
	public void dispose(OpenUrRdbmsRealm instance)
	{
	}
}
