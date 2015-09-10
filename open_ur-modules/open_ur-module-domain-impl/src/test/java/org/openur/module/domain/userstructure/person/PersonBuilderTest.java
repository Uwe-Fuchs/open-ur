package org.openur.module.domain.userstructure.person;

import java.util.ArrayList;

import org.junit.Test;
import org.openur.module.domain.application.OpenURApplication;

public class PersonBuilderTest
{
	private final Name NAME_1 = Name.create(Gender.MALE, "James T.", "Kirk");
	
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
	
	@Test(expected=NullPointerException.class)
	public void testCreateWithEmptyNumber()
	{
		new PersonBuilder()
				.name(NAME_1)
				.build();
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateWithEmptyName()
	{
		new PersonBuilder()
				.number("someNumber")
				.build();
	}
	
	@Test(expected=NullPointerException.class)
	public void checkEmptyNumber()
	{
		new PersonBuilder(null, this.NAME_1)
				.build();
	}
	
	@Test(expected=NullPointerException.class)
	public void checkEmptyName()
	{
		new PersonBuilder("123abc", null)
				.build();
	}
}
