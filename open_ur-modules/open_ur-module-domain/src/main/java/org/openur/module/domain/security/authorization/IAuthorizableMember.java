package org.openur.module.domain.security.authorization;

import java.util.Set;

import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;

public interface IAuthorizableMember
	extends IOrgUnitMember
{
	/**
	 * indicates if a member has a certain permission (within his/her set of roles).
	 * 
	 * @param app : the application for which the permission is used.
	 * @param permission : the permission the member should have.
	 * 
	 * @return the member has the permission.
	 */
	default boolean hasPermission(IApplication app, IPermission permission)
	{
		for (IRole role : getRoles())
		{
			if (role.getPermissions(app) != null && role.getPermissions(app).contains(permission))
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
		return getRoles().contains(role);
	}

	/**
	 * returns the roles the member takes part in.
	 * 
	 * @return roles in a set (or empty set).
	 */
	Set<IRole> getRoles();
}
