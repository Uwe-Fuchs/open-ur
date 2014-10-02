package org.openur.module.persistence.rdbms.entity.userstructure;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Country;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.Address.AddressBuilder;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;

public class PPersonTest
{
	private Name name;
	private Address address;
	
	@Before
	public void setUp()
		throws Exception
	{
		this.name = Name.create(Gender.MALE, "Uwe", "Fuchs");
		
		AddressBuilder b = Address.builder();		
		b.country(Country.byCode("DE"));
		b.city("city_1");
		b.postcode("11");
		b.street("street_1");
		b.streetNo("11");
		b.poBox("poBox_1");	
		this.address = b.build();
	}

	@Test
	public void testMapFromImmutable()
	{
		PersonBuilder pb = new PersonBuilder(this.name)
			.number("123abc")
			.status(Status.ACTIVE)
			.emailAdress(new EMailAddress("office@uwefuchs.com"))
			.employeeNumber("789xyz")
			.phoneNumber("0049123456789")
			.faxNumber("0049987654321")
			.mobileNumber("0049111222333")
			.homeAddress(this.address)
			.homePhoneNumber("0049444555666")
			.homeEmailAdress(new EMailAddress("home@uwefuchs.com"));

		Person person = pb.build();
		PPerson pPerson = PPerson.mapFromImmutable(person);
		
		assertNotNull(pPerson);
		assertEquals(person.getNumber(), pPerson.getNumber());
	}

	@Test
	public void testMapFromEntity()
	{
		fail("Not yet implemented");
	}
}
