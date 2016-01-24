package org.openur.module.persistence.dao;

import java.util.List;

import org.openur.module.domain.application.IApplication;

/**
 * 
 * @author info@uwefuchs.com
 */
public interface IApplicationDao
{
	/**
	 * searches an application user via it's unique identifier.
	 * 
	 * @param applicationId
	 *          : the unique identifier of the application.
	 * 
	 * @return the application or null, if no application is found.
	 */
	IApplication findApplicationById(String applicationId);
	
	/**
	 * searches an application user via it's (unique) name.
	 * 
	 * @param applicationName
	 *          : the name of the application.
	 * 
	 * @return the application or null, if no application is found.
	 */
	IApplication findApplicationByName(String applicationName);
	
	/**
	 * returns all stored applications in a list. If no applications are found, the
	 * result-list will be empty (not null).
	 * 
	 * @return List with all applications.
	 */
	List<IApplication> obtainAllApplications();
}
