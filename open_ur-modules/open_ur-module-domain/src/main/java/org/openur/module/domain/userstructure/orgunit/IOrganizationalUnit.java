package org.openur.module.domain.userstructure.orgunit;

import java.util.Set;

import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;
import org.openur.module.domain.userstructure.IUserStructureBase;

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
}
