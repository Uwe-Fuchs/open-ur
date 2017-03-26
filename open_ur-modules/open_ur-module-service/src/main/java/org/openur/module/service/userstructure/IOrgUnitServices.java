package org.openur.module.service.userstructure;

import java.util.Set;

import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;

public interface IOrgUnitServices
{
  /**
   * searches an organizational-unit via it's unique identifier.
   * 
   * @param orgUnitId : the unique identifier of the organizational-unit.
   * @param inclMembersRoles : including all members (plus their roles)?
   * 
   * @return the organizational-unit or null, if no organizational-unit is found.
   */
  IOrganizationalUnit findOrgUnitById(String orgUnitId, Boolean inclMembersRoles);
  
  /**
   * searches a organizational-unit via it's (domain specific) number.
   * 
   * @param orgUnitNumber : the number of the organizational-unit.
   * @param inclMembersRoles  : including all members (plus their roles)?
   * 
   * @return the organizational-unit or null, if no organizational-unit is found.
   */
  IOrganizationalUnit findOrgUnitByNumber(String orgUnitNumber, Boolean inclMembersRoles);
	
  /**
   * returns all stored organizational-units in a set.
   * If no organizational-units are found, the result-set will be empty (not null).
   * 
   * @return Set with all organizational-units.
   */
  Set<IOrganizationalUnit> obtainAllOrgUnits();
  
  /**
   * returns all subordinated org-units of an org-unit in a set.
   * If no org-units are found, the result-set will be empty (not null).
   * 
   * @param orgUnitId : the unique identifier of the super-org-unit.
   * @param inclMembersRoles : including all members (plus their roles) of the org-units?
   * 
   * @return Set with all subordinated org-units of an org-unit (maybe empty).
   */
  Set<IOrganizationalUnit> obtainSubOrgUnitsForOrgUnit(String orgUnitId, Boolean inclMembersRoles);
	
  /**
   * returns all organizational-units (without their members!!!), which are roots (i.e. the highest in
   * an organization) in a set. If no org-units can be found, the result-set will be empty (not null).
   * 
   * @return Set with all root-org-units.
   */
  Set<IOrganizationalUnit> obtainRootOrgUnits();
}
