package org.openur.module.domain.security.authorization;

import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.util.data.PermissionScope;

public class OpenURPermission
	extends IdentifiableEntityImpl
	implements IPermission
{
	private static final long serialVersionUID = -7018258281730222661L;
	
	// properties:
	private final OpenURApplication application;
	private final String permissionText;
	private final PermissionScope permissionScope;

	// constructor:
	OpenURPermission(OpenURPermissionBuilder b)
	{
		super(b);

		this.application = b.getApp();
		this.permissionText = b.getPermissionText();
		this.permissionScope = b.getPermissionScope();
	}

	@Override
	public PermissionScope getPermissionScope()
	{
		return permissionScope;
	}

	@Override
	public String getPermissionText()
	{
		return permissionText;
	}

	@Override
	public OpenURApplication getApplication()
	{
		return this.application;
	}

	// operations:

}
