package org.openur.module.domain.security.authorization;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;

/**
 * 
 * @author uwe
 *
 */
public interface IAuthorizableTechUser
	extends ITechnicalUser
{
	// operations:	
	/**
	 * indicates if a technical-user has a certain permission.
	 * 
	 * @param permission : the permission the technical-user should have.
	 * 
	 * @return the technical-user has the permission.
	 */
	default boolean hasPermission(IPermission permission)
	{
		Validate.notNull(permission, "permission must not be null!");
		Validate.notNull(permission.getApplication(), "application must be set in permission!");
		
		return containsPermission(permission.getApplication(), permission);
	}

	/**
	 * has this technical-user a certain permission?
	 * 
	 * @param application : the application whose permissions are queried.
	 * @param permission : the permission in question.
	 * 
	 * @return this technical-user has the permission.
	 */
	boolean containsPermission(IApplication application, IPermission permission);
}
