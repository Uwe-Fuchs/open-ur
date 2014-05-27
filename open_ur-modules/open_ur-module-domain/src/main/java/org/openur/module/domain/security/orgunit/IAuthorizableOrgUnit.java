package org.openur.module.domain.security.orgunit;

import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.user.person.IPerson;

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
	boolean hasPermission(IPerson person, IApplication app, IPermission permission);
	/* TODO: use this in Java8:
	 {
		IAuthorizableMember member = findAuthorizableMember(person.getIdentifier());
		
		if (member == null)
		{
			return false;
		}
		
		return member.hasPermission(app, permission);
	}*/
	
  /**
   * searches the member with the given userId in this org-unit.
   *
   * @param id : the id of the member that is searched.
   *
   * @return IAuthorizableMember if found in this org-unit, else null.
   */
	IAuthorizableMember findAuthorizableMember(String id);
	/* TODO: use this in Java8:
	 {
		return (IAuthorizableMember) super.findMember(id);
	}*/
}
