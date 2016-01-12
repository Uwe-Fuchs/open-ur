package org.openur.module.persistence.rdbms.repository;

import org.openur.module.persistence.rdbms.entity.PUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository
	extends JpaRepository<PUserAccount, Long>
{
	PUserAccount findUserAccountByUserName(String userName);
}
