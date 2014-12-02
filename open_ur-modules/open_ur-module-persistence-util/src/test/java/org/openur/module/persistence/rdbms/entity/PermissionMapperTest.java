package org.openur.module.persistence.rdbms.entity;

import static org.junit.Assert.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURPermissionBuilder;
import org.openur.module.domain.security.authorization.PermissionScope;
import org.openur.module.persistence.rdbms.entity.ApplicationMapper;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.openur.module.persistence.rdbms.entity.PermissionMapper;

public class PermissionMapperTest
{
	@Test
	public void testMapFromImmutable()
	{
		OpenURApplication app = new OpenURApplicationBuilder("appName").build();		
		
		OpenURPermission immutable = new OpenURPermissionBuilder("permName", app)
				.permissionScope(PermissionScope.SELECTED)
				.description("permission description")
				.build();
		
		PPermission persistable = PermissionMapper.mapFromImmutable(immutable);
		
		assertTrue(immutableEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapFromEntity()
	{
		OpenURApplication app = new OpenURApplicationBuilder("appName").build();
		PApplication pApp = ApplicationMapper.mapFromImmutable(app);
		
		PPermission persistable = new PPermission();
		persistable.setApplication(pApp);
		persistable.setPermissionName("permName");
		persistable.setDescription("permDescription");
		persistable.setPermissionScope(PermissionScope.SELECTED_SUB);
		
		OpenURPermission immutable = PermissionMapper.mapFromEntity(persistable);
		
		assertTrue(immutableEqualsToEntity(immutable, persistable));
	}
	
	public static boolean immutableEqualsToEntity(OpenURPermission immutable, PPermission persistable)
	{
		if (!AbstractEntityMapperTest.immutableEqualsToEntityIdentifiable(immutable, persistable))
		{
			return false;
		}
	
		return new EqualsBuilder()
				.append(immutable.getApplication().getApplicationName(), persistable.getApplication().getApplicationName())
				.append(immutable.getPermissionName(), persistable.getPermissionName())
				.append(immutable.getPermissionScope(), persistable.getPermissionScope())
				.append(immutable.getDescription(), persistable.getDescription())
				.isEquals();
	}
}
