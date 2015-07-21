package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.persistence.rdbms.config.MapperSpringConfig;
import org.openur.module.persistence.rdbms.entity.PRole;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { MapperSpringConfig.class })
@ActiveProfiles(profiles = { "testMappers" })
@RunWith(SpringJUnit4ClassRunner.class)
public class RoleMapperTest
{
	@Inject
	private RoleMapper roleMapper;
	
	@Test
	public void testMapFromImmutable()
	{
		OpenURRole immutable = TestObjectContainer.ROLE_Z;		
		PRole persistable = roleMapper.mapFromImmutable(immutable);
		
		assertTrue(RoleMapper.immutableEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapFromEntity()
	{
		PRole persistable = roleMapper.mapFromImmutable(TestObjectContainer.ROLE_Z);		
		OpenURRole immutable = roleMapper.mapFromEntity(persistable);
		
		assertTrue(RoleMapper.immutableEqualsToEntity(immutable, persistable));
	}
}
