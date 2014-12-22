package org.openur.module.domain.security.authorization;

import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.person.IPerson;

public interface IAuthorizableOrgUnit
	extends IOrganizationalUnit
{
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
