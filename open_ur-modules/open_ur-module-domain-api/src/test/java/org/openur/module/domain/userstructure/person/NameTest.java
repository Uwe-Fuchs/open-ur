package org.openur.module.domain.userstructure.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Title;

public class NameTest
{
	@Test
	public void testToString()
	{
		Name name = Name.create(Gender.MALE, "Uwe", "Fuchs");
		assertEquals("Uwe Fuchs", name.getFirstAndLastname());
	}

	@Test
	public void testGetFirstAndLastname()
	{
		Name name = Name.create(Gender.MALE, "Uwe", "Fuchs");
		assertEquals("Uwe Fuchs", name.getFirstAndLastname());
	}

	@Test
	public void testGetFullNameLocaleGermany()
	{
		Locale.setDefault(Locale.GERMAN);

		Name name = Name.create(Gender.MALE, "Uwe", "Fuchs");
		assertEquals("Herr Uwe Fuchs", name.getFullNameWithTitle());

		name = Name.create(Gender.MALE, Title.DR, "Erwin", "Markus");
		assertEquals("Herr Dr. Erwin Markus", name.getFullNameWithTitle());

		name = Name.create(Gender.FEMALE, Title.DR, "Angela", "Merkel");
		assertEquals("Frau Dr. Angela Merkel", name.getFullNameWithTitle());

		name = Name.create(Gender.FEMALE, Title.PROF, "Jutta", "Almendinger");
		assertEquals("Frau Jutta Almendinger", name.getFullNameWithTitle());
	}

	@Test
	public void testGetFullNameLocaleFrance()
	{
		Locale.setDefault(Locale.FRENCH);

		Name name = Name.create(Gender.MALE, "Uwe", "Fuchs");
		assertEquals("M. Uwe Fuchs", name.getFullNameWithTitle());

		name = Name.create(Gender.FEMALE, Title.DR, "Angela", "Merkel");
		assertEquals("Mme Dr. Angela Merkel", name.getFullNameWithTitle());

		name = Name.create(Gender.FEMALE, Title.PROF, "Christine", "Lagarde");
		assertEquals("Mme Christine Lagarde", name.getFullNameWithTitle());
	}

	@Test
	public void testGetFullNameFallbackLocale()
	{
		Locale.setDefault(Locale.ENGLISH);

		Name name = Name.create(Gender.MALE, "Uwe", "Fuchs");
		assertEquals("Mr. Uwe Fuchs", name.getFullNameWithTitle());

		name = Name.create(Gender.MALE, Title.DR, "Leonard", "McCoy");
		assertEquals("Mr. Dr. Leonard McCoy", name.getFullNameWithTitle());

		name = Name.create(Gender.MALE, Title.PROF, "Albert", "Einstein");
		assertEquals("Mr. Albert Einstein", name.getFullNameWithTitle());
	}

	@Test
	public void testCompareTo()
	{
		Name name1 = Name.create(Gender.MALE, "Heinz", "Meier");
		Name name2 = Name.create(Gender.MALE, "Jim-Bob", "Walton");
		assertTrue("name1 should be before name2", name1.compareTo(name2) < 1);
		
		name1 = Name.create(Gender.MALE, "Jim-Bob", "Meier");
		name2 = Name.create(Gender.MALE, "Jim-Bob", "Walton");
		assertTrue("name1 should be before name2", name1.compareTo(name2) < 1);
		
		name1 = Name.create(Gender.MALE, "John-Boy", "Walton");
		assertTrue("name2 should be before name1", name1.compareTo(name2) > 1);
		
		name1 = Name.create(Gender.MALE, "Andrea", "Bocelli");
		name2 = Name.create(Gender.FEMALE, "Andrea", "Bocelli");
		assertTrue("name1 should be before name2", name1.compareTo(name2) < 1);
		
		name1 = Name.create(Gender.MALE, "Andrea", "Bocelli");
		name2 = Name.create(Gender.MALE, Title.DR, "Andrea", "Bocelli");
		assertTrue("name1 should be before name2", name1.compareTo(name2) < 1);
	}
}
