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

	public MyRoleImpl(String identifier, String role)
	{
		super();
		
		this.identifier = identifier;
		this.role = role;
	}

	@Override
	public String getIdentifier()
	{
		return this.identifier;
	}

	@Override
	public String getRoleName()
	{
		return this.role;
	}

	@Override
	public Set<? extends IPermission> getPermissions(IApplication application)
	{
		return permissions.get(application);
	}
	
	public void addPermissionSet(MyApplicationImpl app, Set<MyPermissionImpl> perms)
	{
		this.permissions.put(app, perms);
	}

	@Override
	public Map<MyApplicationImpl, Set<? extends IPermission>> getAllPermissions()
	{
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
