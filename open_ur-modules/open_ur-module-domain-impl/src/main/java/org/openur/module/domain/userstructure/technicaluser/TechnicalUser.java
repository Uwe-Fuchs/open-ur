package org.openur.module.domain.userstructure.technicaluser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.UserStructureBaseBuilder;

public class TechnicalUser
	extends UserStructureBase
	implements ITechnicalUser
{
	private static final long serialVersionUID = 4476135911103120283L;
	
	// properties:
	private final Map<OpenURApplication, Set<OpenURPermission>> permissions;

	// accessors:	
	@Override
	public Set<OpenURPermission> getPermissions(IApplication application)
	{
		return permissions.get(application);
	}	

	@Override
	public Set<OpenURApplication> getApplications()
	{
		return this.permissions.keySet();
	}

	// constructor:
	TechnicalUser(TechnicalUserBuilder b)
	{
		super(b);

		this.permissions = Collections.unmodifiableMap(b.getPermissions());
	}

	// builder:
	public static class TechnicalUserBuilder
		extends UserStructureBaseBuilder<TechnicalUserBuilder>
	{
		// properties:
		private Map<OpenURApplication, Set<OpenURPermission>> permissions = new HashMap<>();
		
		// builder-methods:
		public TechnicalUserBuilder permissions(Set<OpenURPermission> perms)
		{		
			Validate.notNull(perms, "permissions must not be null!");
			
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
	  
	  // constructors:
		public TechnicalUserBuilder(String techUserNumber)
		{
			super(techUserNumber);
		}
		
		// accessors:
		Map<OpenURApplication, Set<OpenURPermission>> getPermissions()
		{
			return permissions;
		}		
		
		public TechnicalUser build()
		{
			return new TechnicalUser(this);
		}
	}
}
