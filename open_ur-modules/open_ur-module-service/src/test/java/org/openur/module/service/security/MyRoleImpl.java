package org.openur.module.service.security;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;

public class MyRoleImpl
	implements IRole
{
	private String identifier;
	private String role;
	private Map<MyApplicationImpl, Set<MyPermissionImpl>> permissions = new HashMap<>();

	@Override
	public String getIdentifier()
	{
		return this.identifier;
	}

	@Override
	public String getRole()
	{
		return this.role;
	}

	@Override
	public Map<MyApplicationImpl, Set<? extends IPermission>> getAllPermissions()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<? extends IPermission> getPermissions(IApplication application)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalDateTime getLastModifiedDate()
	{
		return null;
	}

	@Override
	public LocalDateTime getCreationDate()
	{
		return null;
	}
}
