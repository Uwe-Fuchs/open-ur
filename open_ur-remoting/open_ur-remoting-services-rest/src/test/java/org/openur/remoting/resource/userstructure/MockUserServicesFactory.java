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
		final IUserServices userServicesMock = Mockito.mock(IUserServices.class);
		Mockito.when(userServicesMock.findPersonById("123")).thenReturn(ResourceTestUtils.PERSON_1);
		
		return userServicesMock;
	}

	@Override
	public void dispose(IUserServices userServices)
	{
	}
}
