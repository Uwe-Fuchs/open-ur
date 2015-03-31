package org.openur.module.persistence.dao.rdbms;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.persistence.dao.IUserStructureDao;
import org.openur.module.persistence.mapper.rdbms.OrganizationalUnitMapper;
import org.openur.module.persistence.mapper.rdbms.PersonMapper;
import org.openur.module.persistence.mapper.rdbms.TechnicalUserMapper;
import org.openur.module.persistence.rdbms.entity.POrganizationalUnit;
import org.openur.module.persistence.rdbms.entity.PPerson;
import org.openur.module.persistence.rdbms.entity.PTechnicalUser;
import org.openur.module.persistence.rdbms.repository.OrgUnitRepository;
import org.openur.module.persistence.rdbms.repository.PersonRepository;
import org.openur.module.persistence.rdbms.repository.TechnicalUserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class UserStructureDaoImplRdbms
	implements IUserStructureDao
{
	@Inject
	private PersonRepository personRepository;

	@Inject
	private TechnicalUserRepository technicalUserRepository;

	@Inject
	private OrgUnitRepository orgUnitRepository;

	public UserStructureDaoImplRdbms()
	{
		super();
	}

	/**
	 * searches a person via it's unique identifier.
	 * 
	 * @param personId
	 *          : the unique identifier of the person.
	 * 
	 * @return the person or null, if no person is found.
	 * 
	 * @throws NumberFormatException
	 *           , if personId cannot be casted into a long-value.
	 */
	@Override
	public IPerson findPersonById(String personId)
		throws NumberFormatException
	{
		long personIdL = Long.parseLong(personId);

		PPerson persistable = personRepository.findOne(personIdL);

		if (persistable == null)
		{
			return null;
		}

		return PersonMapper.mapFromEntity(persistable);
	}

	@Override
	public IPerson findPersonByNumber(String personalNumber)
	{
		PPerson persistable = personRepository.findPersonByNumber(personalNumber);

		if (persistable == null)
		{
			return null;
		}

		return PersonMapper.mapFromEntity(persistable);
	}

	@Override
	public List<IPerson> obtainAllPersons()
	{
		List<PPerson> persons = personRepository.findAll();

		return persons
				.stream()
				.map(PersonMapper::mapFromEntity)
				.collect(Collectors.toList());
	}

	/**
	 * searches a technical user via it's unique identifier.
	 * 
	 * @param techUserId
	 *          : the unique identifier of the technical user.
	 * 
	 * @return the technical user or null, if no user is found.
	 * 
	 * @throws NumberFormatException
	 *           , if techUserId cannot be casted into a long-value.
	 */
	@Override
	public ITechnicalUser findTechnicalUserById(String techUserId)
		throws NumberFormatException
	{
		long techUserIdL = Long.parseLong(techUserId);

		PTechnicalUser persistable = technicalUserRepository.findOne(techUserIdL);

		if (persistable == null)
		{
			return null;
		}

		return TechnicalUserMapper.mapFromEntity(persistable);
	}

	@Override
	public ITechnicalUser findTechnicalUserByNumber(String techUserNumber)
	{
		PTechnicalUser persistable = technicalUserRepository.findTechnicalUserByNumber(techUserNumber);

		if (persistable == null)
		{
			return null;
		}

		return TechnicalUserMapper.mapFromEntity(persistable);
	}

	@Override
	public List<ITechnicalUser> obtainAllTechnicalUsers()
	{
		List<PTechnicalUser> techUsers = technicalUserRepository.findAll();

		return techUsers
				.stream()
				.map(TechnicalUserMapper::mapFromEntity)
				.collect(Collectors.toList());
	}

	/**
	 * searches an organizational-unit via the unique identifier.
	 * 
	 * @param orgUnitId
	 *          : the unique identifier of the organizational-unit.
	 * 
	 * @return the (authorizable) organizational-unit or null, if no
	 *         organizational-unit is found.
	 * 
	 * @throws NumberFormatException
	 *           , if orgUnitId cannot be casted into a long-value.
	 */
	@Override
	public IAuthorizableOrgUnit findOrgUnitById(String orgUnitId)
		throws NumberFormatException
	{
		return findOrgUnitById_internal(orgUnitId, false, false);
	}

	/**
	 * searches an organizational-unit including its members via the unique
	 * identifier.
	 * 
	 * @param orgUnitId
	 *          : the unique identifier of the organizational-unit.
	 * 
	 * @return the (authorizable) organizational-unit including its members or
	 *         null, if no organizational-unit is found.
	 * 
	 * @throws NumberFormatException
	 *           , if orgUnitId cannot be casted into a long-value.
	 */
	@Override
	public IAuthorizableOrgUnit findOrgUnitAndMembersById(String orgUnitId)
		throws NumberFormatException
	{
		return findOrgUnitById_internal(orgUnitId, true, false);
	}

	/**
	 * searches an organizational-unit including its members and roles via the
	 * unique identifier.
	 * 
	 * @param orgUnitId
	 *          : the unique identifier of the organizational-unit.
	 * 
	 * @return the (authorizable) organizational-unit including its members and
	 *         roles or null, if no organizational-unit is found.
	 * 
	 * @throws NumberFormatException
	 *           , if orgUnitId cannot be casted into a long-value.
	 */
	@Override
	public IAuthorizableOrgUnit findOrgUnitAndMembersRolesById(String orgUnitId)
		throws NumberFormatException
	{
		return findOrgUnitById_internal(orgUnitId, true, true);
	}
	
	private IAuthorizableOrgUnit findOrgUnitById_internal(String orgUnitId, boolean inclMembers, boolean inclRoles)
		throws NumberFormatException
	{
		long orgUnitIdL = Long.parseLong(orgUnitId);

		POrganizationalUnit persistable = orgUnitRepository.findOne(orgUnitIdL);

		if (persistable == null)
		{
			return null;
		}
		
		return OrganizationalUnitMapper.mapFromEntity(persistable, inclMembers, inclRoles);
	}

	@Override
	public IAuthorizableOrgUnit findOrgUnitByNumber(String orgUnitNumber)
	{
		return findOrgUnitByNumber_internal(orgUnitNumber, false, false);
	}

	@Override
	public IAuthorizableOrgUnit findOrgUnitAndMembersByNumber(String orgUnitNumber)
	{
		return findOrgUnitByNumber_internal(orgUnitNumber, true, false);
	}

	@Override
	public IAuthorizableOrgUnit findOrgUnitAndMembersRolesByNumber(String orgUnitNumber)
	{
		return findOrgUnitByNumber_internal(orgUnitNumber, true, true);
	}
	
	private IAuthorizableOrgUnit findOrgUnitByNumber_internal(String orgUnitNumber, boolean inclMembers, boolean inclRoles)
	{
		POrganizationalUnit persistable = orgUnitRepository.findOrganizationalUnitByNumber(orgUnitNumber);

		if (persistable == null)
		{
			return null;
		}
		
		return OrganizationalUnitMapper.mapFromEntity(persistable, inclMembers, inclRoles);
	}

	@Override
	public List<IAuthorizableOrgUnit> obtainAllOrgUnits()
	{
		List<POrganizationalUnit> allOrgUnits = orgUnitRepository.findAll();
		
		return allOrgUnits
					.stream()
					.map(o -> OrganizationalUnitMapper.mapFromEntity(o, false, false))
					.collect(Collectors.toList());
	}

	@Override
	public List<IAuthorizableOrgUnit> obtainSubOrgUnitsForOrgUnit(String orgUnitId, boolean inclMembers)
	{
		long orgUnitIdL = Long.parseLong(orgUnitId);
		
		List<POrganizationalUnit> subOrgUnits = orgUnitRepository.findSubOrgUnitsForOrgUnit(orgUnitIdL);
		
		return subOrgUnits
					.stream()
					.map(o -> OrganizationalUnitMapper.mapFromEntity(o, false, false))
					.collect(Collectors.toList());
	}

	@Override
	public List<IAuthorizableOrgUnit> obtainRootOrgUnits()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
