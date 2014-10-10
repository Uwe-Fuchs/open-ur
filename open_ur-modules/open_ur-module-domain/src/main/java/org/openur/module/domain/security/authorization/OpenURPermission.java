package org.openur.module.domain.security.authorization;

import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.module.domain.application.OpenURApplication;

public class OpenURPermission
	extends IdentifiableEntityImpl
	implements IPermission
{
	private static final long serialVersionUID = -7018258281730222661L;
	
	// properties:
	private final OpenURApplication application;
	private final String permissionName;
	private final String description;
	private final PermissionScope permissionScope;

	// constructor:
	OpenURPermission(OpenURPermissionBuilder b)
	{
		super(b);

		this.application = b.getApp();
		this.permissionName = b.getPermissionName();
		this.description = b.getDescription();
		this.permissionScope = b.getPermissionScope();
	}

	@Override
	public PermissionScope getPermissionScope()
	{
		return permissionScope;
	}

	// accessors:
	public String getDescription()
	{
		return description;
	}

	@Override
	public String getPermissionName()
	{
		return permissionName;
	}

	@Override
	public OpenURApplication getApplication()
	{
		return this.application;
	}

	// operations:

}
