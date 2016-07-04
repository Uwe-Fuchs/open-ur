package org.openur.module.service.security;

import org.openur.module.util.exception.EntityNotFoundException;

public interface IAuthorizationServices
{
	/**
	 * checks if a person has a certain permission in an application and an
	 * organizational-unit. Hint: Use this if you work with org-unit-related
	 * authorization.
	 * 
	 * @param personId
	 * @param orgUnitId
	 * @param permissionText
	 * @param applicationName
	 * 
	 * @return the person has the permission within the given organizational-unit.
	 * 
	 * @throws EntityNotFoundException
	 *           if no person is found with given personId, orgUnit with given
	 *           orgUnitId ect.
	 */
	Boolean hasPermission(String personId, String orgUnitId, String permissionText, String applicationName)
		throws EntityNotFoundException;

	/**
	 * checks if a person has a certain permission in an application. Hint: use
	 * this if you work with system-wide authorization.
	 * 
	 * @param personId
	 * @param permissionText
	 * @param applicationName
	 * 
	 * @return the person has the permission.
	 * 
	 * @throws EntityNotFoundException
	 *           if no person is found with given personId, permission with given
	 *           permissionText ect.
	 */
	Boolean hasPermission(String personId, String permissionText, String applicationName)
		throws EntityNotFoundException;

	/**
	 * checks if a technical-user has a certain permission in an application.
	 * 
	 * @param techUserId
	 * @param permissionText
	 * @param applicationName
	 * 
	 * @return the technical-user has the permission.
	 * 
	 * @throws EntityNotFoundException
	 *           if no technical-user is found with given techUserId, permission with given
	 *           permissionText ect.
	 */
	Boolean hasPermissionTechUser(String techUserId, String permissionText, String applicationName)
		throws EntityNotFoundException;
}
