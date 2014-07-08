package org.openur.module.domain.security.authorization;

import org.openur.module.domain.IIdentifiableEntity;
import org.openur.module.domain.application.IApplication;

public interface IPermission
	extends IIdentifiableEntity, Comparable<IPermission>
{
	/**
	 * the application this permission is assigned to.
	 * 
	 * @return IApplication
	 */
	IApplication getApp();
	
	/**
	 * the permission-literal.
	 * 
	 * @return String.
	 */
	String getPermissionName();
	
	/**
	 * the scope (i.e. SELECTED, SUB or SELECTED_SUB)
	 * 
	 * @return PermissionScope
	 */
	PermissionScope getPermissionScope();
}
