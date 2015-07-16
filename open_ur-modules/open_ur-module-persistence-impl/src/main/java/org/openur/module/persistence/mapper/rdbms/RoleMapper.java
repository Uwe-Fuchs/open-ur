package org.openur.module.persistence.mapper.rdbms;

import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.security.authorization.OpenURRoleBuilder;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.openur.module.persistence.rdbms.entity.PRole;

public class RoleMapper
{
	public static PRole mapFromImmutable(OpenURRole immutable)
	{
		PRole persistable = new PRole(immutable.getRoleName());
		persistable.setDescription(immutable.getDescription());
		
//		immutable.getAllPermissions().keySet()
//			.stream()
//			.forEach(
//				app -> immutable.getPermissions(app)
//					.stream()
//					.map(PermissionMapper::mapFromImmutable)
//					.forEach(pPerm -> persistable.addPermssion(pPerm))
//			);

		for (OpenURApplication app : immutable.getAllPermissions().keySet())
		{
			immutable.getPermissions(app)
				.stream()
				.map(PermissionMapper::mapFromImmutable)
				.forEach(p -> persistable.addPermssion(p));
		}
		
		return persistable;
	}
	
	public static OpenURRole mapFromEntity(PRole persistable)
	{
		OpenURRoleBuilder immutableBuilder = new OpenURRoleBuilder(persistable.getRoleName());
		
		immutableBuilder.permissions(
			persistable.getPermissions()
				.stream()
				.map(PermissionMapper::mapFromEntity)
				.collect(Collectors.toSet())
		);
		
		return immutableBuilder
				.description(persistable.getDescription())
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate())
				.build();
	}
	
	public static boolean immutableEqualsToEntity(OpenURRole immutable, PRole persistable)
	{
		if (!AbstractEntityMapper.immutableEqualsToEntity(immutable, persistable))
		{
			return false;
		}
		
		for (PPermission pPerm : persistable.getPermissions())
		{
			OpenURPermission permission = findPermissionInImmutable(pPerm, immutable);
			
			if (permission == null || !PermissionMapper.immutableEqualsToEntity(permission, pPerm))
			{
				return false;
			}
		}
		
		return new EqualsBuilder()
				.append(immutable.getRoleName(), persistable.getRoleName())
				.append(immutable.getDescription(), persistable.getDescription())
				.isEquals();
	}
	
	private static OpenURPermission findPermissionInImmutable(PPermission pPerm, OpenURRole immutable)
	{
		for (OpenURApplication app : immutable.getAllPermissions().keySet())
		{
			for (OpenURPermission perm : immutable.getPermissions(app))
			{
				if (PermissionMapper.immutableEqualsToEntity(perm, pPerm))
				{
					return perm;
				}
			}
		}
		
		return null;
	}
}
