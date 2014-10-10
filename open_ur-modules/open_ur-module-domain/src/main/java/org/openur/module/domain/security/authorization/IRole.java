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
	
	// operations:
	@Override
	default int compareTo(IRole other)
	{
		int comparison = new CompareToBuilder()
												.append(this.getRole(), other.getRole())
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
