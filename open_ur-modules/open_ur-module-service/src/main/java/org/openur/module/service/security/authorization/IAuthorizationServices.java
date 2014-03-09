package org.openur.module.service.security.authorization;

import java.util.Set;

import org.openur.module.domain.IPrincipalUser;
import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.security.IRole;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;

public interface IAuthorizationServices
{
  /**
   * obtains all defined user-roles.
   * 
   * @return Set with user-roles (maybe empty).
   */
  Set<IRole> obtainAllRoles();
  
  /**
   * searches a role to the given id.
   * 
   * @param roleId : id of the role.
   * 
   * @return the role with the given id or null, if no role is found.
   */
  IRole findRolePerId(String roleId);
  
  /**
   * obtains the permissions assigned to a role.
   * 
   * @param role : the role.
   * 
   * @return Set with permissions assigned to the role (maybe empty).
   */
  Set<IPermission> obtainPermissionsPerRole(IRole role);
  
  /**
   * checks if 
   * 
   * @param app
   * @param permission
   * @param user
   * @param orgUnit
   * 
   * @return
   */
  Boolean hasPermission(IApplication app, IPermission permission, IPrincipalUser user, IOrganizationalUnit orgUnit);
}
