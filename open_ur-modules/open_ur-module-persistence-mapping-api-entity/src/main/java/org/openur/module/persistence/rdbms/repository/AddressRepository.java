package org.openur.module.persistence.rdbms.repository;

import org.openur.module.persistence.rdbms.entity.PAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository
	extends JpaRepository<PAddress, Long>
{
}
