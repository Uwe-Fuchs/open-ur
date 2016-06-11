package org.openur.module.domain.userstructure.technicaluser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IPermissionsContainer;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.IPermissionContainerBuilder;
import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.UserStructureBaseBuilder;

public class TechnicalUser
	extends UserStructureBase
	implements ITechnicalUser, IPermissionsContainer<OpenURApplication, OpenURPermission>
{
	private static final long serialVersionUID = 4476135911103120283L;
	
	// properties:
	private final Map<OpenURApplication, Set<OpenURPermission>> permissions;

	// constructor:
	TechnicalUser(TechnicalUserBuilder b)
	{
		super(b);

		this.permissions = Collections.unmodifiableMap(b.getPermissions());
	}

	// accessors:
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

	// builder:
	public static class TechnicalUserBuilder
		extends UserStructureBaseBuilder<TechnicalUserBuilder>
		implements IPermissionContainerBuilder
	{
		// properties:
		private Map<OpenURApplication, Set<OpenURPermission>> permissions = new HashMap<>();
	  
	  // constructors:
		public TechnicalUserBuilder(String techUserNumber)
		{
			super(techUserNumber);
		}
		
		// accessors:
		@Override
		public Map<OpenURApplication, Set<OpenURPermission>> getPermissions()
		{
			return permissions;
		}
		
		public TechnicalUser build()
		{
			return new TechnicalUser(this);
		}
	}
}
