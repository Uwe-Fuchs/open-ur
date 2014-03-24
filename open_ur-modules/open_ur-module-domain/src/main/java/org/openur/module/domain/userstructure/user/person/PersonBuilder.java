package org.openur.module.domain.userstructure.user.person;

import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.user.person.abstr.AbstractPersonSimpleBuilder;

public class PersonBuilder
	extends AbstractPersonSimpleBuilder<PersonBuilder>
{
	// properties:
	private Name name = null;
  private String employeeNumber = null;
	private String phoneNumber = null;
	private String faxNumber = null;
	private EMailAddress emailAdress = null;
  private String mobileNumber = null;
  private Address homeAddress = null;
  private String homePhoneNumber = null;
  private EMailAddress homeEmailAdress = null;
	
  // constructors:
	public PersonBuilder(String username, String password)
	{
		super(username, password);
	}
	
	public PersonBuilder(String identifier, String username, String password)
	{
		super(identifier, username, password);
	}

	// builder-methods:	
	public PersonBuilder name(Name name)
	{
		this.name = name;			
		return this;
	}
	
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

	public PersonBuilder emailAdress(EMailAddress emailAdress)
	{
		this.emailAdress = emailAdress;			
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

	public PersonBuilder homeEmailAdress(EMailAddress homeEmailAdress)
	{
		this.homeEmailAdress = homeEmailAdress;			
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

	EMailAddress getEmailAdress()
	{
		return emailAdress;
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

	EMailAddress getHomeEmailAdress()
	{
		return homeEmailAdress;
	}
}
