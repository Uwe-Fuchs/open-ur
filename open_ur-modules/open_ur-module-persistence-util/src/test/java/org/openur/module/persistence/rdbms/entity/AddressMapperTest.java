package org.openur.module.persistence.rdbms.entity;

import static org.junit.Assert.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Address.AddressBuilder;
import org.openur.module.domain.userstructure.Country;
import org.openur.module.persistence.rdbms.entity.AddressMapper;
import org.openur.module.persistence.rdbms.entity.PAddress;

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
		assertTrue(AddressMapperTest.immutableEqualsToEntity(immutable, persistable));
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
		assertTrue(AddressMapperTest.immutableEqualsToEntity(immutable, persistable));
	}

	public static boolean immutableEqualsToEntity(Address immutable, PAddress persistable)
	{
		if (!AbstractEntityMapperTest.immutableEqualsToEntityIdentifiable(immutable, persistable))
		{
			return false;
		}
		
		return new EqualsBuilder()
				.append(immutable.getCareOf(), persistable.getCareOf())
				.append(immutable.getCity(), persistable.getCity())
				.append(immutable.getCountry() != null ? immutable.getCountry().getCountryCode() : null, persistable.getCountryCode())
				.append(immutable.getPoBox(), persistable.getPoBox())
				.append(immutable.getPostcode(), persistable.getPostcode())
				.append(immutable.getStreet(), persistable.getStreet())
				.append(immutable.getStreetNo(), persistable.getStreetNo())
				.isEquals();
	}
}
