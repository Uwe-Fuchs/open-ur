package org.openur.module.domain.security.authorization;

import java.util.Set;

import org.apache.commons.lang3.Validate;
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
	 * returns the members of this organizational-unit in a set.
	 * 
	 * @return set of members (maybe empty if the org-unit has no members).
	 */
	Set<? extends IAuthorizableMember> getMembers();
	
	/**
   * searches the (authorizable) member with the given userId in this org-unit.
   *
   * @param personId : the person-id of the searched member.
   *
   * @return IAuthorizableMember if found in this org-unit, else null.
   */
	default IAuthorizableMember findMemberByPersonId(String personId)
	{
		Validate.notNull(personId, "person-id must not be null!");
		
    for (IAuthorizableMember m : this.getMembers())
    {
      if (m.getPerson().getIdentifier().equals(personId))
      {
        return m;
      }
    }

    return null;
	}
	
  /**
   * searches the given person as a (authorizable) member in this org-unit.
   *
   * @param person : the person that is searched.
   *
   * @return IAuthorizableMember if found in this org-unit, else null.
   */
	default IAuthorizableMember findMemberByPerson(IPerson person)
	{
		Validate.notNull(person, "person must not be null!");
		
		return findMemberByPersonId(person.getIdentifier());
	}
	
	/**
	 * indicates if a person has a certain permission in an app.
	 * 
	 * @param person : the person for whom the permission is used.
	 * @param permission : the permission (incl. the app) the person should have.
	 * 
	 * @return the person has the permission.
	 */
	default boolean hasPermission(IPerson person, IPermission permission)
	{
		IAuthorizableMember member = findMemberByPerson(person);
		
		if (member == null)
		{
			return false;
		}
		
		return member.hasPermission(permission);
	}
}
