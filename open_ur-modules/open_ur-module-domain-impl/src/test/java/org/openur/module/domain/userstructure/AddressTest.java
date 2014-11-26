package org.openur.module.domain.userstructure;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Address.AddressBuilder;

public class AddressTest
{
	@Test
	public void testCompareTo()
	{
		Address address1 = new AddressBuilder("11")
				.city("city")
				.country(Country.byCode("DE"))
				.build();

		Address address2 = new AddressBuilder("11")
				.city("city")
				.country(Country.byCode("FR"))
				.build();

		assertTrue("'DE' should be before 'FR'", address1.compareTo(address2) < 1);
		
		
		address1 = new AddressBuilder("11")
				.city("city")
				.country(Country.byCode("DE"))
				.build();
		
		address2 = new AddressBuilder("12")
				.city("city")
				.country(Country.byCode("DE"))
				.build();

		assertTrue("equal countries and cities: postcode '11' should be before postcode '12'", address1.compareTo(address2) < 1);
		
		
		address1 = new AddressBuilder("11")
				.city("city_1")
				.country(Country.byCode("DE"))
				.build();
		
		address2 = new AddressBuilder("11")
				.city("city_2")
				.country(Country.byCode("DE"))
				.build();
		
		assertTrue("equal countries: 'city_1' should be before 'city_2'", address1.compareTo(address2) < 1);
		
		
		address1 = new AddressBuilder("11")
				.city("city_1")
				.country(Country.byCode("DE"))
				.street("street_1")
				.build();
		
		address2 = new AddressBuilder("11")
				.city("city_1")
				.country(Country.byCode("DE"))
				.street("street_2")
				.build();

		assertTrue("equal countries, cities and postcodes: 'street_1' should be before 'street_2'", address1.compareTo(address2) < 1);
		
		
		address1 = new AddressBuilder("11")
				.street("street_1")
				.city("city_1")
				.country(Country.byCode("DE"))
				.poBox("poBox_1")
				.build();
		
		address2 = new AddressBuilder("11")
				.street("street_1")
				.city("city_1")
				.country(Country.byCode("DE"))
				.poBox("poBox_2")
				.build();
		
		assertTrue("equal countries, cities, postcodes and streets: 'poBox_1' should be before 'poBox_2'", address1.compareTo(address2) < 1);
		
		
		address1 = new AddressBuilder("11")
				.street("street_1")
				.city("city_1")
				.poBox("poBox_1")
				.country(Country.byCode("DE"))
				.careOf("careOf_1")
				.build();
		
		address2 = new AddressBuilder("11")
				.street("street_1")
				.city("city_1")
				.poBox("poBox_1")
				.country(Country.byCode("DE"))
				.careOf("careOf_2")
				.build();

		assertTrue("equal countries, cities, postcodes, streets and poBoxes: 'careOf_1' should be before 'careOf_2'", address1.compareTo(address2) < 1);
	}
}
