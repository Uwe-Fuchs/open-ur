package org.openur.module.persistence.rdbms.entity.userstructure;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Country;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;

public class PersonMapperTest
{
	private Name name;
	private Address address;
	private OpenURApplication app1;
	private OpenURApplication app2;
	private Set<OpenURApplication> applications;
	
	@Before
	public void setUp()
		throws Exception
	{
		this.name = Name.create(Gender.MALE, "Uwe", "Fuchs");
		
		this.address = Address.builder()
			.country(Country.byCode("DE"))
			.city("city_1")
			.postcode("11")
			.street("street_1")
			.streetNo("11")
			.poBox("poBox_1")	
			.build();
		
		app1 = new OpenURApplicationBuilder("app1").build();
		app2 = new OpenURApplicationBuilder("app2").build();
		this.applications = new HashSet<>(Arrays.asList(app1, app2));
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
			.homeEmailAdress(new EMailAddress("home@uwefuchs.com"))
			.apps(this.applications);

		Person immutable = pb.build();
		PPerson persistable = PersonMapper.mapFromImmutable(immutable);
		
		assertNotNull(persistable);
		assertEquals(immutable.getNumber(), persistable.getNumber());
		assertEquals(immutable.getHomeEmailAddress().getAsPlainEMailAddress(), persistable.getHomeEmailAddress());
		assertTrue(immutable.getApplications().size() == 2);
		assertTrue(persistable.getApplications().size() == immutable.getApplications().size());
	}

//	@Test
//	public void testMapFromEntity()
//	{
//		fail("Not yet implemented");
//	}
}
