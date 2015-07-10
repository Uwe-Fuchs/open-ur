package org.openur.remoting.xchange.xml.representations;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.Validate;

@XmlRootElement
public abstract class AbstractXmlRepresentation
{
	@XmlAttribute
	private String identifier;

  @XmlSchemaType(name = "date")
	@XmlAttribute(required = true)
	private XMLGregorianCalendar creationDate;

  @XmlSchemaType(name = "date")
	@XmlAttribute
	private XMLGregorianCalendar lastModifiedDate;

	public String getIdentifier()
	{
		return identifier;
	}

	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}

	public XMLGregorianCalendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(XMLGregorianCalendar creationDate)
	{
		Validate.notNull(creationDate, "creation-date must not be null!");
		this.creationDate = creationDate;
	}

	public XMLGregorianCalendar getLastModifiedDate()
	{
		return lastModifiedDate;
	}

	public void setLastModifiedDate(XMLGregorianCalendar lastModifiedDate)
	{
		this.lastModifiedDate = lastModifiedDate;
	}

	protected AbstractXmlRepresentation()
	{
		// JAXB
		super();
	}
}