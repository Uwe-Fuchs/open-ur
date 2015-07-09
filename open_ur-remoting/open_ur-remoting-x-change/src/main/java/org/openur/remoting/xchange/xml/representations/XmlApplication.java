package org.openur.remoting.xchange.xml.representations;

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang3.Validate;

public class XmlApplication
	extends AbstractXmlRepresentation
{
	@XmlAttribute(required = true)
	private String applicationName;

	public String getApplicationName()
	{
		return applicationName;
	}

	public XmlApplication(String applicationName)
	{
		super();
		
		Validate.notEmpty(applicationName, "application-name must not be empty!");
		this.applicationName = applicationName;
	}

	@SuppressWarnings("unused")
	private XmlApplication()
	{
		// JAXB
		super();
	}
}
