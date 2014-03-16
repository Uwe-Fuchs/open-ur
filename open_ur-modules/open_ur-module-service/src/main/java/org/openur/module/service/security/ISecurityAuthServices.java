package org.openur.module.service.security;


import org.openur.module.domain.IPrincipalUser;
import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IAuthenticationToken;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;

public interface ISecurityAuthServices
{
  /**
   * checks if a user has a certain (app-based) permission within an organizational-unit.
   * 
   * @param user
   * @param app
   * @param orgUnit
   * @param permission
   * 
   * @return the user has the permission.
   */
  Boolean hasPermission(IPrincipalUser user, IApplication app, IOrganizationalUnit orgUnit, IPermission permission);
  
  /**
   * checks if a user has a certain (app-based) permission in general.
   * 
   * @param user
   * @param app
   * @param permission
   * 
   * @return the user has the permission.
   */
  Boolean hasPermission(IPrincipalUser user, IApplication app, IPermission permission);

  /**
   * authenticates a user based on the given token.
   * 
   * @param authenticationToken
   * 
   * @return the user-object if authentication was successful, null otherwise.
   */ 
	IPrincipalUser authenticate(IAuthenticationToken authenticationToken);
}
