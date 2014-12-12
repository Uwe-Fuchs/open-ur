package org.openur.module.persistence.rdbms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;

@Entity(name="ROLE")
@Table(uniqueConstraints={@UniqueConstraint(name="UNQ_ROLE_NAME", columnNames={"ROLE_NAME"})})
public class PRole
	extends AbstractOpenUrPersistable
{
	private static final long serialVersionUID = 4450642055287859526L;

	// properties:
	@Column(name="ROLE_NAME", length=50, nullable=false)
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

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setPermissions(Set<PPermission> permissions)
	{
		Validate.notNull(permissions, "permissions-set must not be null!");
		this.permissions = permissions;
	}
	
	// operations:
	@Transient
	public void addPermssion(PPermission permission)
	{
		Validate.notNull(permission, "permission must not be null!");
		this.getPermissions().add(permission);
	}
	
	// constructors:
	public PRole(String roleName)
	{
		this();

		Validate.notEmpty(roleName, "role-name must not be empty!");
		this.roleName = roleName;
	}

	private PRole()
	{
		// JPA
		super();
	}
}
