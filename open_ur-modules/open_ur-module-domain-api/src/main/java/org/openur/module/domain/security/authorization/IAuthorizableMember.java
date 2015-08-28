package org.openur.module.domain.security.authorization;

import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;

public interface IAuthorizableMember
	extends IOrgUnitMember
{
	/**
	 * indicates if a member has a certain permission (within his/her set of roles).
	 * 
	 * @param permission : the permission the member should have.
	 * 
	 * @return the member has the permission.
	 */
	default boolean hasPermission(IPermission permission)
	{
		Validate.notNull(permission, "permission must not be null!");
		Validate.notNull(permission.getApplication(), "application must be set in permission!");
		
		if (getPerson() == null || !getPerson().getApplications().contains(permission.getApplication()))
		{
			return false;
		}
		
		for (IRole role : getRoles())
		{
			if (role.containsPermission(permission.getApplication(), permission))
			{
				return true;
			}
		}
	
		return false;
	}

	/**
	 * indicates if a member has a certain role.
	 * 
	 * @param role : the role in which the member should take part in.
	 * 
	 * @return the member has the role.
	 */
	default boolean hasRole(IRole role)
	{
		Validate.notNull(getRoles(), "roles-list must be set!");
		
		return getRoles().contains(role);
	}

	/**
	 * returns the roles the member takes part in.
	 * 
	 * @return roles in a set (or empty set).
	 */
	Set<? extends IRole> getRoles();
}
