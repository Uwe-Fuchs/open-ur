package org.openur.module.persistence.rdbms.entity.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.openur.module.persistence.rdbms.entity.AbstractOpenUrPersistable;;

@Entity(name="ROLE")
public class PRole
	extends AbstractOpenUrPersistable
{
	private static final long serialVersionUID = 4450642055287859526L;

	// properties:
	@Column(name="ROLE_NAME", length=50, nullable=false, unique=true)
  private String roleName;
	
	@Column(name="DESCRIPTION")
	private String description;

	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
		name="ROLES_PERMISSIONS",
		joinColumns={@JoinColumn(name="ID_ROLE", referencedColumnName="ID")},
		inverseJoinColumns={@JoinColumn(name="ID_PERMISSION", referencedColumnName="ID")}
	)
	private Set<PPermission> permissions = new HashSet<>();
	
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
		name="ROLES_MEMBERS",
		joinColumns={@JoinColumn(name="ID_ROLE", referencedColumnName="ID")},
		inverseJoinColumns={@JoinColumn(name="ID_MEMBER", referencedColumnName="ID")}
	)
	private Set<PAuthorizableMember> members = new HashSet<>();
		
	// accessors:
	public String getRoleName()
	{
		return roleName;
	}

	public String getDescription()
	{
		return description;
	}

	public Set<PPermission> getPermissions()
	{
		return permissions;
	}

	void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	void setDescription(String description)
	{
		this.description = description;
	}

	void setPermissions(Set<PPermission> permissions)
	{
		this.permissions = permissions;
	}
	
	// constructor:
	PRole()
	{
		super();
	}	
}
