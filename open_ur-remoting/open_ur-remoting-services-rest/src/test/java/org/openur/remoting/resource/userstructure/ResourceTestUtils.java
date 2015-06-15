package org.openur.remoting.resource.userstructure;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Address.AddressBuilder;
import org.openur.module.domain.userstructure.Country;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.PersonBuilder;

final class ResourceTestUtils
{
	static final String UUID_1 = UUID.randomUUID().toString();
	static final String NO_123 = "123";
	static final IPerson PERSON_1;
	static final Name name;
	static final Address address;
	static final Set<OpenURApplication> applications;

	static
	{
		name = Name.create(Gender.MALE, "Uwe", "Fuchs");

		address = new AddressBuilder("11")
			.country(Country.byCode("DE"))
			.city("city_1")
			.street("street_1")
			.streetNo("11")
			.poBox("poBox_1")
			.build();
		
		OpenURApplication app1 = new OpenURApplicationBuilder("app1").build();
		OpenURApplication app2 = new OpenURApplicationBuilder("app2").build();
		applications = new HashSet<>(Arrays.asList(app1, app2));

		PERSON_1 = new PersonBuilder(NO_123, name)
			.identifier(UUID_1)
			.creationDate(LocalDateTime.now())
			.status(Status.ACTIVE)
			.emailAddress(EMailAddress.create("office@uwefuchs.com"))
			.phoneNumber("0049123456789")
			.faxNumber("0049987654321")
			.mobileNumber("0049111222333")
			.homeAddress(address)
			.homePhoneNumber("0049444555666")
			.homeEmailAddress(EMailAddress.create("home@uwefuchs.com"))
			.applications(applications)
			.build();
	}
}
