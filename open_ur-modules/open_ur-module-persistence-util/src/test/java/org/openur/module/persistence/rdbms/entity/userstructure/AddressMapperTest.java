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
		AddressBuilder b = Address.builder();		
		b.country(Country.byCode("DE"));
		b.city("city_1");
		b.postcode("11");
		b.street("street_1");
		b.streetNo("11");
		b.poBox("poBox_1");
		b.careOf("Schmidt");
		
		Address immutable = b.build();
		PAddress persistable = AddressMapper.mapFromImmutable(immutable);
		
		assertNotNull(persistable);
		assertTrue(AddressMapper.immutableEqualsToPersistable(immutable, persistable));
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
		assertTrue(AddressMapper.immutableEqualsToPersistable(immutable, persistable));
	}
}
