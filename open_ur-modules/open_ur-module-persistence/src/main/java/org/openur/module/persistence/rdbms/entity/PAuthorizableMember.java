package org.openur.module.persistence.rdbms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name="AUTHORIZABLE_MEMBER")
public class PAuthorizableMember
	extends POrgUnitMember
{
	private static final long serialVersionUID = 9192053190214891627L;
	
	// properties:	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
		name="ROLES_MEMBERS",
		joinColumns={@JoinColumn(name="ID_MEMBER", referencedColumnName="ID")},
		inverseJoinColumns={@JoinColumn(name="ID_ROLE", referencedColumnName="ID")}
	)
	private Set<PRole> roles= new HashSet<>();
	
	//accessors:
	public Set<PRole> getRoles()
	{
		return roles;
	}

	void setRoles(Set<PRole> roles)
	{
		this.roles = roles;
	}

	// constructor:
	PAuthorizableMember(POrganizationalUnit orgUnit, PPerson person)
	{
		super(orgUnit, person);
	}
}
