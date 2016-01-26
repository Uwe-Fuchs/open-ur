package org.openur.module.domain.userstructure.person;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.util.data.Gender;
import org.openur.module.util.data.Status;
import org.openur.module.util.data.Title;

public class PersonTest
{
	private final Name NAME_1 = Name.create(Gender.MALE, "James T.", "Kirk");
	private final Name NAME_2 = Name.create(Gender.MALE, Title.DR, "Leonard", "McCoy");

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

	@Test
	public void testIsInApplication()
	{
		OpenURApplication app1 = new OpenURApplicationBuilder("app1").build();
		OpenURApplication app2 = new OpenURApplicationBuilder("app2").build();
		
		Person person = new PersonBuilder("123abc", this.NAME_1)
			.addApplication(app1)
			.addApplication(app2)
			.build();
		
		assertTrue(person.isInApplication(app1.getApplicationName()));
		assertTrue(person.isInApplication(app2.getApplicationName()));
		
		OpenURApplication app3 = new OpenURApplicationBuilder("app3").build();
		
		assertFalse(person.isInApplication(app3.getApplicationName()));
	}

	@Test(expected=NullPointerException.class)
	public void testIsInApplicationEmptyApp()
	{
		Person p = new PersonBuilder("123abc", this.NAME_1)
			.build();
		p.isInApplication(null);
	}
}
