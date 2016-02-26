package org.openur.remoting.resource.security;

import org.glassfish.hk2.api.Factory;
import org.openur.module.service.security.realm.rdbms.OpenUrRdbmsRealm;

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
}
