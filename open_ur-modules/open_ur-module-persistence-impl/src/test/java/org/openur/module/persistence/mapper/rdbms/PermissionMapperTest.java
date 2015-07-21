package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.persistence.rdbms.config.MapperSpringConfig;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { MapperSpringConfig.class })
@ActiveProfiles(profiles = { "testMappers" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PermissionMapperTest
{
	@Inject
	private PermissionMapper permissionMapper;
	
	@Test
	public void testMapFromImmutable()
	{
		OpenURPermission immutable = TestObjectContainer.PERMISSION_2_C;		
		PPermission persistable = permissionMapper.mapFromImmutable(immutable);
		
		assertTrue(PermissionMapper.immutableEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapFromEntity()
	{
		PPermission persistable = permissionMapper.mapFromImmutable(TestObjectContainer.PERMISSION_2_C);		
		OpenURPermission immutable = permissionMapper.mapFromEntity(persistable);
		
		assertTrue(PermissionMapper.immutableEqualsToEntity(immutable, persistable));
	}
}
