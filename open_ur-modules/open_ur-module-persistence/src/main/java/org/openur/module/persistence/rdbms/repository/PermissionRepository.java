package org.openur.module.persistence.rdbms.repository;

import java.util.List;

import org.openur.module.persistence.rdbms.entity.PApplication;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository
	extends JpaRepository<PPermission, Long>
{
	List<PPermission> findPermissionsByApplication(PApplication application);

	PPermission findPermissionByPermissionName(String permissionName);
}
