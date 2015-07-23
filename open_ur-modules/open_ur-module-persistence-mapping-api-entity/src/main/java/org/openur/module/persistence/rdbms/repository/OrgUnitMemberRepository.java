package org.openur.module.persistence.rdbms.repository;

import java.util.List;

import org.openur.module.persistence.rdbms.entity.POrgUnitMember;
import org.openur.module.persistence.rdbms.entity.POrganizationalUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgUnitMemberRepository
	extends JpaRepository<POrgUnitMember, Long>
{
	List<POrgUnitMember> findOrgUnitMemberByOrgUnit(POrganizationalUnit orgUnit);
	
	List<POrgUnitMember> findOrgUnitMemberByOrgUnitId(Long orgUnitId);
}
