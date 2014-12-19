package org.openur.module.persistence.rdbms.repository;

import org.openur.module.persistence.rdbms.entity.PPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PermissionRepository
	extends JpaRepository<PPermission, Long>
{
	@Query("select perm from PERMISSION perm " +
		"where perm.permissionName = :permName " +
		"and perm.application.applicationName = :appName")
	PPermission findPermissionByPermissionNameAndAppName(@Param("permName") String permissionName, @Param("appName") String applicationName);
}
