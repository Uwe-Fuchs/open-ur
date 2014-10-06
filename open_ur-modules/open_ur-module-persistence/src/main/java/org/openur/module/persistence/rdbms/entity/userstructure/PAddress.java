package org.openur.module.persistence.rdbms.entity.userstructure;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Country;
import org.openur.module.domain.userstructure.IAddress;
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

	public void setPostcode(String postcode)
	{
		this.postcode = postcode;
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

	public void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
	}

	PAddress()
	{
		super();
	}
	
	public static PAddress mapFromImmutable(IAddress immutable)
	{
		PAddress persistable = new PAddress();
		
		persistable.setCareOf(immutable.getCareOf());
		persistable.setCity(immutable.getCity());
		persistable.setPoBox(immutable.getPoBox());
		persistable.setPostcode(immutable.getPostcode());
		persistable.setStreet(immutable.getStreet());
		persistable.setStreetNo(immutable.getStreetNo());
		persistable.setCountryCode(immutable.getCountry() != null ? immutable.getCountry().getCountryCode() : null);
		
		return persistable;
	}
	
	public static Address mapFromEntity(PAddress persistable)
	{
		Address immutable = Address.builder()
				.careOf(persistable.getCareOf())
				.city(persistable.getCity())
				.poBox(persistable.getPoBox())
				.postcode(persistable.getPostcode())
				.street(persistable.getStreet())
				.streetNo(persistable.getStreetNo())
				.country(StringUtils.isNotEmpty(persistable.getCountryCode()) ? Country.byCode(persistable.getCountryCode()) : null)
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate())
				.build();
		
		return immutable;
	}
}
