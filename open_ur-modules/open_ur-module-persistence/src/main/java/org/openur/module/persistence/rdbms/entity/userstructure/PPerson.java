package org.openur.module.persistence.rdbms.entity.userstructure;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.StringUtils;
import org.openur.module.domain.application.OpenURApplication;
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
	private String emailAddress;
	
	@Column(name="MOBILE_NO", length=20)
  private String mobileNumber;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="HOME_ADDRESS_ID", referencedColumnName="ID")
  private PAddress homeAddress;
  
	@Column(name="HOME_PHONE_NO", length=20)
  private String homePhoneNumber;
  
	@Column(name="HOME_EMAIL")
  private String homeEmailAddress;

	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
		name="PERSONS_APPS",
		joinColumns={@JoinColumn(name="ID_PERSON", referencedColumnName="ID")},
		inverseJoinColumns={@JoinColumn(name="ID_APPLICATION", referencedColumnName="ID")}
	)
  private List<PApplication> applications = new ArrayList<>();

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

	public String getEmailAddress()
	{
		return emailAddress;
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

	public String getHomeEmailAddress()
	{
		return homeEmailAddress;
	}

	public Title getTitle()
	{
		return title;
	}

	public List<PApplication> getApplications()
	{
		return applications;
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
		this.emailAddress = emailAdress;
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
		this.homeEmailAddress = homeEmailAdress;
	}

	void setApplications(List<PApplication> applications)
	{
		this.applications = applications;
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
		persistable.setHomeAddress(immutable.getHomeAddress() != null ? PAddress.mapFromImmutable(immutable.getHomeAddress()) : null);
		
		for (OpenURApplication app : immutable.getApplications())
		{
			PApplication pApp = PApplication.mapFromImmutable(app);
			persistable.getApplications().add(pApp);
		}
		
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
		
		PersonBuilder immutableBuilder = new PersonBuilder(name)
				.number(persistable.getNumber())
				.emailAdress(StringUtils.isNotEmpty(persistable.getEmailAddress()) ? new EMailAddress(persistable.getEmailAddress()) : null)
				.employeeNumber(persistable.getEmployeeNumber())
				.faxNumber(persistable.getFaxNumber())
				.homeEmailAdress(StringUtils.isNotEmpty(persistable.getHomeEmailAddress()) ? new EMailAddress(persistable.getHomeEmailAddress()) : null)
				.homePhoneNumber(persistable.getHomePhoneNumber())
				.mobileNumber(persistable.getMobileNumber())
				.phoneNumber(persistable.getPhoneNumber())
				.status(persistable.getStatus())
				.homeAddress(persistable.getHomeAddress() != null ? PAddress.mapFromEntity(persistable.getHomeAddress()) : null)
				.creationDate(persistable.getCreationDate())
				.lastModifiedDate(persistable.getLastModifiedDate());
		
		for (PApplication pApp : persistable.getApplications())
		{
			OpenURApplication app = PApplication.mapToImmutable(pApp);
			immutableBuilder.addApp(app);
		}
		
		return immutableBuilder.build();
	}
}
