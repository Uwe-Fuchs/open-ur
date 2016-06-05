package org.openur.module.domain.userstructure.technicaluser;

import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.userstructure.IUserStructureBase;

public interface ITechnicalUser
	extends IUserStructureBase, Comparable<ITechnicalUser>
{	
	/**
	 * the permissions assigned to this role for one special application.
	 * 
	 * @param application : the application whose permissions are queried.
	 * 
	 * @return Set<IPermission>
	 */
	Set<? extends IPermission> getPermissions(IApplication application);
	
	/**
	 * the applications of assigned permissions.
	 * 
	 * @return Set<IApplication>
	 */
	Set<? extends IApplication> getApplications();
	
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
	
	default int compareTo(ITechnicalUser other)
	{
		int comparison = new CompareToBuilder()
				.append(this.getTechUserNumber(), other.getTechUserNumber())
				.append(this.getStatus(), other.getStatus())
				.toComparison();

		return comparison;
	}
}