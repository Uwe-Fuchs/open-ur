package org.openur.domain.testfixture.dummyimpl;

import java.time.LocalDateTime;

import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.PermissionScope;

public class MyPermissionImpl
	implements IPermission
{
	private String identifier;
	private String permissionName;
	private IApplication application;

	public MyPermissionImpl(String identifier, String permissionName, IApplication application)
	{
		super();
		
		this.identifier = identifier;
		this.permissionName = permissionName;
		this.application = application;
	}

	@Override
	public String getIdentifier()
	{
		return this.identifier;
	}

	@Override
	public IApplication getApplication()
	{
		return this.application;
	}

	@Override
	public String getPermissionName()
	{
		return this.permissionName;
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

	@Override
	public PermissionScope getPermissionScope()
	{
		return null;
	}
}
