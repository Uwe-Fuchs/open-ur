package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.persistence.rdbms.entity.PTechnicalUser;

public class TechnicalUserMapperTest
{
	@Test
	public void testMapFromDomainObject()
	{
		TechnicalUser immutable = TestObjectContainer.TECH_USER_3;		
		PTechnicalUser persistable = new TechnicalUserMapper().mapFromDomainObject(immutable);

		assertTrue(TechnicalUserMapper.domainObjectEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapToImmutable()
	{
		PTechnicalUser persistable = new TechnicalUserMapper().mapFromDomainObject(TestObjectContainer.TECH_USER_3);		
		TechnicalUser immutable = new TechnicalUserMapper().mapFromEntity(persistable);

		assertTrue(TechnicalUserMapper.domainObjectEqualsToEntity(immutable, persistable));
	}
}