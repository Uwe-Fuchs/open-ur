package org.openur.module.persistence.rdbms.entity.userstructure;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.persistence.rdbms.entity.application.ApplicationMapper;
import org.openur.module.persistence.rdbms.entity.application.PApplication;

public class PersonMapper
{
	public static PPerson mapFromImmutable(Person immutable)
	{
		PPerson persistable = new PPerson();

		persistable.setNumber(immutable.getNumber());
		persistable.setEmailAdress(immutable.getEmailAddress() != null ? immutable.getEmailAddress().getAsPlainEMailAddress() : null);
		persistable.setEmployeeNumber(immutable.getEmployeeNumber());
		persistable.setFaxNumber(immutable.getFaxNumber());
		persistable.setFirstName(immutable.getName().getFirstName());
		persistable.setLastName(immutable.getName().getLastName());
		persistable.setGender(immutable.getName().getGender());
		persistable.setTitle(immutable.getName().getTitle());
		persistable.setHomeEmailAdress(immutable.getHomeEmailAddress() != null ? immutable.getHomeEmailAddress().getAsPlainEMailAddress() : null);
		persistable.setHomePhoneNumber(immutable.getHomePhoneNumber());
		persistable.setMobileNumber(immutable.getMobileNumber());
		persistable.setPhoneNumber(immutable.getPhoneNumber());
		persistable.setStatus(immutable.getStatus());
		persistable.setHomeAddress(immutable.getHomeAddress() != null ? AddressMapper.mapFromImmutable(immutable.getHomeAddress()) : null);
		
		for (OpenURApplication app : immutable.getApplications())
		{
			PApplication pApp = ApplicationMapper.mapFromImmutable(app);
			persistable.getApplications().add(pApp);
		}
		
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
		
		PersonBuilder immutableBuilder;
		
		if (StringUtils.isNotEmpty(persistable.getIdentifier()))
		{
			immutableBuilder = new PersonBuilder(persistable.getIdentifier(), name);
		} else
		{
			immutableBuilder = new PersonBuilder(name);
		}
		
		immutableBuilder
				.number(persistable.getNumber())
				.emailAddress(StringUtils.isNotEmpty(persistable.getEmailAddress()) ? new EMailAddress(persistable.getEmailAddress()) : null)
				.employeeNumber(persistable.getEmployeeNumber())
				.faxNumber(persistable.getFaxNumber())
				.homeEmailAddress(StringUtils.isNotEmpty(persistable.getHomeEmailAddress()) ? new EMailAddress(persistable.getHomeEmailAddress()) : null)
				.homePhoneNumber(persistable.getHomePhoneNumber())
				.mobileNumber(persistable.getMobileNumber())
				.phoneNumber(persistable.getPhoneNumber())
				.status(persistable.getStatus())
				.homeAddress(persistable.getHomeAddress() != null ? AddressMapper.mapFromEntity(persistable.getHomeAddress()) : null)
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate());
		
		for (PApplication pApp : persistable.getApplications())
		{
			OpenURApplication app = ApplicationMapper.mapFromEntity(pApp);
			immutableBuilder.addApp(app);
		}
		
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
				.append(immutable.getName().getTitle(), persistable.getTitle())
				.append(immutable.getName().getFirstName(), persistable.getFirstName())
				.append(immutable.getName().getLastName(), persistable.getLastName())
				.append(immutable.getName().getGender(), persistable.getGender())
				.append(immutable.getEmployeeNumber(), persistable.getEmployeeNumber())
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
