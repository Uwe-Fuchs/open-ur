package org.openur.module.persistence.rdbms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.openur.module.domain.security.authorization.PermissionScope;
import org.openur.module.domain.util.DefaultsUtil;

@Entity(name="PERMISSION")
@Table(uniqueConstraints={@UniqueConstraint(name="UNQ_PERMISSION_NAME", columnNames={"PERMISSION_NAME"})},
		indexes = {@Index(columnList="APPLICATION_ID", name="IDX_PERMISSION_APPLICATION")})
public class PPermission
	extends AbstractOpenUrPersistable
{
	private static final long serialVersionUID = 15325108663313105L;

	// properties:
	@Column(name="PERMISSION_NAME", length=50, nullable=false)
  private String permissionName;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="PERMISSION_SCOPE", nullable=false)
	@Enumerated(EnumType.STRING)
	private PermissionScope permissionScope = DefaultsUtil.getDefaultPermissionScope();

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="APPLICATION_ID", referencedColumnName="ID", nullable=false)
  private PApplication application;

	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
		name="ROLES_PERMISSIONS",
		joinColumns={@JoinColumn(name="ID_PERMISSION", referencedColumnName="ID")},
		inverseJoinColumns={@JoinColumn(name="ID_ROLE", referencedColumnName="ID")}
	)
	private Set<PRole> roles = new HashSet<>();

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
