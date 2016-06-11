package org.openur.module.persistence.mapper.rdbms;

import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.security.authorization.OpenURRoleBuilder;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.openur.module.persistence.rdbms.entity.PRole;

public class RoleMapper
	extends AbstractEntityMapper implements IEntityDomainObjectMapper<PRole, OpenURRole>
{
	@Inject
	private PermissionMapper permissionMapper;
	
	@Override
	public PRole mapFromDomainObject(OpenURRole domainObject)
	{
		PRole persistable = new PRole(domainObject.getRoleName());
		persistable.setDescription(domainObject.getDescription());
		
//		immutable.getAllPermissions().keySet()
//			.stream()
//			.forEach(
//				app -> immutable.getPermissions(app)
//					.stream()
//					.map(permissionMapper::mapFromImmutable)
//					.forEach(pPerm -> persistable.addPermssion(pPerm))
//			);

		for (OpenURApplication app : domainObject.getAllPermissions().keySet())
		{
			domainObject.getPermissions(app)
				.stream()
				.map(permissionMapper::mapFromDomainObject)
				.forEach(p -> persistable.addPermssion(p));
		}
		
		return persistable;
	}
	
	@Override
	public OpenURRole mapFromEntity(PRole entity)
	{
		OpenURRoleBuilder immutableBuilder = new OpenURRoleBuilder(entity.getRoleName());
		
		super.mapFromEntity(immutableBuilder, entity);
		
		immutableBuilder.permissions(
			entity.getPermissions()
				.stream()
				.map(permissionMapper::mapFromEntity)
				.collect(Collectors.toSet()),
			immutableBuilder
		);
		
		return immutableBuilder
				.description(entity.getDescription())
				.build();
	}
	
	public static boolean domainObjectEqualsToEntity(OpenURRole domainObject, PRole entity)
	{
		if (!AbstractEntityMapper.domainObjectEqualsToEntity(domainObject, entity))
		{
			return false;
		}
		
		for (PPermission pPerm : entity.getPermissions())
		{
			OpenURPermission permission = findPermissionInDomainObject(pPerm, domainObject);
			
			if (permission == null || !PermissionMapper.domainObjectEqualsToEntity(permission, pPerm))
			{
				return false;
			}
		}
		
		return new EqualsBuilder()
				.append(domainObject.getRoleName(), entity.getRoleName())
				.append(domainObject.getDescription(), entity.getDescription())
				.isEquals();
	}
	
	private static OpenURPermission findPermissionInDomainObject(PPermission pPerm, OpenURRole openUrRole)
	{
		for (OpenURApplication openUrApp : openUrRole.getAllPermissions().keySet())
		{
			for (OpenURPermission openUrPerm : openUrRole.getPermissions(openUrApp))
			{
				if (PermissionMapper.domainObjectEqualsToEntity(openUrPerm, pPerm))
				{
					return openUrPerm;
				}
			}
		}
		
		return null;
	}
}
