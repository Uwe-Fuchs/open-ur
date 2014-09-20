package org.openur.module.persistence.rdbms.repository.userstructure;

import org.openur.module.persistence.rdbms.entity.userstructure.PPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository
	extends JpaRepository<PPerson, Long>
{
}
