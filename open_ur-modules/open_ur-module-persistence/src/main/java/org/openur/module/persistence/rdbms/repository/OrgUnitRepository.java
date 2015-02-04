package org.openur.module.persistence.rdbms.repository;

import org.openur.module.persistence.rdbms.entity.POrganizationalUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgUnitRepository
	extends JpaRepository<POrganizationalUnit, Long>
{
	POrganizationalUnit findOrganizationalUnitByNumber(String orgUnitNumber);
}
