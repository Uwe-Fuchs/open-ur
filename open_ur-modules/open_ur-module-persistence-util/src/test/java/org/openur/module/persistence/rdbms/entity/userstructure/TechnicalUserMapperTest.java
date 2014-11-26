package org.openur.module.persistence.rdbms.entity.userstructure;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser.TechnicalUserBuilder;

public class TechnicalUserMapperTest
{
	@Test
	public void testMapFromImmutable()
	{
		TechnicalUser immutable = new TechnicalUserBuilder("123abc")
				.status(Status.ACTIVE)
				.build();
		
		PTechnicalUser persistable = TechnicalUserMapper.mapFromImmutable(immutable);
		
		assertNotNull(persistable);
		assertTrue(TechnicalUserMapper.immutableEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapToImmutable()
	{
		PTechnicalUser persistable = new PTechnicalUser();
		persistable.setNumber("123abc");
		persistable.setStatus(Status.INACTIVE);
		
		TechnicalUser immutable = TechnicalUserMapper.mapFromEntity(persistable);
		
		assertNotNull(persistable);
		assertTrue(TechnicalUserMapper.immutableEqualsToEntity(immutable, persistable));
	}
}
