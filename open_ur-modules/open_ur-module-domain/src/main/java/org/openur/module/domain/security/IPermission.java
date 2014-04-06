package org.openur.module.domain.security;

import org.openur.module.domain.IIdentifiableEntity;

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
