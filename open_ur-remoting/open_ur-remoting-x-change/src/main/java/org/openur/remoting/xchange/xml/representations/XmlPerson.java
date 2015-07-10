package org.openur.remoting.xchange.xml.representations;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.userstructure.person.Gender;
import org.openur.module.domain.userstructure.person.Title;

public class XmlPerson
	extends XmlUserStructureBase
{
	// properties:
	@XmlAttribute
	private Gender gender;
	
	@XmlAttribute(required = true)
	private Title title;
	
	@XmlAttribute
	private String firstName;
	
	@XmlAttribute(required = true)
	private String lastName;	
  
	@XmlAttribute
	private String phoneNumber;
	
	@XmlAttribute
	private String faxNumber;
	
	@XmlAttribute
	private String emailAddress;
	
	@XmlAttribute
  private String mobileNumber;

	@XmlElement
  private XmlAddress homeAddress;
  
	@XmlAttribute
  private String homePhoneNumber;
  
	@XmlAttribute
  private String homeEmailAddress;

  @XmlElementWrapper(name = "applications", required = true)
  @XmlElement(name = "application")
  private Set<XmlApplication> applications = new HashSet<>();
	
	// accessors:
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

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	public String getMobileNumber()
	{
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber)
	{
		this.mobileNumber = mobileNumber;
	}

	public XmlAddress getHomeAddress()
	{
		return homeAddress;
	}

	public void setHomeAddress(XmlAddress homeAddress)
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

	public String getHomeEmailAddress()
	{
		return homeEmailAddress;
	}

	public void setHomeEmailAddress(String homeEmailAddress)
	{
		this.homeEmailAddress = homeEmailAddress;
	}

	public Set<XmlApplication> getApplications()
	{
		return applications;
	}

	public void setApplications(Set<XmlApplication> applications)
	{
		this.applications = applications;
	}
	
	public void addApplication(XmlApplication application)
	{
		this.applications.add(application);
	}

	// constructors:
	public XmlPerson(String employeeNumber, String lastName)
	{
		super(employeeNumber);
		
		Validate.notNull(lastName, "last name must not be null");
		this.lastName = lastName;
	}

	@SuppressWarnings("unused")
	private XmlPerson()
	{
		// JAXB
		super();
	}
}
