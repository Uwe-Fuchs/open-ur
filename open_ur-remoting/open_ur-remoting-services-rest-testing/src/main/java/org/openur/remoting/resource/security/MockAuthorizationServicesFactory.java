package org.openur.remoting.resource.security;

import java.util.UUID;

import org.glassfish.hk2.api.Factory;
import org.mockito.Mockito;
import org.openur.module.service.security.IAuthorizationServices;

public class MockAuthorizationServicesFactory
	implements Factory<IAuthorizationServices>
{
	static final String APP_NAME = "appName";
	static final String PERMISSION_TEXT = "permissionText";
	static final String OTHER_PERMISSION_TEXT = "otherPermissionText";
	static final String PERSON_ID = UUID.randomUUID().toString();
	static final String OU_ID = UUID.randomUUID().toString();
	
	@Override
	public IAuthorizationServices provide()
	{
		final IAuthorizationServices authorizationServices = Mockito.mock(IAuthorizationServices.class);
		
		Mockito.when(authorizationServices.hasPermission(PERSON_ID, OU_ID, PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.TRUE);		
		Mockito.when(authorizationServices.hasPermission(PERSON_ID, OU_ID, OTHER_PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.FALSE);
		Mockito.when(authorizationServices.hasPermission(PERSON_ID, PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.TRUE);		
		Mockito.when(authorizationServices.hasPermission(PERSON_ID, OTHER_PERMISSION_TEXT, APP_NAME)).thenReturn(Boolean.FALSE);
		
		return authorizationServices;
	}

	@Override
	public void dispose(IAuthorizationServices instance)
	{
	}
}
