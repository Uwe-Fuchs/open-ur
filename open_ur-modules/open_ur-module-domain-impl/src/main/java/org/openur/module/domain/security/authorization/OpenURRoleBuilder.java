package org.openur.module.domain.security.authorization;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.IdentifiableEntityBuilder;
import org.openur.module.domain.application.OpenURApplication;

public class OpenURRoleBuilder
	extends IdentifiableEntityBuilder<OpenURRoleBuilder>
	implements IPermissionContainerBuilder
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

	// accessors:
	String getRoleName()
	{
		return roleName;
	}

	String getDescription()
	{
		return description;
	}

	@Override
	public Map<OpenURApplication, Set<OpenURPermission>> getPermissions()
	{
		return permissions;
	}
}
