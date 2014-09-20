package org.openur.module.persistence.rdbms.entity.userstructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.Title;

@Entity(name="PERSON")
public class PPerson
	extends PUserStructureBase
{
	private static final long serialVersionUID = 1747953676787910440L;

	@Column(name="EMPLOYEE_NO", length=50, nullable=false)
  private String employeeNumber;
	
	@Column(name="GENDER")
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(name="TITLE")
	@Enumerated(EnumType.STRING)
	private Title title;
	
	@Column(name="FIRSTNAME", length=50)
	private String firstName;
	
	@Column(name="LASTNAME", length=50, nullable=false)
	private String lastName;	
  
	@Column(name="PHONE_NO", length=20)
	private String phoneNumber;
	
	@Column(name="FAX_NO", length=20)
	private String faxNumber;
	
	@Column(name="EMAIL")
	private String emailAdress;
	
	@Column(name="MOBILE_NO", length=20)
  private String mobileNumber;

	@ManyToOne(fetch=FetchType.EAGER)
  private PAddress homeAddress;
  
	@Column(name="HOME_PHONE_NO", length=20)
  private String homePhoneNumber;
  
	@Column(name="HOME_EMAIL")
  private String homeEmailAdress;

	public Gender getGender()
	{
		return gender;
	}

	public void setGender(Gender gender)
	{
		this.gender = gender;
	}

	public Title getTitle()
	{
		return title;
	}

	public void setTitle(Title title)
	{
		this.title = title;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getEmployeeNumber()
	{
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber)
	{
		this.employeeNumber = employeeNumber;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getFaxNumber()
	{
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber)
	{
		this.faxNumber = faxNumber;
	}

	public String getEmailAdress()
	{
		return emailAdress;
	}

	public void setEmailAdress(String emailAdress)
	{
		this.emailAdress = emailAdress;
	}

	public String getMobileNumber()
	{
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber)
	{
		this.mobileNumber = mobileNumber;
	}

	public PAddress getHomeAddress()
	{
		return homeAddress;
	}

	public void setHomeAddress(PAddress homeAddress)
	{
		this.homeAddress = homeAddress;
	}

	public String getHomePhoneNumber()
	{
		return homePhoneNumber;
	}

	public void setHomePhoneNumber(String homePhoneNumber)
	{
		this.homePhoneNumber = homePhoneNumber;
	}

	public String getHomeEmailAdress()
	{
		return homeEmailAdress;
	}

	public void setHomeEmailAdress(String homeEmailAdress)
	{
		this.homeEmailAdress = homeEmailAdress;
	}

	PPerson()
	{
		super();
	}
	
	public static PPerson mapFromImmutable(Person p)
	{
		PPerson person = new PPerson();

		person.setNumber(p.getNumber());
		person.setEmailAdress(p.getEmailAdress() != null ? p.getEmailAdress().getAsPlainEMailAddress() : null);
		person.setEmployeeNumber(p.getEmployeeNumber());
		person.setFaxNumber(p.getFaxNumber());
		person.setFirstName(p.getName().getFirstName());
		person.setLastName(p.getName().getLastName());
		person.setGender(p.getName().getGender());
		person.setTitle(p.getName().getTitle());
		person.setHomeEmailAdress(p.getHomeEmailAdress() != null ? p.getHomeEmailAdress().getAsPlainEMailAddress() : null);
		person.setHomePhoneNumber(p.getHomePhoneNumber());
		person.setMobileNumber(p.getMobileNumber());
		person.setPhoneNumber(p.getPhoneNumber());
		person.setStatus(p.getStatus());
		person.setHomeAddress(p.getHomeAddress() != null ? PAddress.mapFromImmutable(p.getHomeAddress()) : null);
		
		return person;
	}
}
