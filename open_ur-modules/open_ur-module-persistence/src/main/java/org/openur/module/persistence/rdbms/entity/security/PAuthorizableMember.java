package org.openur.module.persistence.rdbms.entity.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.openur.module.persistence.rdbms.entity.userstructure.POrgUnitMember;
import org.openur.module.persistence.rdbms.entity.userstructure.POrganizationalUnit;
import org.openur.module.persistence.rdbms.entity.userstructure.PPerson;

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
	private Set<POpenURRole> roles= new HashSet<>();
	
	//accessors:
	public Set<POpenURRole> getRoles()
	{
		return roles;
	}

	void setRoles(Set<POpenURRole> roles)
	{
		this.roles = roles;
	}

	// constructor:
	PAuthorizableMember(POrganizationalUnit orgUnit, PPerson person)
	{
		super(orgUnit, person);
	}
}
