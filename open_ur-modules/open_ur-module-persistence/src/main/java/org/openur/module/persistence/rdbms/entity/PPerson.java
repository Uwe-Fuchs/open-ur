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

	void setTitle(Title title)
	{
		this.title = title;
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

	void setApplications(Set<PApplication> applications)
	{
		this.applications = applications;
	}
	
	@Transient
	public String getEmployeeNumber()
	{
		return super.getNumber();
	}
	
	@Transient
	public void setEmployeeNumber(String employeeNumber)
	{
		super.setNumber(employeeNumber);
	}
	
	// operations:
	@Transient
	void addApplication(PApplication application)
	{
		this.getApplications().add(application);
	}

	// constructor:
	PPerson()
	{
		super();
	}
}
