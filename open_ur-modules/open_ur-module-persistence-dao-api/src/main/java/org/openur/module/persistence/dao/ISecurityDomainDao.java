package org.openur.module.persistence.dao;

import java.util.List;

import org.openur.module.domain.security.authentication.IUserAccount;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;

/**
 * all data-access-operations concerning open-ur security-domain.
 * 
 * @author info@uwefuchs.com
 */
public interface ISecurityDomainDao
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
	 * searches a permission-object with the given text within a given application (text should be unique within an application).
	 * 
	 * @param permissionText : text of the permission.
	 * @param applicationName : name of the application where the permssion is defined in.
	 * 
	 * @return the permission with the given text or null, if no permission is found.
	 */
	IPermission findPermission(String permissionText, String applicationName);

	/**
	 * obtains all defined user-permissions for a given application.
	 * 
	 * @param applicationName : application-name (should be unique).
	 * 
	 * @return List with user-permissions (empty if no permissions are defined for the given app).
	 */
	List<IPermission> obtainPermissionsForApp(String applicationName);
	
	/**
	 * obtains all defined user-permissions.
	 * 
	 * @return List with user-permissions (empty if no permissions are defined).
	 */
	List<IPermission> obtainAllPermissions();
	
	/**
	 * searches a userAccount-object with given userName.
	 * 
	 * @param userName
	 * 
	 * @return the userAccount with given userName or null if no userAccount is found.
	 */
	IUserAccount findUserAccountByUserName(String userName);
}
