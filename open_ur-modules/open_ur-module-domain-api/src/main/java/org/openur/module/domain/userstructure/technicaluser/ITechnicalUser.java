package org.openur.module.domain.userstructure.technicaluser;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.userstructure.IUserStructureBase;

public interface ITechnicalUser
	extends IUserStructureBase, Comparable<ITechnicalUser>
{	
	// operations:
	/**
	 * returns the (domain-specific) number of this tech-user.
	 */
	default String getTechUserNumber()
	{
		String number = getNumber();
		Validate.notNull(number, "technical-user-number must not be null!");		
		
		return number;
	}
	
	// operations:
	/**
	 * has this technical-user a certain permission?
	 * 
	 * @param application : the application whose permissions are queried.
	 * @param permission : the permission in question.
	 * 
	 * @return this technical-user has the permission.
	 */
	boolean containsPermission(IApplication application, IPermission permission);
	
	default int compareTo(ITechnicalUser other)
	{
		int comparison = new CompareToBuilder()
				.append(this.getTechUserNumber(), other.getTechUserNumber())
				.append(this.getStatus(), other.getStatus())
				.toComparison();

		return comparison;
	}
}