package org.openur.module.domain.userstructure.person;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.userstructure.IUserStructureBase;

public interface IPerson
	extends IUserStructureBase, Comparable<IPerson>
{	
	/**
	 * get the applications the person is taking part in.
	 * 
	 * @return Set of applications (may be empty).
	 */
	Set<? extends IApplication> getApplications();

	// operations:
	/**
	 * returns the (domain-specific) number of the person.
	 */
	default String getPersonalNumber()
	{
		return getNumber();
	}
	
	default boolean isInApplication(String applicationName)
	{
		if (StringUtils.isEmpty(applicationName))
		{
			return false;
		}
		
		for (IApplication app : getApplications())
		{
			if (app.getApplicationName().equals(applicationName))
			{
				return true;
			}
		}
		
		return false;
	}
	
	default int compareTo(IPerson other)
	{
		int comparison = new CompareToBuilder()
				.append(this.getPersonalNumber(), other.getPersonalNumber())
				.append(this.getStatus(), other.getStatus())
				.toComparison();

		return comparison;
	}
}
