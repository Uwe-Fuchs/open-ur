package org.openur.remoting.resource.userstructure;

import org.glassfish.hk2.api.Factory;
import org.mockito.Mockito;
import org.openur.module.service.userstructure.IUserServices;

public class MockUserServicesFactory
	implements Factory<IUserServices>
{
	@Override
	public IUserServices provide()
	{
		final IUserServices mockedService = Mockito.mock(IUserServices.class);
		return mockedService;
	}

	@Override
	public void dispose(IUserServices userServices)
	{
		// TODO Auto-generated method stub

	}
}
