package org.openur.remoting.xchange.xml.mapping;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.Address.AddressBuilder;
import org.openur.module.domain.userstructure.Country;
import org.openur.remoting.xchange.xml.representations.XmlAddress;

public class XmlAddressMapper
{
	public static XmlAddress mapFromImmutable(Address immutable)
	{
    XmlAddress address = new XmlAddress(
					immutable.getPostcode(), 
					immutable.getCountry() != null ? immutable.getCountry().getCountryCode() : null);
	
		address.setIdentifier(immutable.getIdentifier());
		address.setCareOf(immutable.getCareOf());
		address.setCity(immutable.getCity());
		address.setPoBox(immutable.getPoBox());
		address.setStreet(immutable.getStreet());
		address.setStreetNo(immutable.getStreetNo());
		
		return AbstractXmlMapper.mapFromImmutable(immutable, address);
	}

	public static Address mapFromXmlRepresentation(XmlAddress xmlRepresentation)
	{    
		AddressBuilder immutableBuilder = new AddressBuilder(xmlRepresentation.getPostcode());
		
		immutableBuilder = immutableBuilder
				.careOf(xmlRepresentation.getCareOf())
				.city(xmlRepresentation.getCity())
				.poBox(xmlRepresentation.getPoBox())
				.street(xmlRepresentation.getStreet())
				.streetNo(xmlRepresentation.getStreetNo())
				.country(StringUtils.isNotEmpty(xmlRepresentation.getCountryCode()) ? Country.byCode(xmlRepresentation.getCountryCode()) : null);
		
		immutableBuilder = AbstractXmlMapper.mapFromXmlRepresentation(immutableBuilder, xmlRepresentation);
		
		return immutableBuilder.build();
	}

	public static boolean immutableEqualsToXmlRepresentation(Address immutable, XmlAddress xmlRepresentation)
	{		
		return new EqualsBuilder()
				.append(immutable.getCareOf(), xmlRepresentation.getCareOf())
				.append(immutable.getCity(), xmlRepresentation.getCity())
				.append(immutable.getCountry() != null ? immutable.getCountry().getCountryCode() : null, xmlRepresentation.getCountryCode())
				.append(immutable.getPoBox(), xmlRepresentation.getPoBox())
				.append(immutable.getPostcode(), xmlRepresentation.getPostcode())
				.append(immutable.getStreet(), xmlRepresentation.getStreet())
				.append(immutable.getStreetNo(), xmlRepresentation.getStreetNo())
				.isEquals();
	}
}
