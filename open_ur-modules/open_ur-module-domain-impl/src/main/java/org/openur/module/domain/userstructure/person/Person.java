package org.openur.module.domain.userstructure.person;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.EMailAddress;

public class Person
	extends AbstractPerson
{
	private static final long serialVersionUID = 357339688664869125L;
	
	// properties:
	private final Name name;
  private final String phoneNumber;
	private final String faxNumber;
	private final EMailAddress emailAddress;
  private final String mobileNumber;
  private final Address homeAddress;
  private final String homePhoneNumber;
  private final EMailAddress homeEmailAddress;

  // constructor:
	Person(PersonBuilder b)
	{
		super(b);
		this.name = b.getName();
		this.phoneNumber = b.getPhoneNumber();
		this.faxNumber = b.getFaxNumber();
		this.emailAddress = b.getEmailAddress();
		this.mobileNumber = b.getMobileNumber();
		this.homeAddress = b.getHomeAddress();
		this.homePhoneNumber = b.getHomePhoneNumber();
		this.homeEmailAddress = b.getHomeEmailAddress();
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

	public EMailAddress getEmailAddress()
	{
		return emailAddress;
	}

	public Name getName()
	{
		return name;
	}

	public String getMobileNumber()
	{
		return mobileNumber;
	}

	public Address getHomeAddress()
	{
		return homeAddress;
	}

	public String getHomePhoneNumber()
	{
		return homePhoneNumber;
	}

	public EMailAddress getHomeEmailAddress()
	{
		return homeEmailAddress;
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
														.append(this.getEmailAddress(), pers.getEmailAddress())
														.toComparison();

		if (comparison != 0)
		{
			return comparison;
		}

		return super.compareTo(pers);
	}
}
