package org.openur.module.domain.userstructure;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.LocaleUtils;
import org.openur.module.domain.GraphNode;

public class Country
	extends GraphNode
	implements Comparable<Country>
{
	private static final long serialVersionUID = 7863135465800262833L;

	private static final SortedSet<String> countryCodes = Collections.unmodifiableSortedSet(
		new TreeSet<String>(Arrays.asList(Locale.getISOCountries())));

	private final String countryCode;

	// constructor:
	private Country(String countryCode)
	{
		this.countryCode = countryCode.toUpperCase();
	}

	// factory-methods:
	public static Country byCode(String countryCode)
	{
		if (countryCode != null && countryCodes.contains(countryCode.toUpperCase()))
		{
			return new Country(countryCode);
		}

		return null;
	}

	public static Country byCode(String countryCode, String defaultCode)
	{
		Country c = byCode(countryCode);

		if (c != null)
		{
			return c;
		} else
		{
			return byCode(defaultCode);
		}
	}

	public static Country byLocale(Locale locale)
	{
		return byCode(locale.getCountry());
	}

	public static List<Country> getAll()
	{
		List<Country> result = new ArrayList<Country>();

		for (String countryCode : countryCodes)
		{
			result.add(Country.byCode(countryCode));
		}

		return result;
	}

	public static List<Country> getDisplayableCountries(final Locale locale)
	{
		List<Country> result = new ArrayList<Country>();

		for (Country country : getAll())
		{
			if (!country.getLocales().isEmpty())
			{
				Locale countryLocale = country.getLocales().get(0);

				if (countryLocale.getDisplayCountry(locale) != null)
				{
					result.add(country);
				}
			}
		}

		Collections.sort(result, new Comparator<Country>()
		{
			@Override
			public int compare(Country o1, Country o2)
			{
				return Collator.getInstance(locale).compare(o1.getLabel(locale), o2.getLabel(locale));
			}

			@Override
			public boolean equals(Object obj)
			{
				return this.equals(obj);
			}
		});

		return result;
	}

	public List<Locale> getLocales()
	{
		return LocaleUtils.languagesByCountry(countryCode);
	}

	public String getLabel(Locale locale)
	{
		List<Locale> localesFromCountryCode = getLocales();

		if (!localesFromCountryCode.isEmpty())
		{
			return localesFromCountryCode.get(0).getDisplayCountry(locale);
		}

		return this.countryCode;
	}

	public String getCountryCode()
	{
		return countryCode;
	}

	@Override
	public String toString()
	{
		return getCountryCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if ((null == obj) || (obj.getClass() != Country.class))
		{
			return false;
		}

		Country other = (Country) obj;

		return this.getCountryCode().equalsIgnoreCase(other.getCountryCode());
	}

	@Override
	public int compareTo(Country o)
	{
		// sort according to countryCode:
		return this.getCountryCode().compareToIgnoreCase(o.getCountryCode());
	}
}
