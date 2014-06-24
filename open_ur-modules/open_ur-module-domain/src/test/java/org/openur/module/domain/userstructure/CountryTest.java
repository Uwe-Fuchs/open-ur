package org.openur.module.domain.userstructure;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Locale;

import org.junit.Test;

public class CountryTest
{
	@Test
	public void testEqualsObject()
	{
		Country c1 = Country.byLocale(Locale.GERMANY);
		Country c2 = Country.byLocale(new Locale("de", "DE"));
		assertTrue(c1.equals(c2));
		
		c2 = Country.byLocale(new Locale("de", "AT"));
		assertFalse(c1.equals(c2));
	}

	@Test
	public void testCompareTo()
	{
		Country c1 = Country.byLocale(Locale.GERMANY);
		Country c2 = Country.byLocale(new Locale("de", "DE"));
		assertTrue(c1.compareTo(c2) == 0);
		
		c2 = Country.byLocale(new Locale("de", "AT"));
		assertTrue(c1.compareTo(c2) > 0);
		
		Country c3 = Country.byLocale(Locale.FRANCE);
		assertTrue(c1.compareTo(c3) < 0);
		assertTrue(c2.compareTo(c3) < 0);
	}

	@Test
	public void testByCodeStringString()
	{
		Country c = Country.byCode("AT", "DE");
		assertEquals(c.getCountryCode(), "AT");
		
		c = Country.byCode("de-AT", "DE");
		assertEquals(c.getCountryCode(), "DE");
	}

	@Test
	public void testByLocale()
	{
		Country c = Country.byLocale(Locale.GERMANY);
		assertEquals(c.getCountryCode(), "DE");
		
		c = Country.byLocale(new Locale("de", "AT"));
		assertEquals(c.getCountryCode(), "AT");
	}

	@Test
	public void testGetAll()
	{
		List<Country> cl = Country.getAll();
		assertEquals(cl.size(), Locale.getISOCountries().length);
	}

	@Test
	public void testGetLocales()
	{
		Country c = Country.byCode("DE");
		List<Locale> locales = c.getLocales();
		assertTrue(locales.size() == 1);
		assertEquals(locales.get(0), Locale.GERMANY);
		
		c = Country.byCode("CH");
		locales = c.getLocales();
		assertTrue(locales.size() == 3);
		assertTrue(locales.contains(new Locale("de", "CH")));
		assertTrue(locales.contains(new Locale("it", "CH")));
		assertTrue(locales.contains(new Locale("fr", "CH")));
	}

	@Test
	public void testGetLabel()
	{
		Country c = Country.byCode("DE");
		assertEquals(c.getLabel(Locale.FRANCE), "Allemagne");
		assertEquals(c.getLabel(Locale.ENGLISH), "Germany");
		assertEquals(c.getLabel(Locale.GERMAN), "Deutschland");
	}
}
