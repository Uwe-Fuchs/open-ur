package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser.TechnicalUserBuilder;
import org.openur.module.persistence.rdbms.entity.PTechnicalUser;

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
		assertTrue(TechnicalUserMapperTest.immutableEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapToImmutable()
	{
		PTechnicalUser persistable = new PTechnicalUser("123abc");
		persistable.setStatus(Status.INACTIVE);
		
		TechnicalUser immutable = TechnicalUserMapper.mapFromEntity(persistable);
		
		assertNotNull(persistable);
		assertTrue(TechnicalUserMapperTest.immutableEqualsToEntity(immutable, persistable));
	}

	public static boolean immutableEqualsToEntity(TechnicalUser immutable, PTechnicalUser persistable)
	{
		if (!AbstractEntityMapperTest.immutableEqualsToEntityUserStructureBase(immutable, persistable))
		{
			return false;
		}
		
		return TechnicalUserMapper.immutableEqualsToEntity(immutable, persistable);
	}
}
