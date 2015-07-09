package org.openur.remoting.xchange.xml.representations;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.Validate;

@XmlType(name="address")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlAddress
	extends AbstractXmlRepresentation
{
	@XmlAttribute
	private String careOf;
	
	@XmlAttribute
	private String poBox;
	
	@XmlAttribute
	private String street;
	
	@XmlAttribute
	private String streetNo;
	
	@XmlAttribute(required = true)
	private String postcode;
	
	@XmlAttribute
	private String city;
	
	@XmlAttribute(required = true)
	private String countryCode;

	public String getCareOf()
	{
		return careOf;
	}

	public void setCareOf(String careOf)
	{
		this.careOf = careOf;
	}

	public String getPoBox()
	{
		return poBox;
	}

	public void setPoBox(String poBox)
	{
		this.poBox = poBox;
	}

	public String getStreet()
	{
		return street;
	}

	public void setStreet(String street)
	{
		this.street = street;
	}

	public String getStreetNo()
	{
		return streetNo;
	}

	public void setStreetNo(String streetNo)
	{
		this.streetNo = streetNo;
	}

	public String getPostcode()
	{
		return postcode;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getCountryCode()
	{
		return countryCode;
	}

	public XmlAddress(String postcode, String countryCode)
	{
		super();
		
		Validate.notEmpty(postcode, "post-code must not be empty!");
		Validate.notEmpty(countryCode, "country-code must not be empty!");	
		this.postcode = postcode;	
		this.countryCode = countryCode;
	}

	@SuppressWarnings("unused")
	private XmlAddress()
	{
		// JAXB
		super();
	}
}
