package org.openur.module.domain.userstructure.person;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.person.abstr.AbstractPersonBuilder;

public class PersonBuilder
	extends AbstractPersonBuilder<PersonBuilder>
{
	// properties:
	private Name name = null;
  private String employeeNumber = null;
	private String phoneNumber = null;
	private String faxNumber = null;
	private EMailAddress emailAddress = null;
  private String mobileNumber = null;
  private Address homeAddress = null;
  private String homePhoneNumber = null;
  private EMailAddress homeEmailAddress = null;
	
  // constructors:
	public PersonBuilder(Name name)
	{		
		super();
		
		init(name);
	}
	
	public PersonBuilder(String identifier, Name name)
	{
		super(identifier);
		
		init(name);
	}
	
	private void init(Name name)
	{
		Validate.notNull(name, "name must not be null");
		this.name = name;
	}

	// builder-methods:	
	public PersonBuilder employeeNumber(String employeeNumber)
	{
		this.employeeNumber = employeeNumber;			
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
	
	public Person build()
	{
		return new Person(this);
	}

	// accessors:
	Name getName()
	{
		return name;
	}

	String getEmployeeNumber()
	{
		return employeeNumber;
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
}
