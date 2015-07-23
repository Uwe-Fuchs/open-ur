package org.openur.module.persistence.rdbms.repository;

import org.openur.module.persistence.rdbms.entity.PApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository
	extends JpaRepository<PApplication, Long>
{
	PApplication findApplicationByApplicationName(String applicationName);
}
