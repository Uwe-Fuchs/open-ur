package org.openur.module.persistence.mapper.rdbms;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURPermissionBuilder;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPermission;

public class PermissionMapper
{
	public static PPermission mapFromImmutable(OpenURPermission immutable)
	{
		PApplication pApp = ApplicationMapper.mapFromImmutable(immutable.getApplication());
		PPermission persistable = new PPermission(immutable.getPermissionName(), pApp);

		persistable.setPermissionScope(immutable.getPermissionScope());
		persistable.setDescription(immutable.getDescription());
		
		return persistable;
	}
	
	public static OpenURPermission mapFromEntity(PPermission persistable)
	{
		OpenURApplication app = ApplicationMapper.mapFromEntity(persistable.getApplication());
		OpenURPermissionBuilder immutableBuilder = new OpenURPermissionBuilder(persistable.getPermissionName(), app);
		
		return immutableBuilder
				.permissionScope(persistable.getPermissionScope())
				.description(persistable.getDescription())
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate())
				.build();
	}
	
	public static boolean immutableEqualsToEntity(OpenURPermission immutable, PPermission persistable)
	{
		return immutable.getPermissionName().equals(persistable.getPermissionName());
	}
}
