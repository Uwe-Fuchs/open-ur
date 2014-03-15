package org.openur.module.service.security;

import java.util.Set;

import org.openur.module.domain.security.IRole;

public interface ISecurityRelatedUserServices
{
	/**
	 * searches a role-object with the given id.
	 * 
	 * @param roleId : id of the role.
	 * 
	 * @return the role with the given id or null, if no role is found.
	 */
	IRole findRoleById(String roleId);
	
	/**
	 * searches a role-object with the given name.
	 * 
	 * @param roleName : name of the role.
	 * 
	 * @return the role with the given name or null, if no role is found.
	 */
	IRole findRoleByName(String roleName);

	/**
	 * obtains all defined user-roles.
	 * 
	 * @return Set with user-roles (empty if no roles are defined).
	 */
	Set<IRole> obtainAllRoles();
}
