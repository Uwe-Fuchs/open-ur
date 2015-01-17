package org.openur.module.persistence.dao;

import java.util.List;

import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;

public interface IUserStructureDao
{
  /**
   * searches a person via it's unique identifier.
   * 
   * @param personId : the unique identifier of the person.
   * 
   * @return the person or null, if no person is found.
   */
  IPerson findPersonById(String personId);
  
  /**
   * searches a person via it's (domain specific) user-number.
   * 
   * @param personalNumber : the user-number of the person.
   * 
   * @return the person or null, if no person is found.
   */
  IPerson findPersonByNumber(String personalNumber);
	
  /**
   * returns all stored persons in a list.
   * If no persons are found, the result-list will be empty (not null).
   * 
   * @return List with all persons.
   */
  List<IPerson> obtainAllPersons();
  
  /**
   * searches an organizational-unit via it's unique identifier.
   * 
   * @param orgUnitId : the unique identifier of the organizational-unit.
   * 
   * @return the (authorizable) organizational-unit or null, if no organizational-unit is found.
   */
  IAuthorizableOrgUnit findOrgUnitById(String orgUnitId);
  
  /**
   * searches a organizational-unit via it's (domain specific) number.
   * 
   * @param orgUnitNumber : the number of the organizational-unit.
   * 
   * @return the (authorizable) organizational-unit or null, if no organizational-unit is found.
   */
  IAuthorizableOrgUnit findOrgUnitByNumber(String orgUnitNumber);
	
  /**
   * returns all stored organizational-units in a list.
   * If no organizational-units are found, the result-list will be empty (not null).
   * 
   * @return List with all (authorizable) organizational-units (maybe empty).
   */
  List<IAuthorizableOrgUnit> obtainAllOrgUnits();
  
  /**
   * returns all subordinated org-units of an org-unit in a list.
   * If no org-units are found, the result-list will be empty (not null).
   * 
   * @param orgUnitId : the unique identifier of the super-org-unit.
   * @param inclMembers : including all members of the org-units?
   * 
   * @return List with all subordinated (authorizable) org-units of an org-unit (maybe empty).
   */
  List<IAuthorizableOrgUnit> obtainSubOrgUnitsForOrgUnit(String orgUnitId, boolean inclMembers);
	
  /**
   * returns all org-units, which are roots (i.e. the highest in
   * an organization) in a list. If no org-units are found, 
   * the result-list will be empty (not null).
   * 
   * @return List with all (authorizable) root-org-units.
   */
  List<IAuthorizableOrgUnit> obtainRootOrgUnits();
  
  /**
   * searches a technical user via it's unique identifier.
   * 
   * @param techUserId : the unique identifier of the technical user.
   * 
   * @return the technical user or null, if no user is found.
   */
	ITechnicalUser findTechnicalUserById(String techUserId);
	
  /**
   * searches a technical user via it's (domain specific) user-number.
   * 
   * @param techUserNumber : the user-number of the technical user.
   * 
   * @return the technical user or null, if no user is found.
   */
	ITechnicalUser findTechnicalUserByNumber(String techUserNumber);
	
  /**
   * returns all stored technical users in a list.
   * If no users are found, the result-list will be empty (not null).
   * 
   * @return List with all technical users.
   */
  List<ITechnicalUser> obtainAllTechnicalUsers();
}
