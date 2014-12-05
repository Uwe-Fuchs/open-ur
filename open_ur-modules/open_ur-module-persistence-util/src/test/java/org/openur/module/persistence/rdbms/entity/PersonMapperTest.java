package org.openur.module.persistence.rdbms.entity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Before;
import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Address.AddressBuilder;
import org.openur.module.domain.userstructure.Country;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.domain.userstructure.person.Title;

public class PersonMapperTest
{
	private Name name;
	private Address address;
	private PAddress pAddress;
	private Set<OpenURApplication> applications;
	private Set<PApplication> pApplications;
	
	@Before
	public void setUp()
		throws Exception
	{
		this.name = Name.create(Gender.MALE, "Uwe", "Fuchs");
		
		this.address = new AddressBuilder("11")
			.country(Country.byCode("DE"))
			.city("city_1")
			.street("street_1")
			.streetNo("11")
			.poBox("poBox_1")	
			.build();
		
		this.pAddress = AddressMapper.mapFromImmutable(this.address);
		
		OpenURApplication app1 = new OpenURApplicationBuilder("app1").build();
		OpenURApplication app2 = new OpenURApplicationBuilder("app2").build();
		this.applications = new HashSet<>(Arrays.asList(app1, app2));
		
		PApplication pApp1 = ApplicationMapper.mapFromImmutable(app1);
		PApplication pApp2 = ApplicationMapper.mapFromImmutable(app2);
		this.pApplications = new HashSet<>(Arrays.asList(pApp1, pApp2));
	}

	@Test
	public void testMapFromImmutable()
	{
		PersonBuilder pb = new PersonBuilder("123abc", this.name)
			.status(Status.ACTIVE)
			.emailAddress(EMailAddress.create("office@uwefuchs.com"))
			.phoneNumber("0049123456789")
			.faxNumber("0049987654321")
			.mobileNumber("0049111222333")
			.homeAddress(this.address)
			.homePhoneNumber("0049444555666")
			.homeEmailAddress(EMailAddress.create("home@uwefuchs.com"))
			.apps(this.applications);

		Person immutable = pb.build();
		PPerson persistable = PersonMapper.mapFromImmutable(immutable);
		
		assertNotNull(persistable);
		assertTrue(PersonMapperTest.immutableEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapFromEntity()
	{
		PPerson persistable = new PPerson();

		persistable.setEmployeeNumber("123abc");
		persistable.setGender(Gender.MALE);
		persistable.setTitle(Title.NONE);
		persistable.setFirstName("Uwe");
		persistable.setLastName("Fuchs");
		persistable.setStatus(Status.ACTIVE);
		persistable.setEmailAdress("office@uwefuchs.com");
		persistable.setPhoneNumber("0049123456789");
		persistable.setFaxNumber("0049987654321");
		persistable.setMobileNumber("0049111222333");
		persistable.setHomePhoneNumber("0049444555666");
		persistable.setHomeEmailAdress("home@uwefuchs.com");
		persistable.setHomeAddress(pAddress);
		persistable.setApplications(pApplications);
		
		Person immutable = PersonMapper.mapFromEntity(persistable);
		
		assertNotNull(immutable);
		assertTrue(PersonMapperTest.immutableEqualsToEntity(immutable, persistable));		
	}

	public static boolean immutableEqualsToEntity(Person immutable, PPerson persistable)
	{
		if (!AbstractEntityMapperTest.immutableEqualsToEntityUserStructureBase(immutable, persistable))
		{
			return false;
		}
		
		for (PApplication app : persistable.getApplications())
		{
			if (!immutable.isInApplication(app.getApplicationName()))
			{
				return false;
			}
		}
		
		if ((immutable.getHomeAddress() != null || persistable.getHomeAddress() != null)
			&& !AddressMapperTest.immutableEqualsToEntity(immutable.getHomeAddress(), persistable.getHomeAddress()))
		{
			return false;
		}
		
		return new EqualsBuilder()
				.append(immutable.getEmployeeNumber(), persistable.getEmployeeNumber())
				.append(immutable.getName().getTitle(), persistable.getTitle())
				.append(immutable.getName().getFirstName(), persistable.getFirstName())
				.append(immutable.getName().getLastName(), persistable.getLastName())
				.append(immutable.getName().getGender(), persistable.getGender())
				.append(immutable.getPhoneNumber(), persistable.getPhoneNumber())
				.append(immutable.getFaxNumber(), persistable.getFaxNumber())
				.append(immutable.getMobileNumber(), persistable.getMobileNumber())
				.append(immutable.getHomePhoneNumber(), persistable.getHomePhoneNumber())
				.append(immutable.getEmailAddress() != null ? immutable.getEmailAddress().getAsPlainEMailAddress() : null, 
						persistable.getEmailAddress())
				.append(immutable.getHomeEmailAddress() != null ? immutable.getHomeEmailAddress().getAsPlainEMailAddress() : null, 
						persistable.getHomeEmailAddress())
				.isEquals();
	}
}
