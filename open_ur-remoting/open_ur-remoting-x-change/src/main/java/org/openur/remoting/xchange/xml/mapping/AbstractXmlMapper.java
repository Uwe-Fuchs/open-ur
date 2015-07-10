package org.openur.remoting.xchange.xml.mapping;

import org.openur.module.domain.IdentifiableEntityBuilder;
import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.remoting.xchange.xml.representations.AbstractXmlRepresentation;

public class AbstractXmlMapper
{
	public static <I extends IdentifiableEntityImpl, X extends AbstractXmlRepresentation> X mapFromImmutable(
			I immutable, X xmlRepresentation)
	{
    XmlDateConverter converter = new XmlDateConverter();
		xmlRepresentation.setCreationDate(converter.convertDateTimeToXml(immutable.getCreationDate()));
		
		if (immutable.getLastModifiedDate() != null)
		{
			xmlRepresentation.setLastModifiedDate(converter.convertDateTimeToXml(immutable.getLastModifiedDate()));
		}

		return xmlRepresentation;
	}

	public static <IB extends IdentifiableEntityBuilder<IB>, X extends AbstractXmlRepresentation> IB mapFromXmlRepresentation(
			IB immutableBuilder, X xmlRepresentation)
	{
    XmlDateConverter converter = new XmlDateConverter();
    
		return immutableBuilder
			.creationDate(converter.convertDateTimeFromXml(xmlRepresentation.getCreationDate()))
			.lastModifiedDate(xmlRepresentation.getLastModifiedDate() != null ? converter.convertDateTimeFromXml(xmlRepresentation.getLastModifiedDate()) : null)
			.identifier(xmlRepresentation.getIdentifier());
	}
}
