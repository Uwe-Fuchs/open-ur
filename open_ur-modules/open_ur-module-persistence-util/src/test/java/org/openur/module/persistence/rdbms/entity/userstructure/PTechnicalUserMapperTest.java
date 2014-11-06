package org.openur.module.persistence.rdbms.entity.userstructure;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUserBuilder;

public class PTechnicalUserMapperTest
{
	@Test
	public void testMapFromImmutable()
	{
		TechnicalUser immutable = new TechnicalUserBuilder()
				.number("123abc")
				.status(Status.ACTIVE)
				.build();
		
		PTechnicalUser persistable = PTechnicalUserMapper.mapFromImmutable(immutable);
		
		assertNotNull(persistable);
		assertTrue(PTechnicalUserMapper.immutableEqualsToPersistable(immutable, persistable));
	}

	@Test
	public void testMapToImmutable()
	{
		PTechnicalUser persistable = new PTechnicalUser();
		persistable.setNumber("123abc");
		persistable.setStatus(Status.INACTIVE);
		
		TechnicalUser immutable = PTechnicalUserMapper.mapToImmutable(persistable);
		
		assertNotNull(persistable);
		assertTrue(PTechnicalUserMapper.immutableEqualsToPersistable(immutable, persistable));
	}
}
