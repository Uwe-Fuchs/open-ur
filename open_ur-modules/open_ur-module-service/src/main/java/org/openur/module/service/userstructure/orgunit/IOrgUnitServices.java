package org.openur.module.service.userstructure.orgunit;

import java.util.Set;

import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;

public interface IOrgUnitServices
{
  /**
   * searches an orgainizational-unit via it's unique identifier.
   * 
   * @param orgUnitId : the unique identifier of the orgainizational-unit.
   * 
   * @return the orgainizational-unit or null, if no orgainizational-unit is found.
   */
  IOrganizationalUnit findOrgUnitById(String orgUnitId);
  
  /**
   * searches a orgainizational-unit via it's (domain specific) number.
   * 
   * @param orgUnitNumber : the number of the orgainizational-unit.
   * 
   * @return the orgainizational-unit or null, if no orgainizational-unit is found.
   */
  IOrganizationalUnit findOrgUnitByNumber(String orgUnitNumber);
	
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
   * @param inclMembers : including all members of the org-units?
   * 
   * @return Set with all subordinated org-units of an org-unit (maybe empty).
   */
  Set<IOrganizationalUnit> obtainSubOrgUnitsForOrgUnit(String orgUnitId, boolean inclMembers);
}
