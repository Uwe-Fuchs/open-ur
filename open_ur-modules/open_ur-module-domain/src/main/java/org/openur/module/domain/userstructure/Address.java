package org.openur.module.domain.userstructure;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.GraphNode;

public class Address
	extends GraphNode
	implements Comparable<Address>
{
	private static final long serialVersionUID = -6280694056713764480L;
	
	// properties:
	private final String careOf;
	private final String poBox;
	private final String street;
	private final String number;
	private final String postcode;
	private final String city;
	private final Country country;

	// constructor:
	private Address(AddressBuilder b)
	{
		super();
		this.careOf = b.careOf;
		this.poBox = b.poBox;
		this.street = b.street;
		this.number = b.number;
		this.city = b.city;
		this.postcode = b.postcode;
		this.country = b.country;
	}

	// accessors:
	public static AddressBuilder builder()
	{
		return new AddressBuilder();
	}

	public String getCareOf()
	{
		return careOf;
	}

	public String getPoBox()
	{
		return poBox;
	}

	public String getStreet()
	{
		return street;
	}

	public String getNumber()
	{
		return number;
	}

	public String getPostcode()
	{
		return postcode;
	}

	public String getCity()
	{
		return city;
	}

	public Country getCountry()
	{
		return country;
	}
	
	// operations:
	@Override
	public boolean equals(Object obj)
	{
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int compareTo(Address o)
	{
		int comparison = new CompareToBuilder()
											.append(this.getCountry(), o.getCountry())
											.append(this.getCity(), o.getCity())
											.append(this.getPostcode(), o.getPostcode())
											.append(this.getStreet(), o.getStreet())
											.append(this.getPoBox(), o.getPoBox())
											.append(this.getCareOf(), o.getCareOf())
											.toComparison();
		
		if (comparison != 0)
		{
			return comparison;
		}
		
		return CompareToBuilder.reflectionCompare(this, o);
	}

	// builder-class:
	public static class AddressBuilder
	{
		// properties:
		private String careOf = null;
		private String poBox = null;
		private String street = null;
		private String number = null;
		private String city = null;
		private String postcode = null;
		private Country country = null;
		
		// constructor:
		private AddressBuilder() {			
		}

		// builder-methods:
		public Address.AddressBuilder careOf(String careOf)
		{
			this.careOf = careOf;
			return this;
		}

		public Address.AddressBuilder poBox(String poBox)
		{
			this.poBox = poBox;
			return this;
		}

		public Address.AddressBuilder street(String street)
		{
			this.street = street;
			return this;
		}

		public Address.AddressBuilder number(String number)
		{
			this.number = number;
			return this;
		}

		public Address.AddressBuilder city(String city)
		{
			this.city = city;
			return this;
		}

		public Address.AddressBuilder postcode(String postcode)
		{
			this.postcode = postcode;
			return this;
		}

		public Address.AddressBuilder country(Country country)
		{
			this.country = country;
			return this;
		}

		// build-operation:
		public Address build()
		{
			return new Address(this);
		}
	}
}
