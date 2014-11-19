package org.openur.module.persistence.rdbms.entity.userstructure;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Country;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.Address.AddressBuilder;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.domain.userstructure.person.Title;
import org.openur.module.persistence.rdbms.entity.application.ApplicationMapper;
import org.openur.module.persistence.rdbms.entity.application.PApplication;

public class PersonMapperTest
{
	private Name name;
	private Address address;
	private PAddress pAddress;
	private OpenURApplication app1;
	private OpenURApplication app2;
	private Set<OpenURApplication> applications;
	private List<PApplication> pApplications;
	
	@Before
	public void setUp()
		throws Exception
	{
		this.name = Name.create(Gender.MALE, "Uwe", "Fuchs");
		
		this.address = new AddressBuilder()
			.country(Country.byCode("DE"))
			.city("city_1")
			.postcode("11")
			.street("street_1")
			.streetNo("11")
			.poBox("poBox_1")	
			.build();
		
		this.pAddress = AddressMapper.mapFromImmutable(address);
		
		app1 = new OpenURApplicationBuilder("app1").build();
		app2 = new OpenURApplicationBuilder("app2").build();
		this.applications = new HashSet<>(Arrays.asList(app1, app2));
		
		PApplication pApp1 = ApplicationMapper.mapFromImmutable(app1);
		PApplication pApp2 = ApplicationMapper.mapFromImmutable(app2);
		this.pApplications = Arrays.asList(pApp1, pApp2);
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
		assertTrue(PersonMapper.immutableEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapFromEntity()
	{
		PPerson persistable = new PPerson();
		
		persistable.setGender(Gender.MALE);
		persistable.setTitle(Title.NONE);
		persistable.setFirstName("Uwe");
		persistable.setLastName("Fuchs");
		persistable.setNumber("123abc");
		persistable.setStatus(Status.ACTIVE);
		persistable.setEmailAdress("office@uwefuchs.com");
		persistable.setEmployeeNumber("789xyz");
		persistable.setPhoneNumber("0049123456789");
		persistable.setFaxNumber("0049987654321");
		persistable.setMobileNumber("0049111222333");
		persistable.setHomePhoneNumber("0049444555666");
		persistable.setHomeEmailAdress("home@uwefuchs.com");
		persistable.setHomeAddress(pAddress);
		persistable.setApplications(pApplications);
		
		Person immutable = PersonMapper.mapFromEntity(persistable);
		
		assertNotNull(immutable);
		assertTrue(PersonMapper.immutableEqualsToEntity(immutable, persistable));		
	}
}
