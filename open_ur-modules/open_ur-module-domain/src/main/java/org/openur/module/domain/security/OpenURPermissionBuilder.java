package org.openur.module.domain.security;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.IdentifiableEntityBuilder;

public class OpenURPermissionBuilder
	extends IdentifiableEntityBuilder<OpenURPermissionBuilder>
{
	// properties:
	private IApplication app = null;
	private String permissionName = null;
	private String description = null;
	private PermissionScope permissionScope = null;

	// constructors:
	public OpenURPermissionBuilder(String id, String permissionName,
		PermissionScope permissionScope, IApplication app)
	{
		super(id);

		init(permissionName, permissionScope, app);
	}

	public OpenURPermissionBuilder(String permissionName,
		PermissionScope permissionScope, IApplication app)
	{
		super();

		init(permissionName, permissionScope, app);
	}

	private void init(String permissionName, PermissionScope permissionScope, IApplication app)
	{
		Validate.notEmpty(permissionName, "permission-name must not be empty!");
		Validate.notNull(permissionScope, "scope must not be null!");
		Validate.notNull(app, "app must not be null!");
		this.permissionName = permissionName;
		this.permissionScope = permissionScope;
		this.app = app;
	}

	// builder-methods:
	public OpenURPermissionBuilder description(String description)
	{
		this.description = description;
		return this;
	}

	// accessors:
	IApplication getApp()
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
