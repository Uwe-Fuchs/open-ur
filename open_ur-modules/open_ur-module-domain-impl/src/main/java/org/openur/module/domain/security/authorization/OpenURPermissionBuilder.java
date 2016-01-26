package org.openur.module.domain.security.authorization;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.IdentifiableEntityBuilder;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.util.DefaultsUtil;
import org.openur.module.util.data.PermissionScope;

public class OpenURPermissionBuilder
	extends IdentifiableEntityBuilder<OpenURPermissionBuilder>
{
	// properties:
	private OpenURApplication app = null;
	private String permissionText = null;
	private PermissionScope permissionScope = DefaultsUtil.getDefaultPermissionScope();

	public OpenURPermissionBuilder(String permissionText, OpenURApplication app)
	{
		super();

		Validate.notEmpty(permissionText, "permission-text must not be empty!");
		Validate.notNull(app, "app must not be null!");
		
		this.permissionText = permissionText;
		this.app = app;
	}

	public OpenURPermissionBuilder permissionScope(PermissionScope permissionScope)
	{
		Validate.notNull(permissionScope, "permission-scope must not be null!");
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

	String getPermissionText()
	{
		return permissionText;
	}

	PermissionScope getPermissionScope()
	{
		return permissionScope;
	}
}
