package org.openur.module.persistence.rdbms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name="ORGANIZATIONAL_UNIT")
@Table(indexes = {@Index(columnList="SUPER_OU_ID", name="IDX_ORG_UNIT_SUPER_ORG_UNIT"),
		@Index(columnList="ROOT_OU_ID", name="IDX_ORG_UNIT_ROOT_ORG_UNIT"),
		@Index(columnList="ADDRESS_ID", name="IDX_ORG_UNIT_ADDRESS")})
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
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="id")
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
	
	// operations:
	@Transient
	void addMember(POrgUnitMember member)
	{
		this.getMembers().add(member);
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
