package org.openur.domain.testfixture.dummyimpl;

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
	private static final long serialVersionUID = 1L;
	
	private String identifier;
	private String role;
	private Map<MyApplicationImpl, Set<MyPermissionImpl>> permissions = new HashMap<>();	

	public MyRoleImpl(String identifier, String role)
	{
		super();
		
		this.identifier = identifier;
		this.role = role;
	}

	public void addPermissionSet(MyApplicationImpl app, Set<MyPermissionImpl> perms)
	{
		this.permissions.put(app, perms);
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
	public boolean containsPermission(IApplication application, IPermission permission)
	{
		Set<MyPermissionImpl> perms = this.permissions.get(application);
		
		return perms != null && perms.contains(permission);
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
