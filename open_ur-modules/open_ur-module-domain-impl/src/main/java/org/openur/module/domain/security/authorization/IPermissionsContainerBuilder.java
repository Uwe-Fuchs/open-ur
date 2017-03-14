package org.openur.module.domain.security.authorization;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.IdentifiableEntityBuilder;
import org.openur.module.domain.application.OpenURApplication;

/**
 * Builder-Interface for PermissionContainers.
 * 
 * @author info@uwefuchs.com
 */
public interface IPermissionsContainerBuilder
{
	/**
	 * 
	 * @return Map with all permissions.
	 */
	Map<OpenURApplication, Set<OpenURPermission>> getPermissions();
	
	/**
	 * builder-method for permissions-container
	 * 
	 * @param perms Set containing permissions.
	 * @param builder the builder-instance that holds the permissions-container.
	 * 
	 * @return {@link IdentifiableEntityBuilder}
	 */
	default <I> I permissions(Set<OpenURPermission> perms, I builder)
	{
		Validate.notNull(perms, "permissions must not be null!");
		
		Map<OpenURApplication, Set<OpenURPermission>> permsLocal = perms
			.stream()
			.collect(Collectors.groupingBy(OpenURPermission::getApplication, Collectors.toSet()));
		
		// make permission-sets unmodifiable:
		for (OpenURApplication app : permsLocal.keySet())
		{
			getPermissions().put(app, Collections.unmodifiableSet(permsLocal.get(app)));
		}
		
		return builder;
	}
}
