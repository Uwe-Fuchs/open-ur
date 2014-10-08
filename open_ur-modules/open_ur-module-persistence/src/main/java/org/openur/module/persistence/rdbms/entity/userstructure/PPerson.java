package org.openur.module.persistence.rdbms.entity.userstructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.StringUtils;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;
import org.openur.module.domain.userstructure.person.Title;
import org.openur.module.persistence.rdbms.entity.application.PApplication;

@Entity(name="PERSON")
public class PPerson
	extends PUserStructureBase
{
	private static final long serialVersionUID = 1747953676787910440L;

	// properties:
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

	@ManyToOne
  private PApplication application;

	// accessors:
	public String getEmployeeNumber()
	{
		return employeeNumber;
	}

	public Gender getGender()
	{
		return gender;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public String getFaxNumber()
	{
		return faxNumber;
	}

	public String getEmailAdress()
	{
		return emailAdress;
	}

	public String getMobileNumber()
	{
		return mobileNumber;
	}

	public PAddress getHomeAddress()
	{
		return homeAddress;
	}

	public String getHomePhoneNumber()
	{
		return homePhoneNumber;
	}

	public String getHomeEmailAdress()
	{
		return homeEmailAdress;
	}

	public PApplication getApplication()
	{
		return application;
	}

	Title getTitle()
	{
		return title;
	}

	void setTitle(Title title)
	{
		this.title = title;
	}

	void setEmployeeNumber(String employeeNumber)
	{
		this.employeeNumber = employeeNumber;
	}

	void setGender(Gender gender)
	{
		this.gender = gender;
	}

	void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	void setFaxNumber(String faxNumber)
	{
		this.faxNumber = faxNumber;
	}

	void setEmailAdress(String emailAdress)
	{
		this.emailAdress = emailAdress;
	}

	void setMobileNumber(String mobileNumber)
	{
		this.mobileNumber = mobileNumber;
	}

	void setHomeAddress(PAddress homeAddress)
	{
		this.homeAddress = homeAddress;
	}

	void setHomePhoneNumber(String homePhoneNumber)
	{
		this.homePhoneNumber = homePhoneNumber;
	}

	void setHomeEmailAdress(String homeEmailAdress)
	{
		this.homeEmailAdress = homeEmailAdress;
	}

	void setApplication(PApplication application)
	{
		this.application = application;
	}

	// constructor:
	PPerson()
	{
		super();
	}

	public static PPerson mapFromImmutable(Person immutable)
	{
		PPerson persistable = new PPerson();

		persistable.setNumber(immutable.getNumber());
		persistable.setEmailAdress(immutable.getEmailAdress() != null ? immutable.getEmailAdress().getAsPlainEMailAddress() : null);
		persistable.setEmployeeNumber(immutable.getEmployeeNumber());
		persistable.setFaxNumber(immutable.getFaxNumber());
		persistable.setFirstName(immutable.getName().getFirstName());
		persistable.setLastName(immutable.getName().getLastName());
		persistable.setGender(immutable.getName().getGender());
		persistable.setTitle(immutable.getName().getTitle());
		persistable.setHomeEmailAdress(immutable.getHomeEmailAdress() != null ? immutable.getHomeEmailAdress().getAsPlainEMailAddress() : null);
		persistable.setHomePhoneNumber(immutable.getHomePhoneNumber());
		persistable.setMobileNumber(immutable.getMobileNumber());
		persistable.setPhoneNumber(immutable.getPhoneNumber());
		persistable.setStatus(immutable.getStatus());
		persistable.setHomeAddress(immutable.getHomeAddress() != null ? PAddress.mapFromImmutable(immutable.getHomeAddress()) : null);
		
		return persistable;
	}
	
	public static Person mapToImmutable(PPerson persistable)
	{
		Name name;
		
		if (persistable.getTitle() != null)
		{
			name = Name.create(
				persistable.getGender(), 
				persistable.getTitle(), 
				persistable.getFirstName(), 
				persistable.getLastName());			
		} else
		{
			name = Name.create(
				persistable.getGender(), 
				persistable.getFirstName(), 
				persistable.getLastName());
		}
		
		Person immutable = new PersonBuilder(name)
				.number(persistable.getNumber())
				.emailAdress(StringUtils.isNotEmpty(persistable.getEmailAdress()) ? new EMailAddress(persistable.getEmailAdress()) : null)
				.employeeNumber(persistable.getEmployeeNumber())
				.faxNumber(persistable.getFaxNumber())
				.homeEmailAdress(StringUtils.isNotEmpty(persistable.getHomeEmailAdress()) ? new EMailAddress(persistable.getHomeEmailAdress()) : null)
				.homePhoneNumber(persistable.getHomePhoneNumber())
				.mobileNumber(persistable.getMobileNumber())
				.phoneNumber(persistable.getPhoneNumber())
				.status(persistable.getStatus())
				.homeAddress(persistable.getHomeAddress() != null ? PAddress.mapFromEntity(persistable.getHomeAddress()) : null)
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate())
				.build();
		
		return immutable;
	}
}
