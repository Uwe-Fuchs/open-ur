package org.openur.module.domain.security.authorization;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.module.domain.application.IApplication;

public class OpenURRole
	extends IdentifiableEntityImpl
	implements IRole
{
	private static final long serialVersionUID = -5991333789511591402L;
	
	// properties:
	private final String roleName;
	private final String description;
	private final Map<IApplication, Set<IPermission>> permissions;

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
	public String getRole()
	{
		return getRoleName();
	}

	@Override
	public Set<IPermission> getPermissions(IApplication app)
	{
		return permissions.get(app);
	}
	
	@Override
	public Map<IApplication, Set<IPermission>> getAllPermissions()
	{
		return this.permissions;
	}

	public String getRoleName()
	{
		return roleName;
	}

	public String getDescription()
	{
		return description;
	}
	
	// operations:
	
}
