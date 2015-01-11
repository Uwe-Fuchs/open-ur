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

import org.apache.commons.lang3.Validate;

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

	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinColumn(name="ADDRESS_ID", referencedColumnName="ID")
  private PAddress address;
  
	@Column(name="EMAIL")
  private String emailAddress;
		
	// accessors
	public POrganizationalUnit getSuperOu()
	{
		return superOu;
	}

	public void setSuperOu(POrganizationalUnit superOu)
	{
		this.superOu = superOu;
	}

	public POrganizationalUnit getRootOu()
	{
		return rootOu;
	}

	public void setRootOu(POrganizationalUnit rootOu)
	{
		this.rootOu = rootOu;
	}

	public Set<POrgUnitMember> getMembers()
	{
		return members;
	}

	public String getName()
	{
		return name;
	}

	public String getShortName()
	{
		return shortName;
	}

	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public PAddress getAddress()
	{
		return address;
	}

	public void setAddress(PAddress address)
	{
		this.address = address;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	public void setMembers(Set<POrgUnitMember> members)
	{
		Validate.notNull(members, "members-set must not be null!");	
		this.members = members;
	}
	
	// operations:
	@Transient
	public void addMember(POrgUnitMember member)
	{
		Validate.notNull(member, "member must not be null!");	
		this.getMembers().add(member);
	}
	
	@Transient
	public String getOrgUnitNumber()
	{
		return super.getNumber();
	}

	// constructors:
	public POrganizationalUnit(String orgUnitNumber, String name)
	{
		super(orgUnitNumber);
		
		Validate.notEmpty(name, "name must not be empty!");
		this.name = name;
	}

	@SuppressWarnings("unused")
	private POrganizationalUnit()
	{
		// JPA
		super();
	}
}
