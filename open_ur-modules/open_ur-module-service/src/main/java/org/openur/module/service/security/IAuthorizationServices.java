package org.openur.module.service.security;



public interface IAuthorizationServices
{
  /**
   * checks if a user has a certain (app-based) permission within an organizational-unit.
   * 
   * @param userId
   * @param orgUnitId
   * @param permissionText
   * @param applicationName
   * 
   * @return the user has the permission.
   */
  Boolean hasPermission(String userId, String orgUnitId, String permissionText, String applicationName);
}
