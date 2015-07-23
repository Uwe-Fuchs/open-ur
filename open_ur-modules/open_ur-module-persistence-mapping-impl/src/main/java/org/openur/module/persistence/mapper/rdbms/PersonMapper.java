package org.openur.module.persistence.mapper.rdbms;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPerson;

public class PersonMapper
	extends UserStructureBaseMapper implements IPersonMapper<Person>
{
	@Inject
	private AddressMapper addressMapper;
	
	@Inject
	private ApplicationMapper applicationMapper;
	
	@Override
	public PPerson mapFromDomainObject(Person domainObject)
	{
		PPerson persistable = new PPerson(domainObject.getPersonalNumber(), domainObject.getName().getLastName());

		persistable.setEmailAdress(domainObject.getEmailAddress() != null ? domainObject.getEmailAddress().getAsPlainEMailAddress() : null);
		persistable.setFaxNumber(domainObject.getFaxNumber());
		persistable.setFirstName(domainObject.getName().getFirstName());
		persistable.setGender(domainObject.getName().getGender());
		persistable.setTitle(domainObject.getName().getTitle());
		persistable.setHomeEmailAdress(domainObject.getHomeEmailAddress() != null ? domainObject.getHomeEmailAddress().getAsPlainEMailAddress() : null);
		persistable.setHomePhoneNumber(domainObject.getHomePhoneNumber());
		persistable.setMobileNumber(domainObject.getMobileNumber());
		persistable.setPhoneNumber(domainObject.getPhoneNumber());
		persistable.setStatus(domainObject.getStatus());
		persistable.setHomeAddress(domainObject.getHomeAddress() != null ? addressMapper.mapFromDomainObject(domainObject.getHomeAddress()) : null);
		
		domainObject.getApplications()
				.stream()
				.map(applicationMapper::mapFromDomainObject)
				.forEach(persistable::addApplication);
		
		return persistable;
	}
	
	@Override
	public Person mapFromEntity(PPerson entity)
	{
		Name name;
		
		if (entity.getTitle() != null)
		{
			name = Name.create(
					entity.getGender(), 
					entity.getTitle(), 
					entity.getFirstName(), 
					entity.getLastName()
				);			
		} else
		{
			name = Name.create(
					entity.getGender(), 
					entity.getFirstName(), 
					entity.getLastName()
				);
		}
		
		PersonBuilder immutableBuilder = new PersonBuilder(entity.getPersonalNumber(), name);		
		immutableBuilder = super.buildImmutable(immutableBuilder, entity);
		
		immutableBuilder
				.emailAddress(StringUtils.isNotEmpty(entity.getEmailAddress()) ? EMailAddress.create(entity.getEmailAddress()) : null)
				.faxNumber(entity.getFaxNumber())
				.homeEmailAddress(StringUtils.isNotEmpty(entity.getHomeEmailAddress()) ? EMailAddress.create(entity.getHomeEmailAddress()) : null)
				.homePhoneNumber(entity.getHomePhoneNumber())
				.mobileNumber(entity.getMobileNumber())
				.phoneNumber(entity.getPhoneNumber())
				.homeAddress(entity.getHomeAddress() != null ? addressMapper.mapFromEntity(entity.getHomeAddress()) : null);
		
		entity.getApplications()
				.stream()
				.map(applicationMapper::mapFromEntity)
				.forEach(immutableBuilder::addApplication);
		
		return immutableBuilder.build();
	}

	public static boolean domainObjectEqualsToEntity(Person domainObject, PPerson entity)
	{
		if (!UserStructureBaseMapper.domainObjectEqualsToEntity(domainObject, entity))
		{
			return false;
		}
		
		for (PApplication app : entity.getApplications())
		{
			if (!domainObject.isInApplication(app.getApplicationName()))
			{
				return false;
			}
		}
		
		if ((domainObject.getHomeAddress() != null || entity.getHomeAddress() != null)
			&& !AddressMapper.domainObjectEqualsToEntity(domainObject.getHomeAddress(), entity.getHomeAddress()))
		{
			return false;
		}
		
		return new EqualsBuilder()
				.append(domainObject.getPersonalNumber(), entity.getPersonalNumber())
				.append(domainObject.getName().getTitle(), entity.getTitle())
				.append(domainObject.getName().getFirstName(), entity.getFirstName())
				.append(domainObject.getName().getLastName(), entity.getLastName())
				.append(domainObject.getName().getGender(), entity.getGender())
				.append(domainObject.getPhoneNumber(), entity.getPhoneNumber())
				.append(domainObject.getFaxNumber(), entity.getFaxNumber())
				.append(domainObject.getMobileNumber(), entity.getMobileNumber())
				.append(domainObject.getHomePhoneNumber(), entity.getHomePhoneNumber())
				.append(domainObject.getEmailAddress() != null ? domainObject.getEmailAddress().getAsPlainEMailAddress() : null, 
						entity.getEmailAddress())
				.append(domainObject.getHomeEmailAddress() != null ? domainObject.getHomeEmailAddress().getAsPlainEMailAddress() : null, 
						entity.getHomeEmailAddress())
				.isEquals();
	}
}
