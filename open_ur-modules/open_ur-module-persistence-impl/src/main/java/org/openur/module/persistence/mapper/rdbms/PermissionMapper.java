package org.openur.module.persistence.mapper.rdbms;

import javax.inject.Inject;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURPermissionBuilder;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPermission;

public class PermissionMapper
	extends AbstractEntityMapper
{
	@Inject
	private ApplicationMapper applicationMapper;
	
	public PPermission mapFromImmutable(OpenURPermission immutable)
	{
		PApplication pApp = applicationMapper.mapFromImmutable(immutable.getApplication());
		PPermission persistable = new PPermission(immutable.getPermissionName(), pApp);

		persistable.setPermissionScope(immutable.getPermissionScope());
		persistable.setDescription(immutable.getDescription());
		
		return persistable;
	}
	
	public OpenURPermission mapFromEntity(PPermission persistable)
	{
		OpenURApplication app = applicationMapper.mapFromEntity(persistable.getApplication());
		OpenURPermissionBuilder immutableBuilder = new OpenURPermissionBuilder(persistable.getPermissionName(), app);
		
		super.mapFromEntity(immutableBuilder, persistable);
		
		return immutableBuilder
				.permissionScope(persistable.getPermissionScope())
				.description(persistable.getDescription())
				.build();
	}
	
	public static boolean immutableEqualsToEntity(OpenURPermission immutable, PPermission persistable)
	{
		if (!AbstractEntityMapper.immutableEqualsToEntity(immutable, persistable))
		{
			return false;
		}
		
		return immutable.getPermissionName().equals(persistable.getPermissionName());
	}
}
