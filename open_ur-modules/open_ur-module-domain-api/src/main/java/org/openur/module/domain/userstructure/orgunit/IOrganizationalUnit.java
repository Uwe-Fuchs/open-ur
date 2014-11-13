package org.openur.module.domain.userstructure.orgunit;

import java.util.Set;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.userstructure.IUserStructureBase;
import org.openur.module.domain.userstructure.person.IPerson;

public interface IOrganizationalUnit 
	extends IUserStructureBase, Comparable<IOrganizationalUnit>
{		
	/**
	 * returns the (domain-specific) number of this org-unit.
	 */
	String getOrgUnitNumber();
	
	/**
	 * returns the organizational unit this ou is subordinated to.
	 * 
	 * @return the superior ou or null if this ou is the highest ou (thus the "root"
	 * of the hierarchy).
	 */
	IOrganizationalUnit getSuperOrgUnit();
	
	/**
	 * returns the root-org-unit of the hierarchy this org-unit is a part of. 
	 * 
	 * @return root-org-unit of this hierarchy.
	 */
	IOrganizationalUnit getRootOrgUnit();
	
	/**
	 * returns the members of this organizational-unit in a set.
	 * 
	 * @return set of members (maybe empty if the org-unit has no members).
	 */
	Set<? extends IOrgUnitMember> getMembers();
	
  /**
   * searches the member with the given userId in this org-unit.
   *
   * @param id : the id of the member that is searched.
   *
   * @return IOrgUnitMember if found in this org-unit, else null.
   */
	IOrgUnitMember findMember(String id);
	
  /**
   * searches the given person in this org-unit.
   *
   * @param id : the person that is searched.
   *
   * @return IOrgUnitMember if found in this org-unit, else null.
   */
	IOrgUnitMember findMember(IPerson person);

	// operations:	
	/**
	 * indicates wether this org-unit is the hierachical root of the organization,
	 * i.e.: no super-org-unit exists for this org-unit.
	 * 
	 * @return this org-unit is the hierachical root of the organization
	 */
	default boolean isRootOrgUnit()
	{
		return (getRootOrgUnit() == null);
	}
	
  /**
   * indicates if a person is a member in this org-unit.
   * 
   * @param id : the id of the person.
   * 
   * @return the given person is a member in this org-unit.
   */
	default boolean isMember(String id)
	{
    if (id == null)
    {
      return false;
    }

    return (this.findMember(id) != null);
	}
	
  /**
   * indicates if a person is a member in this org-unit.
   * 
   * @param id : the person.
   * 
   * @return the given person is a member in this org-unit.
   */
	default boolean isMember(IPerson person)
	{
		if (person == null)
    {
      return false;
    }

    return (this.findMember(person) != null);
	}
	
	default int compareTo(IOrganizationalUnit ou)
	{    
    int comparison = new CompareToBuilder()
														.append(this.getOrgUnitNumber(), ou.getOrgUnitNumber())
														.append(this.getStatus(), ou.getStatus())
														.toComparison();

    return comparison;
	}
}
