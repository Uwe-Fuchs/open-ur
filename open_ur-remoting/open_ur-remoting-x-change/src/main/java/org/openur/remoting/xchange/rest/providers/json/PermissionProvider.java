package org.openur.remoting.xchange.rest.providers.json;

import org.openur.module.domain.security.authorization.OpenURPermission;

public class PermissionProvider
	extends AbstractProvider<OpenURPermission>
{	
	@Override
	protected boolean isProvided(Class<?> type)
	{
		return OpenURPermission.class.isAssignableFrom(type);
	}
}
