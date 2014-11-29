package org.openur.module.persistence.rdbms.entity.security;

import org.apache.commons.lang3.StringUtils;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURPermissionBuilder;
import org.openur.module.persistence.rdbms.entity.application.ApplicationMapper;

public class PermissionMapper
{
	public static PPermission mapFromImmutable(OpenURPermission immutable)
	{
		PPermission persistable = new PPermission();
		
		persistable.setPermissionName(immutable.getPermissionName());
		persistable.setPermissionScope(immutable.getPermissionScope());
		persistable.setDescription(immutable.getDescription());
		persistable.setApplication(ApplicationMapper.mapFromImmutable(immutable.getApplication()));
		
		return persistable;
	}
	
	public static OpenURPermission mapFromEntity(PPermission persistable)
	{
		OpenURApplication app = ApplicationMapper.mapFromEntity(persistable.getApplication());
		OpenURPermissionBuilder immutableBuilder = new OpenURPermissionBuilder(persistable.getPermissionName(), app);
		
		if (StringUtils.isNotEmpty(persistable.getIdentifier()))
		{
			immutableBuilder.identifier(persistable.getIdentifier());
		}
		
		return immutableBuilder
			.permissionScope(persistable.getPermissionScope())
			.description(persistable.getDescription())
			.creationDate(persistable.getCreationDate())
			.lastModifiedDate(persistable.getLastModifiedDate())
			.build();
	}
}
