package org.openur.module.service.security;

import java.util.Set;

import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.security.IRole;

public interface ISecurityRelatedUserServices
{
	/**
	 * searches a role to the given id.
	 * 
	 * @param roleId : id of the role.
	 * 
	 * @return the role with the given id or null, if no role is found.
	 */
	IRole findRolePerId(String roleId);

	/**
	 * obtains all defined user-roles.
	 * 
	 * @return Set with user-roles (maybe empty).
	 */
	Set<IRole> obtainAllRoles();

	/**
	 * obtains the permissions assigned to a role.
	 * 
	 * @param role : the role.
	 * 
	 * @return Set with permissions assigned to the role (maybe empty).
	 */
	Set<IPermission> obtainPermissionsPerRole(IRole role);
}
