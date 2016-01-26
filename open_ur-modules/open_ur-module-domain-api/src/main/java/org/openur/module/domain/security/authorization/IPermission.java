package org.openur.module.domain.security.authorization;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.IIdentifiableEntity;
import org.openur.module.domain.application.IApplication;
import org.openur.module.util.data.PermissionScope;

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
	 * the permission as a string-literal (should be unique).
	 * 
	 * @return String.
	 */
	String getPermissionText();
	
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
												.append(this.getPermissionText(), o.getPermissionText())
												.append(this.getApplication(), o.getApplication())
												.toComparison();
		
		return comparison;
	}
}
