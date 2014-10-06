package org.openur.module.domain.userstructure.person;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.IAddress;
import org.openur.module.domain.userstructure.person.abstr.AbstractPerson;

public class Person
	extends AbstractPerson
{
	private static final long serialVersionUID = 357339688664869125L;
	
	// properties:
	private final Name name;
  private final String employeeNumber;	
	private final String phoneNumber;
	private final String faxNumber;
	private final EMailAddress emailAdress;
  private final String mobileNumber;
  private final IAddress homeAddress;
  private final String homePhoneNumber;
  private final EMailAddress homeEmailAdress;

  // constructor:
	Person(PersonBuilder b)
	{
		super(b);
		this.name = b.getName();
		this.employeeNumber = b.getEmployeeNumber();		
		this.phoneNumber = b.getPhoneNumber();
		this.faxNumber = b.getFaxNumber();
		this.emailAdress = b.getEmailAdress();
		this.mobileNumber = b.getMobileNumber();
		this.homeAddress = b.getHomeAddress();
		this.homePhoneNumber = b.getHomePhoneNumber();
		this.homeEmailAdress = b.getHomeEmailAdress();
	}

	// accessors:
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public String getFaxNumber()
	{
		return faxNumber;
	}

	public EMailAddress getEmailAdress()
	{
		return emailAdress;
	}

	public Name getName()
	{
		return name;
	}

	public String getEmployeeNumber()
	{
		return employeeNumber;
	}

	public String getMobileNumber()
	{
		return mobileNumber;
	}

	public IAddress getHomeAddress()
	{
		return homeAddress;
	}

	public String getHomePhoneNumber()
	{
		return homePhoneNumber;
	}

	public EMailAddress getHomeEmailAdress()
	{
		return homeEmailAdress;
	}

	// operations:
	@Override
	public int compareTo(IPerson other)
	{
		if (!(other instanceof Person))
		{
			return super.compareTo(other);
		}
		
		Person pers = (Person) other;
		
		int comparison = new CompareToBuilder()
														.append(this.getName(), pers.getName())
														.append(this.getEmailAdress(), pers.getEmailAdress())
														.toComparison();

		if (comparison != 0)
		{
			return comparison;
		}

		return super.compareTo(pers);
	}
}
