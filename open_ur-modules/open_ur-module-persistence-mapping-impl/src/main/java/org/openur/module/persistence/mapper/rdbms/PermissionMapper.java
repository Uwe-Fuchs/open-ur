package org.openur.module.persistence.mapper.rdbms;

import javax.inject.Inject;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURPermissionBuilder;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPermission;

public class PermissionMapper
	extends AbstractEntityMapper implements IPermissionMapper<OpenURPermission>
{
	@Inject
	private ApplicationMapper applicationMapper;
	
	@Override
	public PPermission mapFromDomainObject(OpenURPermission domainObject)
	{
		PApplication pApp = applicationMapper.mapFromDomainObject(domainObject.getApplication());
		PPermission persistable = new PPermission(domainObject.getPermissionText(), pApp);

		persistable.setPermissionScope(domainObject.getPermissionScope());
		
		return persistable;
	}
	
	@Override
	public OpenURPermission mapFromEntity(PPermission entity)
	{
		OpenURApplication app = applicationMapper.mapFromEntity(entity.getApplication());
		OpenURPermissionBuilder immutableBuilder = new OpenURPermissionBuilder(entity.getPermissionText(), app);
		
		super.mapFromEntity(immutableBuilder, entity);
		
		return immutableBuilder
				.permissionScope(entity.getPermissionScope())
				.build();
	}
	
	public static boolean domainObjectEqualsToEntity(OpenURPermission domainObject, PPermission entity)
	{
		if (!AbstractEntityMapper.domainObjectEqualsToEntity(domainObject, entity))
		{
			return false;
		}
		
		return domainObject.getPermissionText().equals(entity.getPermissionText());
	}
}
