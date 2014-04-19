package org.openur.module.domain.userstructure.orgunit;

import java.util.Set;

import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.userstructure.IUserStructureBase;
import org.openur.module.domain.userstructure.user.person.IPerson;

public interface IOrganizationalUnit 
	extends IUserStructureBase, Comparable<IOrganizationalUnit>
{		
	/**
	 * returns the (domain-specific) number of this org-unit.
	 */
	String getOrgUnitNumber();
	
	/**
	 * returns the id of the organizational unit that this ou is subordinated to.
	 * 
	 * @return the id of the superior ou or null if this ou is the highest ou (thus the "root"
	 * of the hierarchy).
	 */
	String getSuperOuId();
	
	/**
	 * indicates wether this org-unit is the hierachical root of the organization,
	 * i.e.: no super-org-unit exists for this org-unit.
	 * 
	 * @return this org-unit is the hierachical root of the organization
	 */
	boolean isRootOrgUnit();
	
	/**
	 * returns the members of this orgaizational-unit in a set.
	 * 
	 * @return set of members (maybe empty if the org-unit has no members).
	 */
	Set<IOrgUnitMember> getMembers();
	
  /**
   * searches the member with the given userId in this org-unit.
   *
   * @param id : the id of the member that is searched.
   *
   * @return IOrgUnitMember if found in this org-unit, else null.
   */
	IOrgUnitMember findMember(String id);
	
  /**
   * indicates if a person is a member in this org-unit.
   * 
   * @param id : the id of the person.
   * 
   * @return the given person is a member in this org-unit.
   */
  boolean isMember(String id);
	
  /**
   * indicates if a person is a member in this org-unit.
   * 
   * @param id : the person.
   * 
   * @return the given person is a member in this org-unit.
   */
  boolean isMember(IPerson person);
	
  /**
   * searches the given person in this org-unit.
   *
   * @param id : the person that is searched.
   *
   * @return IOrgUnitMember if found in this org-unit, else null.
   */
	IOrgUnitMember findMember(IPerson person);
	
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
}
