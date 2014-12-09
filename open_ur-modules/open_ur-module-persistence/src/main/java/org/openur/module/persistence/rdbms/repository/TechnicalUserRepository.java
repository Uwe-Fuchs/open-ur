package org.openur.module.persistence.rdbms.repository;

import org.openur.module.persistence.rdbms.entity.PTechnicalUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicalUserRepository
	extends JpaRepository<PTechnicalUser, Long>
{
}
