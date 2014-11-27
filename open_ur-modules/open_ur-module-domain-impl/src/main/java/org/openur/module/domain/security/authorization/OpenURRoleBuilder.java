package org.openur.module.domain.security.authorization;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.IdentifiableEntityBuilder;
import org.openur.module.domain.application.OpenURApplication;

public class OpenURRoleBuilder
	extends IdentifiableEntityBuilder<OpenURRoleBuilder>
{
	// properties:
	private String roleName = null;
	private String description = null;
	private Map<OpenURApplication, Set<OpenURPermission>> permissions = new HashMap<>();

	public OpenURRoleBuilder(String roleName)
	{
		super();

		Validate.notEmpty(roleName, "role-name must not be empty!");
		
		this.roleName = roleName;
	}
	
	// builder-methods:
	public OpenURRoleBuilder description(String description)
	{
		this.description = description;
		
		return this;
	}
	
	public OpenURRole build() 
	{
		return new OpenURRole(this);
	}
	
	public OpenURRoleBuilder permissions(Set<OpenURPermission> perms)
	{		
		Map<OpenURApplication, Set<OpenURPermission>> permsLocal = perms
			.stream()
			.collect(Collectors.groupingBy(OpenURPermission::getApplication, Collectors.toSet()));
		
		// make permission-sets unmodifiable:
		for (OpenURApplication app : permsLocal.keySet())
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

	Map<OpenURApplication, Set<OpenURPermission>> getPermissions()
	{
		return permissions;
	}
}
