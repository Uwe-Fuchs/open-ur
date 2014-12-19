package org.openur.module.persistence.rdbms.repository;

import org.openur.module.persistence.rdbms.entity.PPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository
	extends JpaRepository<PPerson, Long>
{
	PPerson findPersonByNumber(String personalNumber);
}
