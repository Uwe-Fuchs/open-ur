package org.openur.module.persistence.rdbms.repository;

import java.util.List;

import org.openur.module.persistence.rdbms.entity.POrganizationalUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrgUnitRepository
	extends JpaRepository<POrganizationalUnit, Long>
{
	POrganizationalUnit findOrganizationalUnitByNumber(String orgUnitNumber);
	
	@Query("select ou from ORGANIZATIONAL_UNIT ou where ou.superOu.id = :orgUnitId")
	List<POrganizationalUnit> findSubOrgUnitsForOrgUnit(@Param("orgUnitId") Long orgUnitId);
}
