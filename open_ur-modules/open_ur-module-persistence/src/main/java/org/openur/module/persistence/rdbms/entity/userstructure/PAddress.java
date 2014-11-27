package org.openur.module.persistence.rdbms.entity.userstructure;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.openur.module.domain.util.DefaultsUtil;
import org.openur.module.persistence.rdbms.entity.AbstractOpenUrPersistable;

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

	void setCareOf(String careOf)
	{
		this.careOf = careOf;
	}

	void setPoBox(String poBox)
	{
		this.poBox = poBox;
	}

	void setStreet(String street)
	{
		this.street = street;
	}

	void setStreetNo(String streetNo)
	{
		this.streetNo = streetNo;
	}

	void setPostcode(String postcode)
	{
		this.postcode = postcode;
	}

	void setCity(String city)
	{
		this.city = city;
	}

	void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
	}

	// constructor:
	PAddress()
	{
		super();
	}
}
