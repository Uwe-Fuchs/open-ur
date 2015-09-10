package org.openur.module.domain.application;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.IIdentifiableEntity;


public interface IApplication
	extends IIdentifiableEntity, Comparable<IApplication>
{
	/**
	 * the application-literal (should be unique).
	 * 
	 * @return String
	 */
	String getApplicationName();
	
	// operations:
	@Override
	default int compareTo(IApplication o)
	{
		String appName = this.getApplicationName();
		Validate.notNull(appName, "application-name must not be null!");		
		
		return appName.compareToIgnoreCase(o.getApplicationName());
	}
}
