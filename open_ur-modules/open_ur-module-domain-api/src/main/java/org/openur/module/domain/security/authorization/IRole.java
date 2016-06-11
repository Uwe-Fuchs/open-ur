package org.openur.module.domain.security.authorization;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.IIdentifiableEntity;
import org.openur.module.domain.application.IApplication;

public interface IRole
	extends IIdentifiableEntity, Comparable<IRole>
{
	/**
	 * the role as a string-literal.
	 * 
	 * @return String.
	 */
	String getRoleName();
	
	// operations:
	/**
	 * has this role a certain permission?
	 * 
	 * @param application : the application whose permissions are queried.
	 * @param permission : the permission in question.
	 * 
	 * @return this role has the permission.
	 */
	boolean containsPermission(IApplication application, IPermission permission);
	
	@Override
	default int compareTo(IRole other)
	{
		int comparison = new CompareToBuilder()
												.append(this.getRoleName(), other.getRoleName())
												.toComparison();
		
		if (comparison != 0)
		{
			return comparison;
		} else
		{
			// this should never happen because two roles shouldn't have the same name
			return this.getIdentifier().compareTo(other.getIdentifier());
		}
	}
}
