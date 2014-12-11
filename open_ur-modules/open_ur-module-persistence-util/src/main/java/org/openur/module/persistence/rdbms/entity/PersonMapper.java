package org.openur.module.persistence.rdbms.entity;

import org.apache.commons.lang3.StringUtils;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;

public class PersonMapper
{
	public static PPerson mapFromImmutable(Person immutable)
	{
		PPerson persistable = new PPerson(immutable.getPersonalNumber(), immutable.getName().getLastName());

		persistable.setEmailAdress(immutable.getEmailAddress() != null ? immutable.getEmailAddress().getAsPlainEMailAddress() : null);
		persistable.setFaxNumber(immutable.getFaxNumber());
		persistable.setFirstName(immutable.getName().getFirstName());
		persistable.setGender(immutable.getName().getGender());
		persistable.setTitle(immutable.getName().getTitle());
		persistable.setHomeEmailAdress(immutable.getHomeEmailAddress() != null ? immutable.getHomeEmailAddress().getAsPlainEMailAddress() : null);
		persistable.setHomePhoneNumber(immutable.getHomePhoneNumber());
		persistable.setMobileNumber(immutable.getMobileNumber());
		persistable.setPhoneNumber(immutable.getPhoneNumber());
		persistable.setStatus(immutable.getStatus());
		persistable.setHomeAddress(immutable.getHomeAddress() != null ? AddressMapper.mapFromImmutable(immutable.getHomeAddress()) : null);
		
		immutable.getApplications()
				.stream()
				.map(ApplicationMapper::mapFromImmutable)
				.forEach(persistable::addApplication);
		
		return persistable;
	}
	
	public static Person mapFromEntity(PPerson persistable)
	{
		Name name;
		
		if (persistable.getTitle() != null)
		{
			name = Name.create(
					persistable.getGender(), 
					persistable.getTitle(), 
					persistable.getFirstName(), 
					persistable.getLastName()
				);			
		} else
		{
			name = Name.create(
					persistable.getGender(), 
					persistable.getFirstName(), 
					persistable.getLastName()
				);
		}
		
		PersonBuilder immutableBuilder = new PersonBuilder(persistable.getPersonalNumber(), name);
		
		if (StringUtils.isNotEmpty(persistable.getIdentifier()))
		{
			immutableBuilder.identifier(persistable.getIdentifier());
		}
		
		immutableBuilder
				.emailAddress(StringUtils.isNotEmpty(persistable.getEmailAddress()) ? EMailAddress.create(persistable.getEmailAddress()) : null)
				.faxNumber(persistable.getFaxNumber())
				.homeEmailAddress(StringUtils.isNotEmpty(persistable.getHomeEmailAddress()) ? EMailAddress.create(persistable.getHomeEmailAddress()) : null)
				.homePhoneNumber(persistable.getHomePhoneNumber())
				.mobileNumber(persistable.getMobileNumber())
				.phoneNumber(persistable.getPhoneNumber())
				.status(persistable.getStatus())
				.homeAddress(persistable.getHomeAddress() != null ? AddressMapper.mapFromEntity(persistable.getHomeAddress()) : null)
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate());
		
		persistable.getApplications()
				.stream()
				.map(ApplicationMapper::mapFromEntity)
				.forEach(immutableBuilder::addApplication);
		
		return immutableBuilder.build();
	}
}
