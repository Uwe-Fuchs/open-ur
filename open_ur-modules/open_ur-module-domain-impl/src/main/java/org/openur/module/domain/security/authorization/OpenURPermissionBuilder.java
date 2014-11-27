package org.openur.module.domain.security.authorization;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.IdentifiableEntityBuilder;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.util.DefaultsUtil;

public class OpenURPermissionBuilder
	extends IdentifiableEntityBuilder<OpenURPermissionBuilder>
{
	// properties:
	private OpenURApplication app = null;
	private String permissionName = null;
	private String description = null;
	private PermissionScope permissionScope = DefaultsUtil.getDefaultPermissionScope();

	public OpenURPermissionBuilder(String permissionName, OpenURApplication app)
	{
		super();

		Validate.notEmpty(permissionName, "permission-name must not be empty!");
		Validate.notNull(app, "app must not be null!");
		
		this.permissionName = permissionName;
		this.app = app;
	}

	// builder-methods:
	public OpenURPermissionBuilder description(String description)
	{
		this.description = description;
		return this;
	}
	
	public OpenURPermissionBuilder permissionScope(PermissionScope permissionScope)
	{
		this.permissionScope = permissionScope;
		return this;
	}
	
	public OpenURPermission build()
	{
		return new OpenURPermission(this);
	}

	// accessors:
	OpenURApplication getApp()
	{
		return app;
	}

	String getPermissionName()
	{
		return permissionName;
	}

	String getDescription()
	{
		return description;
	}

	PermissionScope getPermissionScope()
	{
		return permissionScope;
	}
}
