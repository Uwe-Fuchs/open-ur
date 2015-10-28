package org.openur.module.persistence.rdbms.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.security.authorization.PermissionScope;
import org.openur.module.domain.util.DefaultsUtil;

@Entity(name="PERMISSION")
@Table(uniqueConstraints={@UniqueConstraint(name="UNQ_PERM_TEXT_APP_ID", columnNames={"PERMISSION_TEXT", "APPLICATION_ID"})},
		indexes = {@Index(columnList="APPLICATION_ID", name="IDX_PERMISSION_APPLICATION")})
public class PPermission
	extends AbstractOpenUrPersistable
{
	private static final long serialVersionUID = 15325108663313105L;

	// properties:
	@Column(name="PERMISSION_TEXT", length=50, nullable=false)
  private String permissionText;
	
	@Column(name="PERMISSION_SCOPE", nullable=false)
	@Enumerated(EnumType.STRING)
	private PermissionScope permissionScope = DefaultsUtil.getDefaultPermissionScope();

	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinColumn(name="APPLICATION_ID", referencedColumnName="ID", nullable=false)
  private PApplication application;

	// accessors:
	public String getPermissionText()
	{
		return permissionText;
	}

	public PermissionScope getPermissionScope()
	{
		return permissionScope;
	}

	public PApplication getApplication()
	{
		return application;
	}

	public void setPermissionScope(PermissionScope permissionScope)
	{
		Validate.notNull(permissionScope, "permission-scope must not be null!");		
		this.permissionScope = permissionScope;
	}

	public void setApplication(PApplication application)
	{
		Validate.notNull(application, "application must not be null!");
		this.application = application;
	}

	// constructors:
	public PPermission(String permissionText, PApplication application)
	{
		this();

		Validate.notEmpty(permissionText, "permission-text must not be empty!");
		Validate.notNull(application, "application must not be null!");		
		
		this.permissionText = permissionText;
		this.application = application;
	}

	private PPermission()
	{
		// JPA
		super();
	}
}
