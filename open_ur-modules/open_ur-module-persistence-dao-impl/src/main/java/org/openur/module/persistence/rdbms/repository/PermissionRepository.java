package org.openur.module.persistence.rdbms.repository;

import java.util.List;

import org.openur.module.persistence.rdbms.entity.PPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PermissionRepository
	extends JpaRepository<PPermission, Long>
{
	List<PPermission> findPermissionsByApplicationApplicationName(String applicationName);

	@Query("select p from PERMISSION p where p.permissionText = :permissionText and p.application.applicationName = :applicationName")
	PPermission findPermission(@Param("permissionText") String permissionText, @Param("applicationName") String applicationName);
}
