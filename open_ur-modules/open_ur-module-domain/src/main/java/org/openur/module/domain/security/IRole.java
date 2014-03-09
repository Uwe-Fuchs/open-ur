package org.openur.module.domain.security;

import java.util.Map;
import java.util.Set;

import org.openur.module.domain.IIdentifiableEntity;

public interface IRole
	extends IIdentifiableEntity, Comparable<IRole>
{
	/**
	 * the role-literal.
	 * 
	 * @return String.
	 */
	String getRole();

	/**
	 * the permissions assigned to this role, for all applications.
	 * 
	 * @return Map<IApplication, Set<IPermission>>
	 */
	Map<IApplication, Set<IPermission>> getAllPermissions();
	
	/**
	 * the permissions assigned to this role for one special application.
	 * 
	 * @param application the application whose permissions are queried.
	 * 
	 * @return Set<IPermission>
	 */
	Set<IPermission> getPermissions(IApplication application);
}
