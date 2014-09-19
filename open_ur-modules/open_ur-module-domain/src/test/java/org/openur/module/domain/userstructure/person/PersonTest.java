package org.openur.module.domain.userstructure.person;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.domain.userstructure.person.PersonSimpleBuilder;

public class PersonTest
{
	private Name name;
	
	@Before
	public void setUp()
		throws Exception
	{
		this.name = Name.create(null, null, "Meier");
	}
	
	@Test(expected=NullPointerException.class)
	public void checkEmptyID()
	{
		new PersonBuilder(null, "username", "password", name);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkEmptyUserName()
	{
		new PersonBuilder(UUID.randomUUID().toString(), "", "password", name);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkEmptyPassword()
	{
		new PersonBuilder(UUID.randomUUID().toString(), "username", "", name);
	}
	
	@Test(expected=NullPointerException.class)
	public void checkEmptyName()
	{
		new PersonBuilder("username", "password", null);
	}

	@Test
	public void testCompareTo()
	{
		PersonBuilder pb = new PersonBuilder(UUID.randomUUID().toString(), "username", "password", name)
			.number("123abc")
			.status(Status.ACTIVE)
			.name(Name.create(Gender.MALE, "Uwe", "Fuchs"))
			.emailAdress(new EMailAddress("mail@uwefuchs.de"));
		IPerson p1 = pb.build();
		
		pb = new PersonBuilder(UUID.randomUUID().toString(), "username2", "password2", name)
			.number("456xyz")
			.name(Name.create(Gender.FEMALE, "Angela", "Merkel"));
		IPerson p2 = pb.build();
		assertTrue("different names", p1.compareTo(p2) < 0);
		
		pb = new PersonBuilder("username3", "password3", name)
			.number("456xyz")
			.name(Name.create(Gender.MALE, "Arne", "Fuchs"));
		p2 = pb.build();	
		assertTrue("same last names, but different first names", p1.compareTo(p2) > 0);

		pb.name(Name.create(Gender.MALE, "Uwe", "Fuchs"))
			.emailAdress(new EMailAddress("fuchsuwe@gmx.de"));
		p2 = pb.build();
		assertTrue("same names, different email-addresses", p1.compareTo(p2) > 0);
		
		pb = new PersonBuilder(UUID.randomUUID().toString(), "username2", "password2", name)
			.number("456xyz")
			.status(Status.ACTIVE);
		p1 = pb.build();
		
		PersonSimpleBuilder psb = new PersonSimpleBuilder("username1", "password1")
			.number("123abc")
			.status(Status.ACTIVE);
		p2 = psb.build();
		assertTrue("p2 should be before p1 because of personal-number", p1.compareTo(p2) > 0);
	}
}
