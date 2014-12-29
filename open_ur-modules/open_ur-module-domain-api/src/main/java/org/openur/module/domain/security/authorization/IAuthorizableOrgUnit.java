package org.openur.module.domain.security.authorization;

import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.person.IPerson;

public interface IAuthorizableOrgUnit
	extends IOrganizationalUnit
{
	/**
	 * returns the (authorizable) ou this ou is subordinated to.
	 * 
	 * @return the superior (authorizable) ou or null if this ou is the highest ou (thus the "root"
	 * of the hierarchy).
	 */
	IAuthorizableOrgUnit getSuperOrgUnit();
	
	/**
	 * returns the (authorizable) root-ou of the hierarchy this ou is a part of. 
	 * 
	 * @return (authorizable) root-ou of this hierarchy.
	 */
	IAuthorizableOrgUnit getRootOrgUnit();
	
	/**
	 * indicates if a person has a certain permission in an app.
	 * 
	 * @param person : the person for whom the permission is used.
	 * @param app : the application for which the permission is used.
	 * @param permission : the permission the person should have.
	 * 
	 * @return the person has the permission.
	 */
	default boolean hasPermission(IPerson person, IApplication app, IPermission permission)
	{
		IAuthorizableMember member = findMember(person.getIdentifier());
		
		if (member == null)
		{
			return false;
		}
		
		return member.hasPermission(app, permission);
	}
}
