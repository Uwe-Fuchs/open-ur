package org.openur.remoting.xchange.xml.mapping;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.Address;
import org.openur.remoting.xchange.xml.representations.XmlAddress;

public class XmlAddressMapperTest
{
	@Test
	public void testMapFromImmutable()
	{
		XmlAddress xmlAddress = XmlAddressMapper.mapFromImmutable(TestObjectContainer.ADDRESS_1);

		assertTrue(immutableEqualsToXmlRepresentation(TestObjectContainer.ADDRESS_1, xmlAddress));
	}

	@Test
	public void testMapFromXmlRepresentation()
	{
		XmlAddress xmlAddress = new XmlAddress(
			TestObjectContainer.ADDRESS_1.getPostcode(), TestObjectContainer.ADDRESS_1.getCountry().getCountryCode());
		xmlAddress.setIdentifier(TestObjectContainer.ADDRESS_1.getIdentifier());
		xmlAddress.setCreationDate(new XmlDateConverter().convertDateTimeToXml(TestObjectContainer.ADDRESS_1.getCreationDate()));
		xmlAddress.setCareOf(TestObjectContainer.ADDRESS_1.getCareOf());
		xmlAddress.setCity(TestObjectContainer.ADDRESS_1.getCity());
		xmlAddress.setPoBox(TestObjectContainer.ADDRESS_1.getPoBox());
		xmlAddress.setStreet(TestObjectContainer.ADDRESS_1.getStreet());
		xmlAddress.setStreetNo(TestObjectContainer.ADDRESS_1.getStreetNo());
		
		Address address = XmlAddressMapper.mapFromXmlRepresentation(xmlAddress);
		
		assertTrue(immutableEqualsToXmlRepresentation(address, xmlAddress));
	}

	public static boolean immutableEqualsToXmlRepresentation(Address immutable, XmlAddress xmlRepresentation)
	{		
		if (!AbstractXmlRepresentationMapperTest.immutableEqualsToEntityBase(immutable, xmlRepresentation))
		{
			return false;
		}
		
		return XmlAddressMapper.immutableEqualsToXmlRepresentation(immutable, xmlRepresentation);
	}
}
