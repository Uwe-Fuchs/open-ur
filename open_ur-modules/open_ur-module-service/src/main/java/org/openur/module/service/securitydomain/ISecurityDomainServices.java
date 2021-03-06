package org.openur.module.service.securitydomain;

import java.util.Set;

import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;

public interface ISecurityDomainServices
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
	
	/**
	 * searches a permission-object with the given id.
	 * 
	 * @param permissionId : id of the permission.
	 * 
	 * @return the permission with the given id or null, if no permission is found.
	 */
	IPermission findPermissionById(String permissionId);
	
	/**
	 * searches a permission-object with the given text within the application with the given name.
	 * 
	 * @param permissionText : text of the permission.
	 * @param applicationName : name of the application.
	 * 
	 * @return the permission with the given text or null, if no permission is found.
	 */
	IPermission findPermission(String permissionText, String applicationName);

	/**
	 * obtains all defined user-permissions for a given application.
	 * 
	 * @param applicationName : name of the given application.
	 * 
	 * @return Set with user-permissions (empty if no permissions are defined for the given app).
	 */
	Set<IPermission> obtainPermissionsForApp(String applicationName);
	
	/**
	 * obtains all defined user-permissions.
	 * 
	 * @return Set with user-permissions (empty if no permissions are defined).
	 */
	Set<IPermission> obtainAllPermissions();
}
