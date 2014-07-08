package org.openur.module.service.security;


import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IAuthenticationToken;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.security.IPrincipalUser;
import org.openur.module.domain.security.orgunit.IAuthorizableOrgUnit;
import org.openur.module.domain.userstructure.user.person.IPerson;

public interface ISecurityAuthServices
{
  /**
   * checks if a user has a certain (app-based) permission within an organizational-unit.
   * 
   * @param userId
   * @param orgUnitId
   * @param permission
   * @param app
   * 
   * @return the user has the permission.
   */
  Boolean hasPermission(String userId, String orgUnitId, String permission, IApplication app);
  
  /**
   * checks if a user has a certain (app-based) permission within an organizational-unit.
   * 
   * @param user
   * @param orgUnit
   * @param permission
   * @param app
   * 
   * @return the user has the permission.
   */
  Boolean hasPermission(IPerson user, IAuthorizableOrgUnit orgUnit,
		IPermission permission, IApplication app);

  /**
   * authenticates a user based on the given token.
   * 
   * @param authenticationToken
   * 
   * @return the user-object if authentication was successful, null otherwise.
   */ 
	IPrincipalUser authenticate(IAuthenticationToken authenticationToken);
}
