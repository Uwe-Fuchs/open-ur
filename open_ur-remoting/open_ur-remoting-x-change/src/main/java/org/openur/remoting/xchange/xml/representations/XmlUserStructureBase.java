package org.openur.remoting.xchange.xml.representations;

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.userstructure.Status;

public class XmlUserStructureBase
	extends AbstractXmlRepresentation
{
	@XmlAttribute(required = true)
	private String number;
	
	@XmlAttribute(required = true)
	private Status status;
	
	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	public XmlUserStructureBase(String number)
	{
		super();
		
		Validate.notEmpty(number, "number must not be empty!");
		this.number = number;
	}

	protected XmlUserStructureBase()
	{
		// JAXB
		super();
	}
}
