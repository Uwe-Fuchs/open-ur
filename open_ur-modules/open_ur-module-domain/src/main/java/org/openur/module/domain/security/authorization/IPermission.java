package org.openur.module.domain.security.authorization;

import org.apache.commons.lang3.builder.CompareToBuilder;
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
	IApplication getApplication();
	
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
	
	// operations:
	default int compareTo(IPermission o)
	{
		int comparison = new CompareToBuilder()
												.append(this.getPermissionName(), o.getPermissionName())
												.append(this.getApplication(), o.getApplication())
												.toComparison();
		
		return comparison;
	}
}
