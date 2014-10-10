package org.openur.module.domain.userstructure.person;

import java.util.Set;

import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.userstructure.IUserStructureBase;

public interface IPerson
	extends IUserStructureBase, Comparable<IPerson>
{
	/**
	 * returns the (domain-specific) number of the person.
	 */
	String getPersonNumber();
	
	/**
	 * get the applications the person is taking part in.
	 * 
	 * @return Set of applications (may be empty).
	 */
	Set<IApplication> getApplications();
}
