package org.openur.module.domain.userstructure.person;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.UserStructureBaseBuilder;

public class PersonBuilder
	extends UserStructureBaseBuilder<PersonBuilder>
{
	// properties:
	private Set<OpenURApplication> applications = new HashSet<OpenURApplication>();
	private Name name = null;
  private String phoneNumber = null;
	private String faxNumber = null;
	private EMailAddress emailAddress = null;
  private String mobileNumber = null;
  private Address homeAddress = null;
  private String homePhoneNumber = null;
  private EMailAddress homeEmailAddress = null;
	
  // constructors:
  public PersonBuilder(String personalNumber, Name name)
	{
		super(personalNumber);
		
		this.name = name;
	}

	public PersonBuilder()
	{
		super();
	}

	// builder-methods:
	public PersonBuilder personalNumber(String personalNumber)
	{
		super.number(personalNumber);
		
		return this;
	}

	public PersonBuilder name(Name name)
	{
		this.name = name;
		
		return this;
	}
	
	public PersonBuilder applications(Collection<OpenURApplication> applications)
	{
		Validate.notEmpty(applications, "applications-list must not be empty!");		
		this.applications.addAll(applications);
		
		return this;
	}

	public PersonBuilder addApplication(OpenURApplication application)
	{
		Validate.notNull(application, "application must not be null!");
		this.getApplications().add(application);
		
		return this;
	}

	public PersonBuilder phoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;			
		return this;
	}

	public PersonBuilder faxNumber(String faxNumber)
	{
		this.faxNumber = faxNumber;			
		return this;
	}

	public PersonBuilder emailAddress(EMailAddress emailAddress)
	{
		this.emailAddress = emailAddress;			
		return this;
	}
	
	public PersonBuilder mobileNumber(String mobileNumber)
	{
		this.mobileNumber = mobileNumber;			
		return this;
	}

	public PersonBuilder homeAddress(Address homeAddress)
	{
		this.homeAddress = homeAddress;			
		return this;
	}
	
	public PersonBuilder homePhoneNumber(String homePhoneNumber)
	{
		this.homePhoneNumber = homePhoneNumber;			
		return this;
	}

	public PersonBuilder homeEmailAddress(EMailAddress homeEmailAddress)
	{
		this.homeEmailAddress = homeEmailAddress;			
		return this;
	}

	// accessors:
	Set<OpenURApplication> getApplications()
	{
		return applications;
	}
	
	Name getName()
	{
		return name;
	}

	String getPhoneNumber()
	{
		return phoneNumber;
	}

	String getFaxNumber()
	{
		return faxNumber;
	}

	EMailAddress getEmailAddress()
	{
		return emailAddress;
	}

	String getMobileNumber()
	{
		return mobileNumber;
	}

	Address getHomeAddress()
	{
		return homeAddress;
	}

	String getHomePhoneNumber()
	{
		return homePhoneNumber;
	}

	EMailAddress getHomeEmailAddress()
	{
		return homeEmailAddress;
	}
	
	// builder:
	public Person build()
	{
		super.build();
		
		Validate.notNull(name, "name must not be null");
		
		return new Person(this);
	}
}
