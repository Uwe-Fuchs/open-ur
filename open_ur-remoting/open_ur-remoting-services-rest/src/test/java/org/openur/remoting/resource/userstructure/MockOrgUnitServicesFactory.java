package org.openur.remoting.resource.userstructure;

import java.util.Arrays;
import java.util.HashSet;

import org.glassfish.hk2.api.Factory;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.service.userstructure.IOrgUnitServices;

public class MockOrgUnitServicesFactory
	implements Factory<IOrgUnitServices>
{
	@Override
	public IOrgUnitServices provide()
	{
		final IOrgUnitServices orgUnitServicesMock = Mockito.mock(IOrgUnitServices.class);
		
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A)).thenReturn(TestObjectContainer.ORG_UNIT_A);
		Mockito.when(orgUnitServicesMock.findOrgUnitByNumber(TestObjectContainer.ORG_UNIT_NUMBER_A)).thenReturn(TestObjectContainer.ORG_UNIT_A);
		Mockito.when(orgUnitServicesMock.obtainAllOrgUnits()).thenReturn(new HashSet<>(Arrays.asList(TestObjectContainer.ORG_UNIT_A, TestObjectContainer.ORG_UNIT_B, 
			TestObjectContainer.ORG_UNIT_C)));

		return orgUnitServicesMock;
	}

	@Override
	public void dispose(IOrgUnitServices arg0)
	{		
	}
}
