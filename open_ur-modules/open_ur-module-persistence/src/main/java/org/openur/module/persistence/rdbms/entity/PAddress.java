package org.openur.module.persistence.rdbms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.util.DefaultsUtil;

@Entity(name="ADDRESS")
public class PAddress
	extends AbstractOpenUrPersistable
{
	private static final long serialVersionUID = 2608307202913998424L;

	// properties:
	@Column(name="CARE_OF")
	private String careOf;
	
	@Column(name="PO_BOX")
	private String poBox;
	
	@Column(name="STREET")
	private String street;
	
	@Column(name="STREET_NO")
	private String streetNo;
	
	@Column(name="POST_CODE", nullable=false)
	private String postcode;
	
	@Column(name="CITY")
	private String city;
	
	@Column(name="COUNTRY_CODE", nullable=false)
	private String countryCode = DefaultsUtil.getDefaultCountryCode();

	// accessors:
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

	public String getCountryCode()
	{
		return countryCode;
	}

	public void setCareOf(String careOf)
	{
		this.careOf = careOf;
	}

	public void setPoBox(String poBox)
	{
		this.poBox = poBox;
	}

	public void setStreet(String street)
	{
		this.street = street;
	}

	public void setStreetNo(String streetNo)
	{
		this.streetNo = streetNo;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public void setCountryCode(String countryCode)
	{
		Validate.notEmpty(countryCode, "country-code must not be empty!");		
		this.countryCode = countryCode;
	}

	// constructors:
	public PAddress(String postcode)
	{
		super();
		
		Validate.notEmpty(postcode, "post-code must not be empty!");	
		this.postcode = postcode;
	}
	
	@SuppressWarnings("unused")
	private PAddress()
	{
		// jpa
		super();
	}
}
