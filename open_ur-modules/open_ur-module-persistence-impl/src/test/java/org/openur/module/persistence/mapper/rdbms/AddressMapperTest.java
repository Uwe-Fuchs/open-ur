package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.persistence.rdbms.entity.PAddress;

public class AddressMapperTest
{
	@Test
	public void testMapFromImmutable()
	{
		Address immutable = TestObjectContainer.ADDRESS_3;
		PAddress persistable = AddressMapper.mapFromImmutable(immutable);
		
		assertNotNull(persistable);
		assertTrue(AddressMapper.immutableEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapFromEntity()
	{
		PAddress persistable = new PAddress("11");
		
		persistable.setCareOf("Schmidt");
		persistable.setCity("city_1");
		persistable.setPoBox("poBox_1");
		persistable.setStreet("street_1");
		persistable.setStreetNo("11");
		persistable.setCountryCode("DE");
		
		Address immutable = AddressMapper.mapFromEntity(persistable);
		
		assertNotNull(immutable);
		assertTrue(AddressMapper.immutableEqualsToEntity(immutable, persistable));
	}
}
