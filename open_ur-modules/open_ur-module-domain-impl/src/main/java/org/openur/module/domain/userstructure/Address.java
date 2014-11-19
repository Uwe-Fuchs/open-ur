package org.openur.module.domain.userstructure;

import org.openur.module.domain.IdentifiableEntityBuilder;
import org.openur.module.domain.IdentifiableEntityImpl;

public class Address
	extends IdentifiableEntityImpl
	implements IAddress
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
		super(b);
		
		this.careOf = b.careOf;
		this.poBox = b.poBox;
		this.street = b.street;
		this.streetNo = b.streetNo;
		this.city = b.city;
		this.postcode = b.postcode;
		this.country = b.country;
	}

	// accessors:
	@Override
	public String getCareOf()
	{
		return careOf;
	}

	@Override
	public String getPoBox()
	{
		return poBox;
	}

	@Override
	public String getStreet()
	{
		return street;
	}

	@Override
	public String getStreetNo()
	{
		return streetNo;
	}

	@Override
	public String getPostcode()
	{
		return postcode;
	}

	@Override
	public String getCity()
	{
		return city;
	}

	@Override
	public Country getCountry()
	{
		return country;
	}

	// builder-class:
	public static class AddressBuilder
		extends IdentifiableEntityBuilder<AddressBuilder>
	{
		// properties:
		private String careOf = null;
		private String poBox = null;
		private String street = null;
		private String streetNo = null;
		private String city = null;
		private String postcode = null;
		private Country country = null;
		
		// constructors:
		public AddressBuilder()
		{
			super();
		}
		
		public AddressBuilder(String identifier)
		{
			super(identifier);
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
