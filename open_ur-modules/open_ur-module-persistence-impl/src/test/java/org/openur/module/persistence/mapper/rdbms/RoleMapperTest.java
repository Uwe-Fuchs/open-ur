package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.persistence.rdbms.entity.PRole;

public class RoleMapperTest
{
	@Test
	public void testMapFromImmutable()
	{
		OpenURRole immutable = TestObjectContainer.ROLE_Z;		
		PRole persistable = RoleMapper.mapFromImmutable(immutable);
		
		assertTrue(RoleMapper.immutableEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapFromEntity()
	{
		PRole persistable = RoleMapper.mapFromImmutable(TestObjectContainer.ROLE_Z);		
		OpenURRole immutable = RoleMapper.mapFromEntity(persistable);
		
		assertTrue(RoleMapper.immutableEqualsToEntity(immutable, persistable));
	}
}
