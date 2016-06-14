package org.openur.module.domain.security.authorization;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.userstructure.technicaluser.AbstractTechnicalUser.AbstractTechnicalUserBuilder;

// builder-class:
public class AuthorizableTechUserBuilder
	extends AbstractTechnicalUserBuilder<AuthorizableTechUserBuilder>
	implements IPermissionContainerBuilder
{
	// properties:
	private Map<OpenURApplication, Set<OpenURPermission>> permissions = new HashMap<>();
	
	// constructor:
	public AuthorizableTechUserBuilder(String techUserNumber)
	{
		super(techUserNumber);
	}
	
	// builder-methods:
	public AuthorizableTechUserBuilder permissions(Set<OpenURPermission> perms)
	{
		IPermissionContainerBuilder.super.permissions(perms, this);
		
		return this;
	}

	// accessors:
	@Override
	public Map<OpenURApplication, Set<OpenURPermission>> getPermissions()
	{
		return permissions;
	}
	
	// builder:
	@Override
	public AuthorizableTechUser build()
	{
		super.build();
		
		return new AuthorizableTechUser(this);
	}
}