package org.openur.module.service.security;



public interface IAuthorizationServices
{
  /**
   * checks if a person has a certain (app-based) permission within an organizational-unit.
   * Hint: Use this if work with permission-based, org-unit-related authorization.
   * 
   * @param personId
   * @param orgUnitId
   * @param permissionText
   * @param applicationName
   * 
   * @return the person has the permission within the given organizational-unit.
   */
  Boolean hasPermission(String personId, String orgUnitId, String permissionText, String applicationName);
  
  /**
   * checks if a person has a certain (app-based) permission.
   * Hint: use this if you work with permission-based, but NOT org-unit-related authorization.
   * 
   * @param personId
   * @param permissionText
   * @param applicationName
   * 
   * @return the person has the permission.
   */
  Boolean hasPermission(String personId, String permissionText, String applicationName);}
