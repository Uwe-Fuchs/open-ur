package org.openur.remoting.xchange.xml.mapping;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.userstructure.Address;
import org.openur.remoting.xchange.xml.representations.XmlAddress;

public class XmlAddressMapperTest
{

	@Before
	public void setUp()
		throws Exception
	{
	}

	@After
	public void tearDown()
		throws Exception
	{
	}

	@Test
	public void testMapFromImmutable()
	{
		XmlAddress xmlAddress = XmlAddressMapper.mapFromImmutable(TestObjectContainer.ADDRESS_1);

		assertTrue(immutableEqualsToXmlRepresentation(TestObjectContainer.ADDRESS_1, xmlAddress));
	}

	@Test
	public void testMapFromXmlRepresentation()
	{
		Address address = TestObjectContainer.ADDRESS_1;
		XmlDateConverter converter = new XmlDateConverter();
		
		XmlAddress xmlAddress = new XmlAddress(
				address.getPostcode(), address.getCountry().getCountryCode(), converter.convertDateTimeToXml(address.getCreationDate()));
		xmlAddress.setIdentifier(address.getIdentifier());
		xmlAddress.setCareOf(address.getCareOf());
		xmlAddress.setCity(address.getCity());
		xmlAddress.setPoBox(address.getPoBox());
		xmlAddress.setStreet(address.getStreet());
		xmlAddress.setStreetNo(address.getStreetNo());

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
