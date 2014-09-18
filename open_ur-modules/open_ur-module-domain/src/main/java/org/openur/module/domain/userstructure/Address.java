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
	private final String streetNo;
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
		this.streetNo = b.streetNo;
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

	public String getStreetNo()
	{
		return streetNo;
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
		if (obj == null || !this.getClass().equals(obj.getClass()))
		{
			return false;
		}
		
		if (this == obj)
		{
			return true;
		}
		
		Address other = (Address) obj;
		
		return new EqualsBuilder()
											.append(this.getCountry(), other.getCountry())
											.append(this.getCity(), other.getCity())
											.append(this.getPostcode(), other.getPostcode())
											.append(this.getStreet(), other.getStreet())
											.append(this.getStreetNo(), other.getStreetNo())
											.append(this.getPoBox(), other.getPoBox())
											.isEquals();
	}

	@Override
	public int compareTo(Address other)
	{
		int comparison = new CompareToBuilder()
											.append(this.getCountry(), other.getCountry())
											.append(this.getCity(), other.getCity())
											.append(this.getPostcode(), other.getPostcode())
											.append(this.getStreet(), other.getStreet())
											.append(this.getStreetNo(), other.getStreetNo())
											.append(this.getPoBox(), other.getPoBox())
											.append(this.getCareOf(), other.getCareOf())
											.toComparison();
		
		if (comparison != 0)
		{
			return comparison;
		}
		
		return CompareToBuilder.reflectionCompare(this, other);
	}

	// builder-class:
	public static class AddressBuilder
	{
		// properties:
		private String careOf = null;
		private String poBox = null;
		private String street = null;
		private String streetNo = null;
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

		public Address.AddressBuilder streetNo(String streetNo)
		{
			this.streetNo = streetNo;
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
