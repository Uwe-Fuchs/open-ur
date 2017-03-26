package org.openur.module.persistence.dao;

import java.util.List;

import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;

public interface IOrgUnitDao
{
	/**
	 * searches an organizational-unit via the unique identifier.
	 * 
	 * @param orgUnitId
	 *          : the unique identifier of the organizational-unit.
	 * 
	 * @return the organizational-unit or null, if no
	 *         organizational-unit is found.
	 */
	IOrganizationalUnit findOrgUnitById(String orgUnitId);

	/**
	 * searches an organizational-unit including its members and roles via the
	 * unique identifier.
	 * 
	 * @param orgUnitId
	 *          : the unique identifier of the organizational-unit.
	 * 
	 * @return the organizational-unit including its members and
	 *         roles or null, if no organizational-unit is found.
	 */
	IOrganizationalUnit findOrgUnitAndMembersById(String orgUnitId);

	/**
	 * searches a organizational-unit via the (domain specific) number.
	 * 
	 * @param orgUnitNumber
	 *          : the number of the organizational-unit.
	 * 
	 * @return the organizational-unit or null, if no
	 *         organizational-unit is found.
	 */
	IOrganizationalUnit findOrgUnitByNumber(String orgUnitNumber);

	/**
	 * searches a organizational-unit including its members and roles via the
	 * (domain specific) number.
	 * 
	 * @param orgUnitNumber
	 *          : the number of the organizational-unit.
	 * 
	 * @return the organizational-unit including its members and
	 *         roles or null, if no organizational-unit is found.
	 */
	IOrganizationalUnit findOrgUnitAndMembersByNumber(String orgUnitNumber);

	/**
	 * returns all stored organizational-units in a list. If no
	 * organizational-units are found, the result-list will be empty (not null).
	 * 
	 * @return List with all organizational-units (maybe empty).
	 */
	List<IOrganizationalUnit> obtainAllOrgUnits();

	/**
	 * returns all stored organizational-units including its members and roles in a list. If no
	 * organizational-units are found, the result-list will be empty (not null).
	 * 
	 * @return List with all organizational-units (maybe empty).
	 */
	List<IOrganizationalUnit> obtainAllOrgUnitsInclMembers();

	/**
	 * returns all subordinated org-units of an org-unit in a list. If no
	 * org-units are found, the result-list will be empty (not null).
	 * 
	 * @param orgUnitId
	 *          : the unique identifier of the super-org-unit.
	 * 
	 * @return List with all subordinated org-units of an org-unit
	 *         (maybe empty).
	 */
	List<IOrganizationalUnit> obtainSubOrgUnitsForOrgUnit(String orgUnitId);

	/**
	 * returns all subordinated org-units of an org-unit including its members and roles in a list. 
	 * If no org-units are found, the result-list will be empty (not null).
	 * 
	 * @param orgUnitId
	 *          : the unique identifier of the super-org-unit.
	 * 
	 * @return List with all subordinated org-units of an org-unit including its members 
	 *         (maybe empty).
	 */
	List<IOrganizationalUnit> obtainSubOrgUnitsForOrgUnitInclMembers(String orgUnitId);

	/**
	 * returns all org-units, which are roots (i.e. the highest in an
	 * organization) in a list. If no org-units are found, the result-list will be
	 * empty (not null).
	 * 
	 * @return List with all root-org-units.
	 */
	List<IOrganizationalUnit> obtainRootOrgUnits();
	
	/**
	 * returns all members of a given org-unit in a list. If the org-unit has no members,
	 * the result-list will be empty (not null).
	 * 
	 * @param orgUnitId: the org-unit-id, of which the members are needed.
	 * 
	 * @return List with all members.
	 */
	List<IOrgUnitMember> findMembersForOrgUnit(String orgUnitId);
}
