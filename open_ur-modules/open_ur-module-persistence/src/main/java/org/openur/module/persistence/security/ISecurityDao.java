package org.openur.module.persistence.security;

import java.util.List;

import org.openur.module.domain.security.IRole;

public interface ISecurityDao
{
	/**
	 * searches all defined user-roles in the underlying persistence-system and returns them
	 * in a list.
	 * 
	 * @return Set with user-roles (empty if no roles are defined).
	 */
	List<IRole> obtainAllRoles();
	
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
}
