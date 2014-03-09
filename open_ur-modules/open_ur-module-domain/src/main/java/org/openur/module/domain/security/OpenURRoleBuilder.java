package org.openur.module.domain.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.IdentifiableEntityBuilder;

public class OpenURRoleBuilder
	extends IdentifiableEntityBuilder<OpenURRoleBuilder>
{
	// properties:
	private String roleName = null;
	private String description = null;
	private Map<IApplication, Set<IPermission>> permissions = new HashMap<IApplication, Set<IPermission>>();

	// constructors:
	public OpenURRoleBuilder(String identifer, String roleName)
	{
		super(identifer);

		init(roleName);
	}

	public OpenURRoleBuilder(String roleName)
	{
		super();

		init(roleName);
	}
	
	private void init(String roleName)
	{
		Validate.notEmpty(roleName, "role-name must not be empty!");
		this.roleName = roleName;
	}
	
	// builder-methods:
	public OpenURRoleBuilder description(String description)
	{
		this.description = description;
		
		return this;
	}
	
	public OpenURRoleBuilder permissions(Set<IPermission> perms)
	{
		Map<IApplication, Set<IPermission>> permsLocal = new HashMap<IApplication, Set<IPermission>>();
		
		for (IPermission p : perms)
		{
			Set<IPermission> sp = permsLocal.get(p.getApp());
			
			if (sp == null)
			{
				sp = new HashSet<IPermission>();
				permsLocal.put(p.getApp(), sp);
			}

			sp.add(p);
		}
		
		// make permission-sets unmodifiable:
		for (IApplication app : permsLocal.keySet())
		{
			this.permissions.put(app, Collections.unmodifiableSet(permsLocal.get(app)));
		}
		
		return this;
	}

	// accessors:
	String getRoleName()
	{
		return roleName;
	}

	String getDescription()
	{
		return description;
	}

	Map<IApplication, Set<IPermission>> getPermissions()
	{
		return permissions;
	}
}
