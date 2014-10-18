package org.openur.module.persistence.rdbms.entity.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.openur.module.domain.security.authorization.PermissionScope;
import org.openur.module.persistence.rdbms.entity.AbstractOpenUrPersistable;

@Entity(name="PERMISSION")
public class PPermission
	extends AbstractOpenUrPersistable
{
	
	// properties:
	@Column(name="PERMISSION_NAME", length=50, nullable=false)
  private String permissionName;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="PERMISSION_SCOPE")
	@Enumerated(EnumType.STRING)
	private PermissionScope permissionScope;

	PPermission()
	{
		// JPA
	}
}
