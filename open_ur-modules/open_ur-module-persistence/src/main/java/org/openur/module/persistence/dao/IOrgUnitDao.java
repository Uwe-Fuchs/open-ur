package org.openur.module.persistence.dao;

import java.util.List;

import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;

public interface IOrgUnitDao
{
	/**
	 * searches an organizational-unit via the unique identifier.
	 * 
	 * @param orgUnitId
	 *          : the unique identifier of the organizational-unit.
	 * 
	 * @return the (authorizable) organizational-unit or null, if no
	 *         organizational-unit is found.
	 */
	IAuthorizableOrgUnit findOrgUnitById(String orgUnitId);

	/**
	 * searches an organizational-unit including its members via the unique
	 * identifier.
	 * 
	 * @param orgUnitId
	 *          : the unique identifier of the organizational-unit.
	 * 
	 * @return the (authorizable) organizational-unit including its members or
	 *         null, if no organizational-unit is found.
	 */
	IAuthorizableOrgUnit findOrgUnitAndMembersById(String orgUnitId);

	/**
	 * searches an organizational-unit including its members and roles via the
	 * unique identifier.
	 * 
	 * @param orgUnitId
	 *          : the unique identifier of the organizational-unit.
	 * 
	 * @return the (authorizable) organizational-unit including its members and
	 *         roles or null, if no organizational-unit is found.
	 */
	IAuthorizableOrgUnit findOrgUnitAndMembersRolesById(String orgUnitId);

	/**
	 * searches a organizational-unit via the (domain specific) number.
	 * 
	 * @param orgUnitNumber
	 *          : the number of the organizational-unit.
	 * 
	 * @return the (authorizable) organizational-unit or null, if no
	 *         organizational-unit is found.
	 */
	IAuthorizableOrgUnit findOrgUnitByNumber(String orgUnitNumber);

	/**
	 * searches a organizational-unit including its members via the (domain
	 * specific) number.
	 * 
	 * @param orgUnitNumber
	 *          : the number of the organizational-unit.
	 * 
	 * @return the (authorizable) organizational-unit including its members or
	 *         null, if no organizational-unit is found.
	 */
	IAuthorizableOrgUnit findOrgUnitAndMembersByNumber(String orgUnitNumber);

	/**
	 * searches a organizational-unit including its members and roles via the
	 * (domain specific) number.
	 * 
	 * @param orgUnitNumber
	 *          : the number of the organizational-unit.
	 * 
	 * @return the (authorizable) organizational-unit including its members and
	 *         roles or null, if no organizational-unit is found.
	 */
	IAuthorizableOrgUnit findOrgUnitAndMembersRolesByNumber(String orgUnitNumber);

	/**
	 * returns all stored organizational-units in a list. If no
	 * organizational-units are found, the result-list will be empty (not null).
	 * 
	 * @return List with all (authorizable) organizational-units (maybe empty).
	 */
	List<IAuthorizableOrgUnit> obtainAllOrgUnits();

	/**
	 * returns all subordinated org-units of an org-unit in a list. If no
	 * org-units are found, the result-list will be empty (not null).
	 * 
	 * @param orgUnitId
	 *          : the unique identifier of the super-org-unit.
	 * @param inclMembers
	 *          : including all members of the org-units?
	 * 
	 * @return List with all subordinated (authorizable) org-units of an org-unit
	 *         (maybe empty).
	 */
	List<IAuthorizableOrgUnit> obtainSubOrgUnitsForOrgUnit(String orgUnitId, boolean inclMembers);

	/**
	 * returns all org-units, which are roots (i.e. the highest in an
	 * organization) in a list. If no org-units are found, the result-list will be
	 * empty (not null).
	 * 
	 * @return List with all (authorizable) root-org-units.
	 */
	List<IAuthorizableOrgUnit> obtainRootOrgUnits();
}
