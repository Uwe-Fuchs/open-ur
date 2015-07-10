package org.openur.remoting.xchange.xml.mapping;

import org.apache.commons.lang3.StringUtils;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.remoting.xchange.xml.representations.XmlPerson;

public class XmlPersonMapper
{
	public static XmlPerson mapFromImmutable(Person immutable)
	{
		XmlPerson xmlRepresentation = new XmlPerson(immutable.getPersonalNumber(), immutable.getName().getLastName());

		xmlRepresentation.setEmailAddress(immutable.getEmailAddress() != null ? immutable.getEmailAddress().getAsPlainEMailAddress() : null);
		xmlRepresentation.setFaxNumber(immutable.getFaxNumber());
		xmlRepresentation.setFirstName(immutable.getName().getFirstName());
		xmlRepresentation.setGender(immutable.getName().getGender());
		xmlRepresentation.setTitle(immutable.getName().getTitle());
		xmlRepresentation.setHomeEmailAddress(immutable.getHomeEmailAddress() != null ? immutable.getHomeEmailAddress().getAsPlainEMailAddress() : null);
		xmlRepresentation.setHomePhoneNumber(immutable.getHomePhoneNumber());
		xmlRepresentation.setMobileNumber(immutable.getMobileNumber());
		xmlRepresentation.setPhoneNumber(immutable.getPhoneNumber());
		xmlRepresentation.setStatus(immutable.getStatus());
		xmlRepresentation.setHomeAddress(immutable.getHomeAddress() != null ? XmlAddressMapper.mapFromImmutable(immutable.getHomeAddress()) : null);
		
		immutable.getApplications()
				.stream()
				.map(XmlApplicationMapper::mapFromImmutable)
				.forEach(xmlRepresentation::addApplication);
		
		return AbstractXmlMapper.mapFromImmutable(immutable, xmlRepresentation);
	}
	
	public static Person mapFromXmlRepresentation(XmlPerson xmlRepresentation)
	{
		Name name;
		
		if (xmlRepresentation.getTitle() != null)
		{
			name = Name.create(
					xmlRepresentation.getGender(), 
					xmlRepresentation.getTitle(), 
					xmlRepresentation.getFirstName(), 
					xmlRepresentation.getLastName()
				);			
		} else
		{
			name = Name.create(
					xmlRepresentation.getGender(), 
					xmlRepresentation.getFirstName(), 
					xmlRepresentation.getLastName()
				);
		}
		
		PersonBuilder immutableBuilder = new PersonBuilder(xmlRepresentation.getNumber(), name);
		
		immutableBuilder
				.emailAddress(StringUtils.isNotEmpty(xmlRepresentation.getEmailAddress()) ? EMailAddress.create(xmlRepresentation.getEmailAddress()) : null)
				.faxNumber(xmlRepresentation.getFaxNumber())
				.homeEmailAddress(StringUtils.isNotEmpty(xmlRepresentation.getHomeEmailAddress()) ? EMailAddress.create(xmlRepresentation.getHomeEmailAddress()) : null)
				.homePhoneNumber(xmlRepresentation.getHomePhoneNumber())
				.mobileNumber(xmlRepresentation.getMobileNumber())
				.phoneNumber(xmlRepresentation.getPhoneNumber())
				.status(xmlRepresentation.getStatus())
				.homeAddress(xmlRepresentation.getHomeAddress() != null ? XmlAddressMapper.mapFromXmlRepresentation(xmlRepresentation.getHomeAddress()) : null);
		
		xmlRepresentation.getApplications()
				.stream()
				.map(XmlApplicationMapper::mapFromXmlRepresentation)
				.forEach(immutableBuilder::addApplication);
		
		immutableBuilder = AbstractXmlMapper.mapFromXmlRepresentation(immutableBuilder, xmlRepresentation);
		
		return immutableBuilder.build();
	}

	public static boolean immutableEqualsToEntity(Person immutable, XmlPerson xmlRepresentation)
	{
		return immutable.getPersonalNumber().equals(xmlRepresentation.getNumber());
	}
}
