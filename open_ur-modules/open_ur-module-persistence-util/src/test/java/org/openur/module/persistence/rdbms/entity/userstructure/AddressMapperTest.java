package org.openur.module.persistence.rdbms.entity.userstructure;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Address.AddressBuilder;
import org.openur.module.domain.userstructure.Country;

public class AddressMapperTest
{
	@Test
	public void testMapFromImmutable()
	{
		AddressBuilder b = new AddressBuilder("11")
				.country(Country.byCode("DE"))
				.city("city_1")
				.street("street_1")
				.streetNo("11")
				.poBox("poBox_1")
				.careOf("Schmidt");
		
		Address immutable = b.build();
		PAddress persistable = AddressMapper.mapFromImmutable(immutable);
		
		assertNotNull(persistable);
		assertTrue(AddressMapper.immutableEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapFromEntity()
	{
		PAddress persistable = new PAddress();
		
		persistable.setCareOf("Schmidt");
		persistable.setCity("city_1");
		persistable.setPoBox("poBox_1");
		persistable.setPostcode("11");
		persistable.setStreet("street_1");
		persistable.setStreetNo("11");
		persistable.setCountryCode("DE");
		
		Address immutable = AddressMapper.mapFromEntity(persistable);
		
		assertNotNull(immutable);
		assertTrue(AddressMapper.immutableEqualsToEntity(immutable, persistable));
	}
}
