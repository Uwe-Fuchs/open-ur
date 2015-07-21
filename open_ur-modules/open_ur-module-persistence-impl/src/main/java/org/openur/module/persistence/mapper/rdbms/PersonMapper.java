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
	extends UserStructureBaseMapper
{
	@Inject
	private AddressMapper addressMapper;
	
	@Inject
	private ApplicationMapper applicationMapper;
	
	public PPerson mapFromImmutable(Person immutable)
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
		persistable.setHomeAddress(immutable.getHomeAddress() != null ? addressMapper.mapFromImmutable(immutable.getHomeAddress()) : null);
		
		immutable.getApplications()
				.stream()
				.map(applicationMapper::mapFromImmutable)
				.forEach(persistable::addApplication);
		
		return persistable;
	}
	
	public Person mapFromEntity(PPerson persistable)
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
		immutableBuilder = super.buildImmutable(immutableBuilder, persistable);
		
		immutableBuilder
				.emailAddress(StringUtils.isNotEmpty(persistable.getEmailAddress()) ? EMailAddress.create(persistable.getEmailAddress()) : null)
				.faxNumber(persistable.getFaxNumber())
				.homeEmailAddress(StringUtils.isNotEmpty(persistable.getHomeEmailAddress()) ? EMailAddress.create(persistable.getHomeEmailAddress()) : null)
				.homePhoneNumber(persistable.getHomePhoneNumber())
				.mobileNumber(persistable.getMobileNumber())
				.phoneNumber(persistable.getPhoneNumber())
				.homeAddress(persistable.getHomeAddress() != null ? addressMapper.mapFromEntity(persistable.getHomeAddress()) : null);
		
		persistable.getApplications()
				.stream()
				.map(applicationMapper::mapFromEntity)
				.forEach(immutableBuilder::addApplication);
		
		return immutableBuilder.build();
	}

	public static boolean immutableEqualsToEntity(Person immutable, PPerson persistable)
	{
		if (!UserStructureBaseMapper.immutableEqualsToEntity(immutable, persistable))
		{
			return false;
		}
		
		for (PApplication app : persistable.getApplications())
		{
			if (!immutable.isInApplication(app.getApplicationName()))
			{
				return false;
			}
		}
		
		if ((immutable.getHomeAddress() != null || persistable.getHomeAddress() != null)
			&& !AddressMapper.immutableEqualsToEntity(immutable.getHomeAddress(), persistable.getHomeAddress()))
		{
			return false;
		}
		
		return new EqualsBuilder()
				.append(immutable.getPersonalNumber(), persistable.getPersonalNumber())
				.append(immutable.getName().getTitle(), persistable.getTitle())
				.append(immutable.getName().getFirstName(), persistable.getFirstName())
				.append(immutable.getName().getLastName(), persistable.getLastName())
				.append(immutable.getName().getGender(), persistable.getGender())
				.append(immutable.getPhoneNumber(), persistable.getPhoneNumber())
				.append(immutable.getFaxNumber(), persistable.getFaxNumber())
				.append(immutable.getMobileNumber(), persistable.getMobileNumber())
				.append(immutable.getHomePhoneNumber(), persistable.getHomePhoneNumber())
				.append(immutable.getEmailAddress() != null ? immutable.getEmailAddress().getAsPlainEMailAddress() : null, 
						persistable.getEmailAddress())
				.append(immutable.getHomeEmailAddress() != null ? immutable.getHomeEmailAddress().getAsPlainEMailAddress() : null, 
						persistable.getHomeEmailAddress())
				.isEquals();
	}
}
