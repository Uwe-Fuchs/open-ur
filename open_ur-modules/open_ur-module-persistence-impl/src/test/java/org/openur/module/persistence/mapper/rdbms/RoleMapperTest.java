package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURPermissionBuilder;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.security.authorization.OpenURRoleBuilder;
import org.openur.module.domain.security.authorization.PermissionScope;
import org.openur.module.persistence.mapper.rdbms.PermissionMapper;
import org.openur.module.persistence.mapper.rdbms.RoleMapper;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.openur.module.persistence.rdbms.entity.PRole;

public class RoleMapperTest
{
	@Test
	public void testMapFromImmutable()
	{
		OpenURApplication app1 = new OpenURApplicationBuilder("app1")
			.build();		
		OpenURPermission perm1 = new OpenURPermissionBuilder("perm1", app1)
			.description("description perm1")
			.permissionScope(PermissionScope.SELECTED)
			.build();
		
		OpenURApplication app2 = new OpenURApplicationBuilder("app2")
			.build();
		OpenURPermission perm2 = new OpenURPermissionBuilder("perm2", app2)
			.description("description perm2")
			.permissionScope(PermissionScope.SELECTED_SUB)
			.build();
	
		OpenURRole immutable = new OpenURRoleBuilder("role1")
			.description("description role1")
			.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm1, perm2)))
			.build();
		
		PRole persistable = RoleMapper.mapFromImmutable(immutable);
		
		assertTrue(immutableEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapFromEntity()
	{
		OpenURApplication app1 = new OpenURApplicationBuilder("app1")
			.build();		
		OpenURPermission perm1 = new OpenURPermissionBuilder("perm1", app1)
			.description("description perm1")
			.permissionScope(PermissionScope.SELECTED)
			.build();
		PPermission pPerm1 = PermissionMapper.mapFromImmutable(perm1);
		
		OpenURApplication app2 = new OpenURApplicationBuilder("app2")
			.build();		
		OpenURPermission perm2 = new OpenURPermissionBuilder("perm2", app2)
			.description("description perm2")
			.permissionScope(PermissionScope.SELECTED_SUB)
			.build();
		PPermission pPerm2 = PermissionMapper.mapFromImmutable(perm2);
	
		PRole persistable = new PRole("role1");
		persistable.setDescription("description role1");
		persistable.setPermissions(new HashSet<PPermission>(Arrays.asList(pPerm1, pPerm2)));
		
		OpenURRole immutable = RoleMapper.mapFromEntity(persistable);
		
		assertTrue(immutableEqualsToEntity(immutable, persistable));
	}
	
	public static boolean immutableEqualsToEntity(OpenURRole immutable, PRole persistable)
	{
		if (!AbstractEntityMapperTest.immutableEqualsToEntityBase(immutable, persistable))
		{
			return false;
		}
		
		for (PPermission pPerm : persistable.getPermissions())
		{
			OpenURPermission permission = findPermissionInImmutable(pPerm, immutable);
			
			if (permission == null || !PermissionMapperTest.immutableEqualsToEntity(permission, pPerm))
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
				if (PermissionMapperTest.immutableEqualsToEntity(perm, pPerm))
				{
					return perm;
				}
			}
		}
		
		return null;
	}
}
