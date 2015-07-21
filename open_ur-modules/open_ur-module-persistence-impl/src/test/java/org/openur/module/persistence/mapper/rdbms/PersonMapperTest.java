package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.persistence.rdbms.config.MapperSpringConfig;
import org.openur.module.persistence.rdbms.entity.PPerson;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { MapperSpringConfig.class })
@ActiveProfiles(profiles = { "testMappers" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonMapperTest
{
	@Inject
	private PersonMapper personMapper;
	
	@Test
	public void testMapFromImmutable()
	{
		Person immutable = TestObjectContainer.PERSON_3;
		PPerson persistable = personMapper.mapFromImmutable(immutable);

		assertTrue(PersonMapper.immutableEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapFromEntity()
	{
		PPerson persistable = personMapper.mapFromImmutable(TestObjectContainer.PERSON_1);
		Person immutable = personMapper.mapFromEntity(persistable);
		
		assertTrue(PersonMapper.immutableEqualsToEntity(immutable, persistable));		
	}
}
