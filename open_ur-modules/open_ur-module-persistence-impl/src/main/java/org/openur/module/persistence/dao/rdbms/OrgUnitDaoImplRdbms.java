package org.openur.module.persistence.dao.rdbms;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.openur.module.domain.security.authorization.IAuthorizableMember;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.persistence.dao.IOrgUnitDao;
import org.openur.module.persistence.mapper.rdbms.OrganizationalUnitMapper;
import org.openur.module.persistence.mapper.rdbms.OrganizationalUnitMapper.OrgUnitMemberMapper;
import org.openur.module.persistence.rdbms.entity.POrgUnitMember;
import org.openur.module.persistence.rdbms.entity.POrganizationalUnit;
import org.openur.module.persistence.rdbms.repository.OrgUnitMemberRepository;
import org.openur.module.persistence.rdbms.repository.OrgUnitRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class OrgUnitDaoImplRdbms
	implements IOrgUnitDao
{
	@Inject
	private OrgUnitRepository orgUnitRepository;

	@Inject
	private OrgUnitMemberRepository orgUnitMemberRepository;
	
	public OrgUnitDaoImplRdbms()
	{
		super();
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
	public IAuthorizableOrgUnit findOrgUnitAndMembersById(String orgUnitId)
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
		
		return mapEntityOrgUnitListToImmutable(allOrgUnits, false);
	}

	@Override
	public List<IAuthorizableOrgUnit> obtainAllOrgUnitsInclMembers()
	{
		List<POrganizationalUnit> allOrgUnits = orgUnitRepository.findAll();
		
		return mapEntityOrgUnitListToImmutable(allOrgUnits, true);
	}

	@Override
	public List<IAuthorizableOrgUnit> obtainSubOrgUnitsForOrgUnit(String orgUnitId)
	{
		long orgUnitIdL = Long.parseLong(orgUnitId);
		
		List<POrganizationalUnit> subOrgUnits = orgUnitRepository.findSubOrgUnitsForOrgUnit(orgUnitIdL);
		
		return mapEntityOrgUnitListToImmutable(subOrgUnits, false);
	}

	@Override
	public List<IAuthorizableOrgUnit> obtainSubOrgUnitsForOrgUnitInclMembers(String orgUnitId)
	{
		long orgUnitIdL = Long.parseLong(orgUnitId);
		
		List<POrganizationalUnit> subOrgUnits = orgUnitRepository.findSubOrgUnitsForOrgUnit(orgUnitIdL);
		
		return mapEntityOrgUnitListToImmutable(subOrgUnits, true);
	}

	@Override
	public List<IAuthorizableOrgUnit> obtainRootOrgUnits()
	{
		List<POrganizationalUnit> rootOrgUnits = orgUnitRepository.findRootOrgUnits();
		
		return mapEntityOrgUnitListToImmutable(rootOrgUnits, false);
	}

	@Override
	public List<IAuthorizableMember> findMembersForOrgUnit(String orgUnitId)
	{
		long orgUnitIdL = Long.parseLong(orgUnitId);
		
		List<POrgUnitMember> orgUnitMembers = orgUnitMemberRepository.findOrgUnitMemberByOrgUnitId(orgUnitIdL);
		
		return orgUnitMembers
					.stream()
					.map(o -> OrgUnitMemberMapper.mapFromEntity(o, orgUnitId, true))
					.collect(Collectors.toList());
	}
	
	private List<IAuthorizableOrgUnit> mapEntityOrgUnitListToImmutable(List<POrganizationalUnit> entities, boolean inclMembers) {
		return entities
					.stream()
					.map(o -> OrganizationalUnitMapper.mapFromEntity(o, inclMembers, inclMembers))
					.collect(Collectors.toList());
	}
}
