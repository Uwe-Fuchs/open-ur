package org.openur.module.domain.userstructure.person;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.Status;

public class PersonTest
{
	@Test(expected=NullPointerException.class)
	public void checkEmptyID()
	{
		new PersonBuilder(null, Name.create(Gender.MALE, "Uwe", "Fuchs"));
	}
	
	@Test(expected=NullPointerException.class)
	public void checkEmptyName()
	{
		new PersonBuilder(null);
	}

	@Test
	public void testCompareTo()
	{
		PersonBuilder pb = new PersonBuilder(UUID.randomUUID().toString(), Name.create(Gender.MALE, "Uwe", "Fuchs"))
			.number("123abc")
			.status(Status.ACTIVE)
			.emailAddress(EMailAddress.create("info@uwefuchs.com"));
		Person p1 = pb.build();
		
		pb = new PersonBuilder(UUID.randomUUID().toString(), Name.create(Gender.FEMALE, Title.DR, "Angela", "Merkel"))
			.number("456xyz");
		Person p2 = pb.build();
		assertTrue("different names", p1.compareTo(p2) < 0);
		
		pb = new PersonBuilder(Name.create(Gender.MALE, "Arne", "Fuchs"))
			.number("456xyz");
		p2 = pb.build();	
		assertTrue("same last names, but different first names", p1.compareTo(p2) > 0);

		pb = new PersonBuilder(Name.create(Gender.MALE, "Uwe", "Fuchs"))
			.emailAddress(EMailAddress.create("fuchsuwe@gmx.de"));
		p2 = pb.build();
		assertTrue("same names, different email-addresses", p1.compareTo(p2) > 0);
		
		pb = new PersonBuilder(UUID.randomUUID().toString(), Name.create(Gender.MALE, "Uwe", "Fuchs"))
			.number("456xyz")
			.status(Status.ACTIVE);
		p1 = pb.build();
		
		PersonSimpleBuilder psb = new PersonSimpleBuilder()
			.number("123abc")
			.status(Status.ACTIVE);
		PersonSimple p3 = psb.build();
		assertTrue("p3 should be before p1 because of personal-number", p1.compareTo(p3) > 0);
	}
}
