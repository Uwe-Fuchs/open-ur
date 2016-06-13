package org.openur.module.domain.security.authorization;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.userstructure.technicaluser.AbstractTechnicalUser;

public class AuthorizableTechUser
	extends AbstractTechnicalUser
	implements IAuthorizableTechUser, IPermissionsContainer<OpenURApplication, OpenURPermission>
{	
	private static final long serialVersionUID = 8496242194231500596L;
	
	// properties:
	private final Map<OpenURApplication, Set<OpenURPermission>> permissions;
	
	public AuthorizableTechUser(AuthorizableTechUserBuilder b)
	{
		super(b);

		this.permissions = Collections.unmodifiableMap(b.getPermissions());
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
