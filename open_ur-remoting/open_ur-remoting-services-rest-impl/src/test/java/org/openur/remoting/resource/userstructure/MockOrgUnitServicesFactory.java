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
		
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A, Boolean.TRUE)).thenReturn(TestObjectContainer.ORG_UNIT_A);
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A, Boolean.FALSE))
			.thenReturn(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS);
		Mockito.when(orgUnitServicesMock.findOrgUnitByNumber(TestObjectContainer.ORG_UNIT_NUMBER_A, Boolean.TRUE)).thenReturn(TestObjectContainer.ORG_UNIT_A);
		Mockito.when(orgUnitServicesMock.findOrgUnitByNumber(TestObjectContainer.ORG_UNIT_NUMBER_A, Boolean.FALSE))
			.thenReturn(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS);
		Mockito.when(orgUnitServicesMock.obtainAllOrgUnits()).thenReturn(new HashSet<>(Arrays.asList(TestObjectContainer.ORG_UNIT_A, TestObjectContainer.ORG_UNIT_B, 
			TestObjectContainer.ORG_UNIT_C)));
		Mockito.when(orgUnitServicesMock.obtainSubOrgUnitsForOrgUnit(TestObjectContainer.SUPER_OU_UUID_1, Boolean.TRUE)).thenReturn(
			new HashSet<>(Arrays.asList(TestObjectContainer.ORG_UNIT_A, TestObjectContainer.ORG_UNIT_B)));
		Mockito.when(orgUnitServicesMock.obtainSubOrgUnitsForOrgUnit(TestObjectContainer.SUPER_OU_UUID_1, Boolean.FALSE)).thenReturn(
			new HashSet<>(Arrays.asList(TestObjectContainer.ORG_UNIT_A_WITHOUT_MEMBERS)));
		Mockito.when(orgUnitServicesMock.obtainRootOrgUnits()).thenReturn(new HashSet<>(Arrays.asList(TestObjectContainer.ROOT_OU)));

		return orgUnitServicesMock;
	}

	@Override
	public void dispose(IOrgUnitServices arg0)
	{		
	}
}
