package org.openur.module.persistence.rdbms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.Validate;

@Entity(name="ORG_UNIT_MEMBER")
@Table(indexes = {@Index(columnList="ORG_UNIT_ID", name="IDX_ORG_UNIT_MEMBER_ORG_UNIT"),
		@Index(columnList="PERSON_ID", name="IDX_ORG_UNIT_MEMBER_PERSON")})
public class POrgUnitMember
	extends AbstractOpenUrPersistable
{
	private static final long serialVersionUID = -7790268803941598263L;

	// properties:
	@ManyToOne
	@JoinColumn(name="ORG_UNIT_ID", referencedColumnName="ID", nullable=false)
	private POrganizationalUnit orgUnit;
	
	@ManyToOne
	@JoinColumn(name="PERSON_ID", referencedColumnName="ID", nullable=false)
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

	public void setRoles(Set<PRole> roles)
	{
		Validate.notNull(roles, "roles-collection must not be null!");		
		this.roles = roles;
	}
	
	// operations:
	@Transient
	public void addRole(PRole role)
	{
		Validate.notNull(role, "role must not be null!");		
		this.getRoles().add(role);
	}

	// constructors:
	public POrgUnitMember(POrganizationalUnit orgUnit, PPerson person)
	{
		this();
		
		Validate.notNull(orgUnit, "org-unit must not be null!");
		Validate.notNull(person, "person must not be null!");
		
		this.orgUnit = orgUnit;
		this.person = person;
	}

	private POrgUnitMember()
	{
		// JPA
		super();
	}
}
