package org.openur.remoting.xchange.xml.mapping;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.remoting.xchange.xml.representations.XmlApplication;

public class XmlApplicationMapperTest
{
	@Test
	public void testMapFromImmutable()
	{
		XmlApplication application = XmlApplicationMapper.mapFromImmutable(TestObjectContainer.APP_A);
		
		assertTrue(immutableEqualsToXmlRepresentation(TestObjectContainer.APP_A, application));
	}

	@Test
	public void testMapFromXmlRepresentation()
	{
		XmlApplication xmlApplication = new XmlApplication(TestObjectContainer.APP_A.getApplicationName());
		xmlApplication.setIdentifier(TestObjectContainer.APP_A.getIdentifier());
		xmlApplication.setCreationDate(new XmlDateConverter().convertDateTimeToXml(TestObjectContainer.APP_A.getCreationDate()));
		
		OpenURApplication application = XmlApplicationMapper.mapFromXmlRepresentation(xmlApplication);
		
		assertTrue(immutableEqualsToXmlRepresentation(application, xmlApplication));
	}

	public static boolean immutableEqualsToXmlRepresentation(OpenURApplication immutable, XmlApplication xmlRepresentation)
	{		
		if (!AbstractXmlRepresentationMapperTest.immutableEqualsToEntityBase(immutable, xmlRepresentation))
		{
			return false;
		}
		
		return XmlApplicationMapper.immutableEqualsToXmlRepresentation(immutable, xmlRepresentation);
	}
}
