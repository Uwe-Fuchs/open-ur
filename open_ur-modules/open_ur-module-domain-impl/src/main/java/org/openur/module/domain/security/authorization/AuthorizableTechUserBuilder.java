package org.openur.module.domain.security.authorization;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.userstructure.technicaluser.AbstractTechnicalUser.AbstractTechnicalUserBuilder;
import org.openur.module.util.data.Status;

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

	@Override
	public AuthorizableTechUserBuilder identifier(String identifier)
	{
		return (AuthorizableTechUserBuilder) super.identifier(identifier);
	}

	@Override
	public AuthorizableTechUserBuilder creationDate(LocalDateTime creationDate)
	{
		return (AuthorizableTechUserBuilder) super.creationDate(creationDate);
	}

	@Override
	public AuthorizableTechUserBuilder lastModifiedDate(LocalDateTime changeDate)
	{
		return (AuthorizableTechUserBuilder) super.lastModifiedDate(changeDate);
	}

	@Override
	public AuthorizableTechUserBuilder number(String number)
	{
		return (AuthorizableTechUserBuilder) super.number(number);
	}

	@Override
	public AuthorizableTechUserBuilder status(Status status)
	{
		return (AuthorizableTechUserBuilder) super.status(status);
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