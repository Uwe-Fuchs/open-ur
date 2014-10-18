package org.openur.module.persistence.rdbms.entity.userstructure;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="ORGANIZATIONAL_UNIT")
public class POrganizationalUnit
	extends PUserStructureBase
{
	
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

	// constructor:
	POrganizationalUnit()
	{
		super();
	}
}
