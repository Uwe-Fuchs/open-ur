package org.openur.module.domain.userstructure.orgunit;

import java.util.Set;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.userstructure.IUserStructureBase;
import org.openur.module.domain.userstructure.person.IPerson;

public interface IOrganizationalUnit 
	extends IUserStructureBase, Comparable<IOrganizationalUnit>
{			
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

	// operations:
	/**
	 * returns the (domain-specific) number of this org-unit.
	 */
	default String getOrgUnitNumber()
	{
		return getNumber();
	}
	
  /**
   * searches the member with the given personId in this org-unit.
   *
   * @param personId : the person-id of the searched member.
   *
   * @return IOrgUnitMember if found in this org-unit, else null.
   */
	default IOrgUnitMember findMemberByPersonId(String personId)
	{
		for (IOrgUnitMember m : this.getMembers())
		{
      if (m.getPerson().getIdentifier().equals(personId))
      {
        return m;
      }
		}

		return null;
	}
	
  /**
   * searches the given person as a member in this org-unit.
   *
   * @param person : the person that is searched.
   *
   * @return IOrgUnitMember if found in this org-unit, else null.
   */
	default IOrgUnitMember findMemberByPerson(IPerson person)
	{
		if (person == null)
    {
      return null;
    }
		
		return findMemberByPersonId(person.getIdentifier());
	}
	
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

    return (this.findMemberByPersonId(id) != null);
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

    return (this.findMemberByPerson(person) != null);
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
