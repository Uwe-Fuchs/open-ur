package org.openur.module.persistence.rdbms.repository;

import org.openur.module.persistence.rdbms.entity.PRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author info@uwefuchs.com
 */
public interface RoleRepository
	extends JpaRepository<PRole, Long>
{
	PRole findRoleByRoleName(String roleName);
}
