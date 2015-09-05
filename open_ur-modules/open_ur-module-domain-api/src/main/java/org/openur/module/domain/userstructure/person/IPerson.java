package org.openur.module.domain.userstructure.person;

import java.util.Set;

import org.apache.commons.lang3.Validate;
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
		String personalNumber = getNumber();		
		Validate.notNull(personalNumber, "personal-number must not be null!");
		
		return personalNumber;
	}
	
	default boolean isInApplication(String applicationName)
	{
		Validate.notNull(applicationName, "application-name must not be null!");
		
		for (IApplication app : getApplications())
		{
			if (applicationName.equals(app.getApplicationName()))
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
