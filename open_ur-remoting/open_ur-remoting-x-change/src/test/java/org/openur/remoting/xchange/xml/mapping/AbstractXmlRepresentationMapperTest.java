package org.openur.remoting.xchange.xml.mapping;

import java.time.LocalDateTime;

import org.openur.domain.testfixture.util.TestHelper;
import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.module.util.exception.OpenURRuntimeException;
import org.openur.remoting.xchange.xml.representations.AbstractXmlRepresentation;

public class AbstractXmlRepresentationMapperTest
{
	public static <I extends IdentifiableEntityImpl, X extends AbstractXmlRepresentation>
		boolean immutableEqualsToEntityBase(I immutable, X xmlRepresentation)
	{
		if (immutable == null && xmlRepresentation == null)
		{
			throw new OpenURRuntimeException("Both objects, immutable and entity, are null!");
		}

		if (immutable == null || xmlRepresentation == null)
		{
			return false;
		}
		
		XmlDateConverter converter = new XmlDateConverter();

		if (!TestHelper.compareLocalDateTimes(immutable.getCreationDate(), converter.convertDateTimeFromXml(xmlRepresentation.getCreationDate())))
		{
			return false;
		}
		
		LocalDateTime ldt = xmlRepresentation.getLastModifiedDate() != null ? converter.convertDateTimeFromXml(xmlRepresentation.getCreationDate()) : null;

		return TestHelper.compareLocalDateTimes(immutable.getLastModifiedDate(), ldt);
	}
}
