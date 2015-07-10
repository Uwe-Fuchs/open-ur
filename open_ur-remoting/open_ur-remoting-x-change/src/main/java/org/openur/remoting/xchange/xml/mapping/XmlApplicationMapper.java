package org.openur.remoting.xchange.xml.mapping;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.application.OpenURApplicationBuilder;
import org.openur.remoting.xchange.xml.representations.XmlApplication;

public class XmlApplicationMapper
{
	public static XmlApplication mapFromImmutable(OpenURApplication immutable)
	{
		XmlApplication xmlApplication = new XmlApplication(immutable.getApplicationName());		
		
		return AbstractXmlMapper.mapFromImmutable(immutable, xmlApplication);
	}

	public static OpenURApplication mapFromXmlRepresentation(XmlApplication xmlRepresentation)
	{
		OpenURApplicationBuilder immutableBuilder = new OpenURApplicationBuilder(xmlRepresentation.getApplicationName());		
		immutableBuilder = AbstractXmlMapper.mapFromXmlRepresentation(immutableBuilder, xmlRepresentation);
		
		return immutableBuilder.build();
	}	
	
	public static boolean immutableEqualsToXmlRepresentation(OpenURApplication immutable, XmlApplication xmlRepresentation)
	{
		return immutable.getApplicationName().equals(xmlRepresentation.getApplicationName());
	}
}
