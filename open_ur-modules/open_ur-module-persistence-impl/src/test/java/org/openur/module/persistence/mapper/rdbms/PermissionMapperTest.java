package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.persistence.rdbms.entity.PPermission;

public class PermissionMapperTest
{
	@Test
	public void testMapFromImmutable()
	{
		OpenURPermission immutable = TestObjectContainer.PERMISSION_2_C;		
		PPermission persistable = PermissionMapper.mapFromImmutable(immutable);
		
		assertTrue(PermissionMapper.immutableEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapFromEntity()
	{
		PPermission persistable = PermissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_2_C);		
		OpenURPermission immutable = PermissionMapper.mapFromEntity(persistable);
		
		assertTrue(PermissionMapper.immutableEqualsToEntity(immutable, persistable));
	}
}
