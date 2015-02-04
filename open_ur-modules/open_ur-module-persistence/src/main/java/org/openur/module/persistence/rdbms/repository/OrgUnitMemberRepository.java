package org.openur.module.persistence.rdbms.repository;

import org.openur.module.persistence.rdbms.entity.POrgUnitMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgUnitMemberRepository
	extends JpaRepository<POrgUnitMember, Long>
{
}
