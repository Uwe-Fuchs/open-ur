package org.openur.remoting.resource.userstructure;

import org.glassfish.hk2.api.Factory;
import org.openur.module.service.userstructure.IUserServices;

public class MockUserServicesFactory_ArbObj
	implements Factory<IUserServices>
{
	@Override
	public IUserServices provide()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dispose(IUserServices userServices)
	{
	}
}
