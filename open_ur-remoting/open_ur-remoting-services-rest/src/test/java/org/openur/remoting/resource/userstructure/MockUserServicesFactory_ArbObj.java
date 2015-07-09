package org.openur.remoting.resource.userstructure;

import java.util.Arrays;
import java.util.HashSet;

import org.glassfish.hk2.api.Factory;
import org.mockito.Mockito;
import org.openur.module.service.userstructure.IUserServices;

public class MockUserServicesFactory_ArbObj
	implements Factory<IUserServices>
{
	@Override
	public IUserServices provide()
	{
		final IUserServices userServicesMock = Mockito.mock(IUserServices.class);
		
		Mockito.when(userServicesMock.findPersonById(UserResourceTest_ArbObj.UUID_1)).thenReturn(UserResourceTest_ArbObj.PERSON_1);
		Mockito.when(userServicesMock.findPersonByNumber(UserResourceTest_ArbObj.NO_123)).thenReturn(UserResourceTest_ArbObj.PERSON_1);
		Mockito.when(userServicesMock.obtainAllPersons()).thenReturn(
					new HashSet<>(Arrays.asList(UserResourceTest_ArbObj.PERSON_1, UserResourceTest_ArbObj.PERSON_2)));
		Mockito.when(userServicesMock.findTechnicalUserById(UserResourceTest_ArbObj.UUID_2)).thenReturn(UserResourceTest_ArbObj.TECH_USER_1);
		Mockito.when(userServicesMock.findTechnicalUserByNumber(UserResourceTest_ArbObj.NO_456)).thenReturn(UserResourceTest_ArbObj.TECH_USER_1);
		Mockito.when(userServicesMock.obtainAllTechnicalUsers()).thenReturn(
					new HashSet<>(Arrays.asList(UserResourceTest_ArbObj.TECH_USER_1, UserResourceTest_ArbObj.TECH_USER_2)));

		return userServicesMock;
	}

	@Override
	public void dispose(IUserServices userServices)
	{
	}
}
