package org.openur.module.persistence.dao.rdbms;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.persistence.dao.IOrgUnitDao;
import org.openur.module.persistence.mapper.rdbms.IOrgUnitMemberMapper;
import org.openur.module.persistence.mapper.rdbms.IOrganizationalUnitMapper;
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
	private IOrganizationalUnitMapper<? extends IOrganizationalUnit> organizationalUnitMapper;
	
	@Inject
	private IOrgUnitMemberMapper<? extends IOrgUnitMember> orgUnitMemberMapper;
	
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
	 * @return the organizational-unit or null, if no
	 *         organizational-unit is found.
	 * 
	 * @throws NumberFormatException
	 *           , if orgUnitId cannot be casted into a long-value.
	 */
	@Override
	public IOrganizationalUnit findOrgUnitById(String orgUnitId)
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
	 * @return the organizational-unit including its members and
	 *         roles or null, if no organizational-unit is found.
	 * 
	 * @throws NumberFormatException
	 *           , if orgUnitId cannot be casted into a long-value.
	 */
	@Override
	public IOrganizationalUnit findOrgUnitAndMembersById(String orgUnitId)
		throws NumberFormatException
	{
		return findOrgUnitById_internal(orgUnitId, true, true);
	}
	
	private IOrganizationalUnit findOrgUnitById_internal(String orgUnitId, boolean inclMembers, boolean inclRoles)
		throws NumberFormatException
	{
		long orgUnitIdL = Long.parseLong(orgUnitId);

		POrganizationalUnit persistable = orgUnitRepository.findOne(orgUnitIdL);

		if (persistable == null)
		{
			return null;
		}
		
		return organizationalUnitMapper.mapFromEntity(persistable, inclMembers, inclRoles);
	}

	@Override
	public IOrganizationalUnit findOrgUnitByNumber(String orgUnitNumber)
	{
		return findOrgUnitByNumber_internal(orgUnitNumber, false, false);
	}

	@Override
	public IOrganizationalUnit findOrgUnitAndMembersByNumber(String orgUnitNumber)
	{
		return findOrgUnitByNumber_internal(orgUnitNumber, true, true);
	}
	
	private IOrganizationalUnit findOrgUnitByNumber_internal(String orgUnitNumber, boolean inclMembers, boolean inclRoles)
	{
		POrganizationalUnit persistable = orgUnitRepository.findOrganizationalUnitByNumber(orgUnitNumber);

		if (persistable == null)
		{
			return null;
		}
		
		return organizationalUnitMapper.mapFromEntity(persistable, inclMembers, inclRoles);
	}

	@Override
	public List<IOrganizationalUnit> obtainAllOrgUnits()
	{
		List<POrganizationalUnit> allOrgUnits = orgUnitRepository.findAll();
		
		return mapEntityOrgUnitListToImmutable(allOrgUnits, false);
	}

	@Override
	public List<IOrganizationalUnit> obtainAllOrgUnitsInclMembers()
	{
		List<POrganizationalUnit> allOrgUnits = orgUnitRepository.findAll();
		
		return mapEntityOrgUnitListToImmutable(allOrgUnits, true);
	}

	@Override
	public List<IOrganizationalUnit> obtainSubOrgUnitsForOrgUnit(String orgUnitId)
	{
		long orgUnitIdL = Long.parseLong(orgUnitId);
		
		List<POrganizationalUnit> subOrgUnits = orgUnitRepository.findSubOrgUnitsForOrgUnit(orgUnitIdL);
		
		return mapEntityOrgUnitListToImmutable(subOrgUnits, false);
	}

	@Override
	public List<IOrganizationalUnit> obtainSubOrgUnitsForOrgUnitInclMembers(String orgUnitId)
	{
		long orgUnitIdL = Long.parseLong(orgUnitId);
		
		List<POrganizationalUnit> subOrgUnits = orgUnitRepository.findSubOrgUnitsForOrgUnit(orgUnitIdL);
		
		return mapEntityOrgUnitListToImmutable(subOrgUnits, true);
	}

	@Override
	public List<IOrganizationalUnit> obtainRootOrgUnits()
	{
		List<POrganizationalUnit> rootOrgUnits = orgUnitRepository.findRootOrgUnits();
		
		return mapEntityOrgUnitListToImmutable(rootOrgUnits, false);
	}

	@Override
	public List<IOrgUnitMember> findMembersForOrgUnit(String orgUnitId)
	{
		long orgUnitIdL = Long.parseLong(orgUnitId);
		
		List<POrgUnitMember> orgUnitMembers = orgUnitMemberRepository.findOrgUnitMemberByOrgUnitId(orgUnitIdL);
		
		return orgUnitMembers
					.stream()
					.map(o -> orgUnitMemberMapper.mapFromEntity(o, orgUnitId, true))
					.collect(Collectors.toList());
	}
	
	private List<IOrganizationalUnit> mapEntityOrgUnitListToImmutable(List<POrganizationalUnit> entities, boolean inclMembers)
	{
		return entities
					.stream()
					.map(o -> organizationalUnitMapper.mapFromEntity(o, inclMembers, inclMembers))
					.collect(Collectors.toList());
	}
}
