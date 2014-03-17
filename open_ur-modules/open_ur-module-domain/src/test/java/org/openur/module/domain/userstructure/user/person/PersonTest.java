package org.openur.module.domain.userstructure.user.person;

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
		new PersonBuilder(null, "username", "password");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkEmptyUserName()
	{
		new PersonBuilder(UUID.randomUUID().toString(), "", "password");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkEmptyPassword()
	{
		new PersonBuilder(UUID.randomUUID().toString(), "username", "");
	}

	@Test
	public void testCompareTo()
	{
		PersonBuilder pb = new PersonBuilder(UUID.randomUUID().toString(), "username", "password")
			.number("123abc")
			.status(Status.ACTIVE)
			.name(Name.create(Gender.MALE, "Uwe", "Fuchs"))
			.emailAdress(new EMailAddress("mail@uwefuchs.de"));
		IPerson p1 = new Person(pb);
		
		pb = new PersonBuilder(UUID.randomUUID().toString(), "username2", "password2")
			.number("456xyz")
			.name(Name.create(Gender.FEMALE, "Angela", "Merkel"));
		IPerson p2 = new Person(pb);
		assertTrue("different names", p1.compareTo(p2) < 0);
		
		pb = new PersonBuilder("username3", "password3")
			.number("456xyz")
			.name(Name.create(Gender.MALE, "Arne", "Fuchs"));
		p2 = new Person(pb);		
		assertTrue("same last names, but different first names", p1.compareTo(p2) > 0);

		pb.name(Name.create(Gender.MALE, "Uwe", "Fuchs"))
			.emailAdress(new EMailAddress("fuchsuwe@gmx.de"));
		p2 = new Person(pb);		
		assertTrue("same names, different email-addresses", p1.compareTo(p2) > 0);
		
		pb = new PersonBuilder(UUID.randomUUID().toString(), "username2", "password2")
			.number("456xyz")
			.status(Status.ACTIVE);
		p1 = new Person(pb);
		
		PersonSimpleBuilder psb = new PersonSimpleBuilder("username1", "password1")
			.number("123abc")
			.status(Status.ACTIVE);
		p2 = new PersonSimple(psb);
		assertTrue("p2 should be before p1 because of personal-number", p1.compareTo(p2) > 0);
	}
}
