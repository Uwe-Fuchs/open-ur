package org.openur.module.service.security;


import org.openur.module.domain.IPrincipalUser;
import org.openur.module.domain.security.IApplication;
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

	IPrincipalUser authenticate(String userName);
}
