package org.openur.module.persistence.rdbms.entity.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.openur.module.domain.security.authorization.PermissionScope;
import org.openur.module.domain.util.DefaultsUtil;
import org.openur.module.persistence.rdbms.entity.AbstractOpenUrPersistable;
import org.openur.module.persistence.rdbms.entity.application.PApplication;

@Entity(name="PERMISSION")
public class PPermission
	extends AbstractOpenUrPersistable
{
	private static final long serialVersionUID = 15325108663313105L;

	// properties:
	@Column(name="PERMISSION_NAME", length=50, unique=true, nullable=false)
  private String permissionName;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="PERMISSION_SCOPE", nullable=false)
	@Enumerated(EnumType.STRING)
	private PermissionScope permissionScope = DefaultsUtil.getDefaultPermissionScope();

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="APPLICATION_ID", referencedColumnName="ID", unique=true, nullable=false)
  private PApplication application;

	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
		name="ROLES_PERMISSIONS",
		joinColumns={@JoinColumn(name="ID_PERMISSION", referencedColumnName="ID")},
		inverseJoinColumns={@JoinColumn(name="ID_ROLE", referencedColumnName="ID")}
	)
	private Set<POpenURRole> roles = new HashSet<>();

	// accessors:
	public String getPermissionName()
	{
		return permissionName;
	}

	public String getDescription()
	{
		return description;
	}

	public PermissionScope getPermissionScope()
	{
		return permissionScope;
	}

	public PApplication getApplication()
	{
		return application;
	}

	void setPermissionName(String permissionName)
	{
		this.permissionName = permissionName;
	}

	void setDescription(String description)
	{
		this.description = description;
	}

	void setPermissionScope(PermissionScope permissionScope)
	{
		this.permissionScope = permissionScope;
	}

	void setApplication(PApplication application)
	{
		this.application = application;
	}

	// constructor:
	PPermission()
	{
		super();
	}
}
