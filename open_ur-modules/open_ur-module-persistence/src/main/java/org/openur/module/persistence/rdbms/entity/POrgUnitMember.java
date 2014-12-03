package org.openur.module.persistence.rdbms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.Validate;

@Entity(name="ORG_UNIT_MEMBER")
public class POrgUnitMember
	extends AbstractOpenUrPersistable
{
	private static final long serialVersionUID = -7790268803941598263L;

	// properties:
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="ORG_UNIT_ID", referencedColumnName="ID")
	private POrganizationalUnit orgUnit;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="PERSON_ID", referencedColumnName="ID")
	private PPerson person;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
		name="ROLES_MEMBERS",
		joinColumns={@JoinColumn(name="ID_MEMBER", referencedColumnName="ID")},
		inverseJoinColumns={@JoinColumn(name="ID_ROLE", referencedColumnName="ID")}
	)
	private Set<PRole> roles= new HashSet<>();
	
	// accessors:
	public POrganizationalUnit getOrgUnit()
	{
		return orgUnit;
	}

	public PPerson getPerson()
	{
		return person;
	}
	
	public Set<PRole> getRoles()
	{
		return roles;
	}

	void setOrgUnit(POrganizationalUnit orgUnit)
	{
		this.orgUnit = orgUnit;
	}

	void setPerson(PPerson person)
	{
		this.person = person;
	}

	void setRoles(Set<PRole> roles)
	{
		this.roles = roles;
	}

	// protected:
	POrgUnitMember(POrganizationalUnit orgUnit, PPerson person)
	{
		super();
		
		Validate.notNull(orgUnit, "org-unit must not be null!");
		Validate.notNull(person, "person must not be null!");
		
		this.orgUnit = orgUnit;
		this.person = person;
	}
}
