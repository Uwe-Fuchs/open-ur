package org.openur.module.persistence.security;

import java.util.List;

import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.security.IRole;
import org.openur.module.domain.security.orgunit.IAuthorizableOrgUnit;

public interface ISecurityDao
{
	/**
	 * searches all defined user-roles in the underlying persistence-system and returns them
	 * in a list.
	 * 
	 * @return List with user-roles (empty if no roles are defined).
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
	
	/**
	 * searches a permission-object with the given id.
	 * 
	 * @param permissionId : id of the permission.
	 * 
	 * @return the permission with the given id or null, if no permission is found.
	 */
	IPermission findPermissionById(String permissionId);
	
	/**
	 * searches a permission-object with the given name and the given application.
	 * 
	 * @param permissionName : name of the permission.
	 * @param application : application
	 * 
	 * @return the permission with the given name or null, if no permission is found.
	 */
	IPermission findPermissionByName(String permissionName, IApplication application);

	/**
	 * obtains all defined user-permissions for a given application.
	 * 
	 * @param application : application
	 * 
	 * @return List with user-permissions (empty if no permissions are defined for the given app).
	 */
	List<IPermission> obtainPermissionsForApp(IApplication application);
	
	/**
	 * obtains all defined user-permissions.
	 * 
	 * @return List with user-permissions (empty if no permissions are defined).
	 */
	List<IPermission> obtainAllPermissions();
	
  /**
   * searches an authorizable org-unit via it's unique identifier.
   * 
   * @param orgUnitId : the unique identifier of the org-unit.
   * 
   * @return the authorizable org-unit or null, if no org-unit is found.
   */
	IAuthorizableOrgUnit findAuthOrgUnitById(String orgUnitId);
	// idea: search the org-unit via orgUnitDao, then enrich it's members with roles (???)
}
