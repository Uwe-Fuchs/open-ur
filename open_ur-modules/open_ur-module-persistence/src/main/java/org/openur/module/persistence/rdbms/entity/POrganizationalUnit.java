package org.openur.module.persistence.rdbms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity(name="ORGANIZATIONAL_UNIT")
public class POrganizationalUnit
	extends PUserStructureBase
{
	private static final long serialVersionUID = -8093388723274386419L;

	// properties:
	@ManyToOne(fetch=FetchType.EAGER, optional=true)
	@JoinColumn(name="SUPER_OU_ID", referencedColumnName="ID")
	private POrganizationalUnit superOu;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=true)
	@JoinColumn(name="ROOT_OU_ID", referencedColumnName="ID")
	private POrganizationalUnit rootOu;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="ORG_UNIT_MEMBER_ID", referencedColumnName="ID")
	private Set<POrgUnitMember> members = new HashSet<>();
	
	@Column(name="NAME", length=50, nullable=false)
  private String name;
	
	@Column(name="SHORT_NAME", length=20)
  private String shortName;
	
	@Column(name="DESCRIPTION")
	private String description;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ADDRESS_ID", referencedColumnName="ID")
  private PAddress address;
  
	@Column(name="EMAIL")
  private String emailAddress;
		
	// accessors
	public POrganizationalUnit getSuperOu()
	{
		return superOu;
	}

	void setSuperOu(POrganizationalUnit superOu)
	{
		this.superOu = superOu;
	}

	public POrganizationalUnit getRootOu()
	{
		return rootOu;
	}

	void setRootOu(POrganizationalUnit rootOu)
	{
		this.rootOu = rootOu;
	}

	public Set<POrgUnitMember> getMembers()
	{
		return members;
	}

	void setMembers(Set<POrgUnitMember> members)
	{
		this.members = members;
	}

	public String getName()
	{
		return name;
	}

	void setName(String name)
	{
		this.name = name;
	}

	public String getShortName()
	{
		return shortName;
	}

	void setShortName(String shortName)
	{
		this.shortName = shortName;
	}

	public String getDescription()
	{
		return description;
	}

	void setDescription(String description)
	{
		this.description = description;
	}

	public PAddress getAddress()
	{
		return address;
	}

	void setAddress(PAddress address)
	{
		this.address = address;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}

	void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}
	
	@Transient
	public String getOrgUnitNumber()
	{
		return super.getNumber();
	}
	
	@Transient
	public void setOrgUnitNumber(String employeeNumber)
	{
		super.setNumber(employeeNumber);
	}

	// constructor:
	POrganizationalUnit()
	{
		super();
	}
}
