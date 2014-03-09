package org.openur.module.domain.userstructure.user.person;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.Status;

public class PersonTest
{

	@Before
	public void setUp()
		throws Exception
	{
	}
	
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
		PersonBuilder pb = new PersonBuilder(UUID.randomUUID().toString(), "username", "password");
		pb.number("123abc");
		pb.status(Status.ACTIVE);
		pb.name(Name.create(Gender.MALE, "Uwe", "Fuchs"));
		pb.emailAdress(new EMailAddress("mail@uwefuchs.de"));
		Person p1 = new Person(pb);
		
		pb = new PersonBuilder(UUID.randomUUID().toString(), "username2", "password2");
		pb.number("456xyz");
		pb.name(Name.create(Gender.FEMALE, "Angela", "Merkel"));
		Person p2 = new Person(pb);
		assertTrue("different names", p1.compareTo(p2) < 0);
		
		pb = new PersonBuilder(UUID.randomUUID().toString(), "username3", "password3");
		pb.number("456xyz");
		pb.name(Name.create(Gender.MALE, "Arne", "Fuchs"));
		p2 = new Person(pb);		
		assertTrue("same last names, but different first names", p1.compareTo(p2) > 0);

		pb.name(Name.create(Gender.MALE, "Uwe", "Fuchs"));
		pb.emailAdress(new EMailAddress("fuchsuwe@gmx.de"));
		p2 = new Person(pb);		
		assertTrue("same names, different email-addresses", p1.compareTo(p2) > 0);
	}
}
