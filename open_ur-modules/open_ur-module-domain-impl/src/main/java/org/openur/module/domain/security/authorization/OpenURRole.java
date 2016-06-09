package org.openur.module.domain.security.authorization;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.application.OpenURApplication;

public class OpenURRole
	extends IdentifiableEntityImpl
	implements IRole, IPermissionsContainer<OpenURApplication, OpenURPermission>
{
	private static final long serialVersionUID = -5991333789511591402L;
	
	// properties:
	private final String roleName;
	private final String description;
	private final Map<OpenURApplication, Set<OpenURPermission>> permissions;

	// constructor:
	OpenURRole(OpenURRoleBuilder b)
	{
		super(b);

		this.roleName = b.getRoleName();
		this.description = b.getDescription();
		this.permissions = Collections.unmodifiableMap(b.getPermissions());
	}

	// accessors:
	@Override
	public String getRoleName()
	{
		return roleName;
	}

	public String getDescription()
	{
		return description;
	}

	@Override
	public Map<OpenURApplication, Set<OpenURPermission>> getAllPermissions()
	{
		return this.permissions;
	}
	
	// operations:
	@Override
	public boolean containsPermission(IApplication application, IPermission permission)
	{
		return IPermissionsContainer.super.containsPermission(application, permission);
	}	
}
