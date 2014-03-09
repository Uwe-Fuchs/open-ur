package org.openur.module.domain.userstructure;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Country;
import org.openur.module.domain.userstructure.Address.AddressBuilder;

public class AddressTest
{
	@Test
	public void testCompareTo()
	{
		AddressBuilder b = Address.builder();
		Address address1;
		Address address2;
		
		b.country(Country.byCode("DE"));
		b.city("city");
		address1 = b.build();		
		b.country(Country.byCode("FR"));
		b.city("city");
		address2 = b.build();
		assertTrue("'DE' should be before 'FR'", address1.compareTo(address2) < 1);
		
		b.country(Country.byCode("DE"));
		b.city("city_1");
		address1 = b.build();
		b.country(Country.byCode("DE"));
		b.city("city_2");
		address2 = b.build();
		assertTrue("equal countries: 'city_1' should be before 'city_2'", 
			address1.compareTo(address2) < 1);
		
		b.country(Country.byCode("DE"));
		b.city("city_1");
		b.postcode("11");
		address1 = b.build();
		b.country(Country.byCode("DE"));
		b.city("city_1");
		b.postcode("12");
		address2 = b.build();
		assertTrue("equal countries and cities: postcode '11' should be before postcode '12'", 
			address1.compareTo(address2) < 1);
		
		b.country(Country.byCode("DE"));
		b.city("city_1");
		b.postcode("11");
		b.street("street_1");
		address1 = b.build();
		b.country(Country.byCode("DE"));
		b.city("city_1");
		b.postcode("11");
		b.street("street_2");
		address2 = b.build();
		assertTrue("equal countries, cities and postcodes: 'street_1' should be before 'street_2'", 
			address1.compareTo(address2) < 1);
		
		b.country(Country.byCode("DE"));
		b.city("city_1");
		b.postcode("11");
		b.street("street_1");
		b.poBox("poBox_1");
		address1 = b.build();
		b.country(Country.byCode("DE"));
		b.city("city_1");
		b.postcode("11");
		b.street("street_1");
		b.poBox("poBox_2");
		address2 = b.build();
		assertTrue("equal countries, cities, postcodes and streets: 'poBox_1' " +
				"should be before 'poBox_2'", address1.compareTo(address2) < 1);
		
		b.country(Country.byCode("DE"));
		b.city("city_1");
		b.postcode("11");
		b.street("street_1");
		b.poBox("poBox_1");
		b.careOf("careOf_1");
		address1 = b.build();
		b.country(Country.byCode("DE"));
		b.city("city_1");
		b.postcode("11");
		b.street("street_1");
		b.poBox("poBox_1");
		b.careOf("careOf_2");
		address2 = b.build();
		assertTrue("equal countries, cities, postcodes, streets and poBoxes: 'careOf_1' " +
				"should be before 'careOf_2'", address1.compareTo(address2) < 1);
	}
}
