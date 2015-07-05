package org.openur.remoting.resource.userstructure;

import java.util.Arrays;
import java.util.HashSet;

import org.glassfish.hk2.api.Factory;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.service.userstructure.IUserServices;

public class MockUserServicesFactory
	implements Factory<IUserServices>
{
	@Override
	public IUserServices provide()
	{
		final IUserServices userServicesMock = Mockito.mock(IUserServices.class);
		Mockito.when(userServicesMock.findPersonById(TestObjectContainer.PERSON_UUID_1)).thenReturn(TestObjectContainer.PERSON_1);
		Mockito.when(userServicesMock.findPersonByNumber(TestObjectContainer.PERSON_NUMBER_1)).thenReturn(TestObjectContainer.PERSON_1);
		Mockito.when(userServicesMock.obtainAllPersons()).thenReturn(new HashSet<>(Arrays.asList(TestObjectContainer.PERSON_1, TestObjectContainer.PERSON_2, 
			TestObjectContainer.PERSON_3)));

		return userServicesMock;
	}

	@Override
	public void dispose(IUserServices userServices)
	{
	}
}
