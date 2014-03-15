package org.openur.module.domain.userstructure.orgunit;

import java.util.Set;

import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.security.IRole;

public interface IOrgUnitMember
	extends Comparable<IOrgUnitMember>
{
	/**
	 * returns the personal identifier of the member.
	 */
	String getPersonId();

	/**
	 * returns the roles the member takes part in.
	 * 
	 * @return roles in a set (or empty set).
	 */
	Set<IRole> getRoles();

	/**
	 * indicates if a member has a certain role.
	 * 
	 * @param role : the role in which the member should take part in.
	 * 
	 * @return the member has the role.
	 */
	boolean hasRole(IRole role);
	
	/**
	 * indicates if a member has a certain permission (within his/her set of roles).
	 * 
	 * @param role : the permission the member should have.
	 * 
	 * @return the member has the permission.
	 */
	boolean hasPermission(IApplication app, IPermission permission);
}
