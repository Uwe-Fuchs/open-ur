package org.openur.module.persistence.rdbms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Title;

@Entity(name="PERSON")
@Table(indexes = {@Index(columnList="HOME_ADDRESS_ID", name="IDX_PERSON_ADDRESS")})
public class PPerson
	extends PUserStructureBase
{
	private static final long serialVersionUID = 1747953676787910440L;

	// properties:
	@Column(name="GENDER")
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(name="TITLE")
	@Enumerated(EnumType.STRING)
	private Title title = Title.NONE;
	
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

	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
		name="PERSONS_APPS",
		joinColumns={@JoinColumn(name="ID_PERSON", referencedColumnName="ID")},
		inverseJoinColumns={@JoinColumn(name="ID_APPLICATION", referencedColumnName="ID")}
	)
  private Set<PApplication> applications = new HashSet<>();

	// accessors:
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

	public Set<PApplication> getApplications()
	{
		return applications;
	}

	public void setTitle(Title title)
	{
		this.title = title;
	}

	public void setGender(Gender gender)
	{
		this.gender = gender;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public void setFaxNumber(String faxNumber)
	{
		this.faxNumber = faxNumber;
	}

	public void setEmailAdress(String emailAdress)
	{
		this.emailAddress = emailAdress;
	}

	public void setMobileNumber(String mobileNumber)
	{
		this.mobileNumber = mobileNumber;
	}

	public void setHomeAddress(PAddress homeAddress)
	{
		this.homeAddress = homeAddress;
	}

	public void setHomePhoneNumber(String homePhoneNumber)
	{
		this.homePhoneNumber = homePhoneNumber;
	}

	public void setHomeEmailAdress(String homeEmailAdress)
	{
		this.homeEmailAddress = homeEmailAdress;
	}

	public void setApplications(Set<PApplication> applications)
	{
		Validate.notNull(applications, "applications-set must not be null");
		this.applications = applications;
	}
	
	// operations:
	@Transient
	public void addApplication(PApplication application)
	{
		Validate.notNull(application, "application must not be null");
		this.getApplications().add(application);
	}
	
	@Transient
	public String getEmployeeNumber()
	{
		return super.getNumber();
	}

	// constructor:
	public PPerson(String employeeNumber, String lastName)
	{
		super(employeeNumber);
		
		Validate.notNull(lastName, "last name must not be null");
		this.lastName = lastName;
	}
}
