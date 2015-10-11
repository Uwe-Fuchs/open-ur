package org.openur.remoting.client.ws.rs.userstructure;

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
		Mockito.when(userServicesMock.findTechnicalUserById(TestObjectContainer.TECH_USER_UUID_1)).thenReturn(TestObjectContainer.TECH_USER_1);
		Mockito.when(userServicesMock.findTechnicalUserByNumber(TestObjectContainer.TECH_USER_NUMBER_1)).thenReturn(TestObjectContainer.TECH_USER_1);
		Mockito.when(userServicesMock.obtainAllTechnicalUsers()).thenReturn(new HashSet<>(Arrays.asList(TestObjectContainer.TECH_USER_1, 
			TestObjectContainer.TECH_USER_2, TestObjectContainer.TECH_USER_3)));

		return userServicesMock;
	}

	@Override
	public void dispose(IUserServices userServices)
	{
	}
}
