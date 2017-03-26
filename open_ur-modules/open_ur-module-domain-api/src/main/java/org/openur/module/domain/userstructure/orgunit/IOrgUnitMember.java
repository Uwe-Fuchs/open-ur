package org.openur.module.domain.userstructure.orgunit;


import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.IIdentifiableEntity;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.domain.userstructure.person.IPerson;

public interface IOrgUnitMember
	extends IIdentifiableEntity, Comparable<IOrgUnitMember>
{
	/**
	 * returns the person-object of the member.
	 */
	IPerson getPerson();
	
	/**
	 * returns the org-unit of the member.
	 */
	String getOrgUnitId();

	// operations:
	default int compareTo(IOrgUnitMember other)
	{
		if (this.equals(other))
		{
			return 0;
		}
		
		return new CompareToBuilder()
										.append(this.getOrgUnitId(), other.getOrgUnitId())
										.append(this.getPerson(), other.getPerson())
										.toComparison();
	}

	/**
	 * returns the roles the member takes part in.
	 * 
	 * @return roles in a set (or empty set).
	 */
	Set<? extends IRole> getRoles();

	/**
	 * indicates if a member has a certain permission (within his/her set of roles).
	 * 
	 * @param permission : the permission the member should have.
	 * 
	 * @return the member has the permission.
	 */
	default boolean hasPermission(IPermission permission)
	{
		Validate.notNull(permission, "permission must not be null!");
		Validate.notNull(permission.getApplication(), "application must be set in permission!");
		
		if (getPerson() == null || !getPerson().getApplications().contains(permission.getApplication()))
		{
			return false;
		}
		
		for (IRole role : getRoles())
		{
			if (role.containsPermission(permission.getApplication(), permission))
			{
				return true;
			}
		}
	
		return false;
	}

	/**
	 * indicates if a member has a certain role.
	 * 
	 * @param role : the role in which the member should take part in.
	 * 
	 * @return the member has the role.
	 */
	default boolean hasRole(IRole role)
	{
		Validate.notNull(getRoles(), "roles-list must be set!");
		
		return getRoles().contains(role);
	}
}
