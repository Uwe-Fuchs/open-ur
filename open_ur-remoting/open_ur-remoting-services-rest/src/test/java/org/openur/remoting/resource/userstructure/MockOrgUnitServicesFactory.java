package org.openur.remoting.resource.userstructure;

import java.util.Arrays;
import java.util.HashSet;

import org.glassfish.hk2.api.Factory;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.service.userstructure.IOrgUnitServices;

public class MockOrgUnitServicesFactory
	implements Factory<IOrgUnitServices>
{
	static final AuthorizableOrgUnit MY_ROOT_OU;
	static final AuthorizableOrgUnit MY_SUPER_OU;
	static final AuthorizableOrgUnit MY_OU_1;
	static final AuthorizableOrgUnit MY_OU_2;
	
	static
	{
		MY_ROOT_OU = new AuthorizableOrgUnit.AuthorizableOrgUnitBuilder("myRootOuNo", "myRootOu")
				.build();
		
		MY_SUPER_OU = new AuthorizableOrgUnit.AuthorizableOrgUnitBuilder("mySuperOuNo", "mySuperOu")
				.rootOrgUnit(MY_ROOT_OU)
				.superOrgUnit(MY_ROOT_OU)
				.build();
		
		MY_OU_1 = new AuthorizableOrgUnit.AuthorizableOrgUnitBuilder("myOu_1_No", "myOu_1")
				.rootOrgUnit(MY_ROOT_OU)
				.superOrgUnit(MY_SUPER_OU)
				.build();
		
		MY_OU_2 = new AuthorizableOrgUnit.AuthorizableOrgUnitBuilder("myOu_2_No", "myOu_2")
				.rootOrgUnit(MY_ROOT_OU)
				.superOrgUnit(MY_SUPER_OU)
				.build();
	}
	
	@Override
	public IOrgUnitServices provide()
	{
		final IOrgUnitServices orgUnitServicesMock = Mockito.mock(IOrgUnitServices.class);
		
		Mockito.when(orgUnitServicesMock.findOrgUnitById(TestObjectContainer.ORG_UNIT_UUID_A)).thenReturn(TestObjectContainer.ORG_UNIT_A);
		Mockito.when(orgUnitServicesMock.findOrgUnitByNumber(TestObjectContainer.ORG_UNIT_NUMBER_A)).thenReturn(TestObjectContainer.ORG_UNIT_A);
		Mockito.when(orgUnitServicesMock.obtainAllOrgUnits()).thenReturn(new HashSet<>(Arrays.asList(TestObjectContainer.ORG_UNIT_A, TestObjectContainer.ORG_UNIT_B, 
			TestObjectContainer.ORG_UNIT_C)));
		Mockito.when(orgUnitServicesMock.obtainSubOrgUnitsForOrgUnit(TestObjectContainer.SUPER_OU_UUID_1, true)).thenReturn(
			new HashSet<>(Arrays.asList(TestObjectContainer.ORG_UNIT_A, TestObjectContainer.ORG_UNIT_B)));
		Mockito.when(orgUnitServicesMock.obtainSubOrgUnitsForOrgUnit(MY_SUPER_OU.getIdentifier(), false)).thenReturn(
			new HashSet<>(Arrays.asList(MY_OU_1, MY_OU_2)));
		Mockito.when(orgUnitServicesMock.obtainRootOrgUnits()).thenReturn(new HashSet<>(Arrays.asList(TestObjectContainer.ROOT_OU)));

		return orgUnitServicesMock;
	}

	@Override
	public void dispose(IOrgUnitServices arg0)
	{		
	}
}
