package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.persistence.rdbms.entity.PPerson;

public class PersonMapperTest
{
	@Test
	public void testMapFromImmutable()
	{
		Person immutable = TestObjectContainer.PERSON_3;
		PPerson persistable = PersonMapper.mapFromImmutable(immutable);

		assertTrue(PersonMapper.immutableEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapFromEntity()
	{
		PPerson persistable = PersonMapper.mapFromImmutable(TestObjectContainer.PERSON_1);
		Person immutable = PersonMapper.mapFromEntity(persistable);
		
		assertTrue(PersonMapper.immutableEqualsToEntity(immutable, persistable));		
	}
}
