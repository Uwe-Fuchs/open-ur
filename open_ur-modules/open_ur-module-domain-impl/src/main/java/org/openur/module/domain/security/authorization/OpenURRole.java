package org.openur.module.domain.security.authorization;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.application.OpenURApplication;

public class OpenURRole
	extends IdentifiableEntityImpl
	implements IRole
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
	public String getRole()
	{
		return getRoleName();
	}

	@Override
	public Set<OpenURPermission> getPermissions(IApplication app)
	{
		return permissions.get(app);
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
	@Override
	public Map<OpenURApplication, Set<? extends IPermission>> getAllPermissions()
	{
		Map<OpenURApplication, Set<? extends IPermission>> map = new HashMap<>(this.permissions.size());
		
		for (OpenURApplication app : this.permissions.keySet())
		{
			map.put(app, this.getPermissions(app));
		}
		
		return map;
	}
}
