package org.openur.module.domain.security.authorization;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.IIdentifiableEntity;
import org.openur.module.domain.application.IApplication;

public interface IRole
	extends IIdentifiableEntity, Comparable<IRole>
{
	/**
	 * the role as a string-literal.
	 * 
	 * @return String.
	 */
	String getRoleName();

	/**
	 * the permissions assigned to this role, for all applications.
	 * 
	 * @return Map<IApplication, Set<IPermission>>
	 */
	Map<? extends IApplication, Set<? extends IPermission>> getAllPermissions();
	
	/**
	 * the permissions assigned to this role for one special application.
	 * 
	 * @param application : the application whose permissions are queried.
	 * 
	 * @return Set<IPermission>
	 */
	Set<? extends IPermission> getPermissions(IApplication application);
	
	// operations:
	/**
	 * has this role a certain permission?
	 * 
	 * @param application : the application whose permissions are queried.
	 * @param permission : the permission in question.
	 * 
	 * @return this role has the permission.
	 */
	default boolean containsPermission(IApplication application, IPermission permission)
	{
		Set<? extends IPermission> perms = this.getPermissions(application);
		
		return (perms != null && perms.contains(permission));
	}
	
	@Override
	default int compareTo(IRole other)
	{
		int comparison = new CompareToBuilder()
												.append(this.getRoleName(), other.getRoleName())
												.toComparison();
		
		if (comparison != 0)
		{
			return comparison;
		} else
		{
			// this should never happen because two roles shouldn't have the same name
			return this.getIdentifier().compareTo(other.getIdentifier());
		}
	}
}
