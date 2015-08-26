package org.openur.remoting.resource.security;

import java.util.Arrays;
import java.util.HashSet;

import org.glassfish.hk2.api.Factory;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.service.security.ISecurityDomainServices;

public class MockSecurityDomainServicesFactory
	implements Factory<ISecurityDomainServices>
{
	@Override
	public ISecurityDomainServices provide()
	{
		final ISecurityDomainServices securityDomainServices = Mockito.mock(ISecurityDomainServices.class);
		
		Mockito.when(securityDomainServices.findPermissionById(TestObjectContainer.PERMISSION_1_A.getIdentifier())).thenReturn(
			TestObjectContainer.PERMISSION_1_A);
		Mockito.when(securityDomainServices.findPermissionByName(TestObjectContainer.PERMISSION_1_A.getPermissionName())).thenReturn(
			TestObjectContainer.PERMISSION_1_A);
		Mockito.when(securityDomainServices.obtainAllPermissions()).thenReturn(new HashSet<>(Arrays.asList(
			TestObjectContainer.PERMISSION_1_A, TestObjectContainer.PERMISSION_1_B, TestObjectContainer.PERMISSION_1_C,
			TestObjectContainer.PERMISSION_2_A, TestObjectContainer.PERMISSION_2_B, TestObjectContainer.PERMISSION_2_C)));
		Mockito.when(securityDomainServices.obtainPermissionsForApp(TestObjectContainer.APP_A.getApplicationName())).thenReturn(
			new HashSet<>(Arrays.asList(TestObjectContainer.PERMISSION_1_A, TestObjectContainer.PERMISSION_2_A)));
		
		return securityDomainServices;
	}
	
	@Override
	public void dispose(ISecurityDomainServices arg0)
	{
	}
}
