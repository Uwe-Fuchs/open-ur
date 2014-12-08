package org.openur.module.domain.userstructure.person;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.Status;

public class PersonTest
{
	private final Name NAME_1= Name.create(Gender.MALE, "James T.", "Kirk");
	private final Name NAME_2= Name.create(Gender.MALE, Title.DR, "Leonard", "McCoy");
	
	@Test(expected=NullPointerException.class)
	public void checkEmptyNumber()
	{
		new PersonBuilder(null, this.NAME_1);
	}
	
	@Test(expected=NullPointerException.class)
	public void checkEmptyName()
	{
		new PersonBuilder("123abc", null);
	}

	@Test
	public void testCompareTo()
	{
		Person p1 = new PersonBuilder("123abc", this.NAME_1)
				.build();
		Person p2 = new PersonBuilder("123abc", this.NAME_2)
				.build();
		assertTrue("different names", p1.compareTo(p2) < 0);

		p1 = new PersonBuilder("123abc", this.NAME_1)
				.emailAddress(EMailAddress.create("james.kirk@enterprise.uss"))
				.build();
		p2 = new PersonBuilder("123abc", this.NAME_1)
				.emailAddress(EMailAddress.create("james.kirk@home.uss"))
				.build();
		assertTrue("same names, different email-addresses", p1.compareTo(p2) < 0);
		
		p1 = new PersonBuilder("123abc", this.NAME_1)
			.build();
		p2 = new PersonBuilder("456xyz", this.NAME_2)
			.build();
		
		assertTrue("different personal numbers", p1.compareTo(p2) < 0);
		
		p2 = new PersonBuilder("123abc", this.NAME_1)
			.status(Status.INACTIVE)
			.build();
		assertTrue("same personal numbers, but different status", p1.compareTo(p2) < 0);
	
		p2 = new PersonBuilder("123abc", this.NAME_1)
			.build();
		assertTrue("same personal numbers, same status", p1.compareTo(p2) == 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateWithEmptyAppsList()
	{
		new PersonBuilder("123abc", this.NAME_1)
			.applications(new ArrayList<OpenURApplication>(0))
			.build();
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateWithNullMembers()
	{
		new PersonBuilder("123abc", this.NAME_1)
		.addApplication(null)
		.build();
	}
}
