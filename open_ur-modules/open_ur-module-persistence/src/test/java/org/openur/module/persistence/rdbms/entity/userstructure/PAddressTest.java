package org.openur.module.persistence.rdbms.entity.userstructure;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Country;
import org.openur.module.domain.userstructure.Address.AddressBuilder;

public class PAddressTest
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
		
		Address address = b.build();
		PAddress pAddress = PAddress.mapFromImmutable(address);
		
		assertNotNull(pAddress);
		assertEquals(address.getCareOf(), pAddress.getCareOf());
		assertEquals(address.getCity(), pAddress.getCity());
		assertEquals(address.getCountry().getCountryCode(), pAddress.getCountryCode());
		assertEquals(address.getPoBox(), pAddress.getPoBox());
		assertEquals(address.getPostcode(), pAddress.getPostcode());
		assertEquals(address.getStreet(), pAddress.getStreet());
		assertEquals(address.getStreetNo(), pAddress.getStreetNo());
	}

	@Test
	public void testMapFromEntity()
	{
		PAddress pAddress = new PAddress();
		
		pAddress.setCareOf("Schmidt");
		pAddress.setCity("city_1");
		pAddress.setPoBox("poBox_1");
		pAddress.setPostcode("11");
		pAddress.setStreet("street_1");
		pAddress.setStreetNo("11");
		pAddress.setCountryCode("DE");
		
		Address address = PAddress.mapFromEntity(pAddress);
		
		assertNotNull(address);
		assertEquals(address.getCareOf(), pAddress.getCareOf());
		assertEquals(address.getCity(), pAddress.getCity());
		assertEquals(address.getCountry().getCountryCode(), pAddress.getCountryCode());
		assertEquals(address.getPoBox(), pAddress.getPoBox());
		assertEquals(address.getPostcode(), pAddress.getPostcode());
		assertEquals(address.getStreet(), pAddress.getStreet());
		assertEquals(address.getStreetNo(), pAddress.getStreetNo());
	}
}
