package org.openur.module.service.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.persistence.dao.ISecurityDao;

public class SecurityDomainServicesImpl
	implements ISecurityDomainServices
{
	@Inject
	private ISecurityDao securityDao;

	@Override
	public IRole findRoleById(String roleId)
	{
		return securityDao.findRoleById(roleId);
	}

	@Override
	public Set<IRole> obtainAllRoles()
	{
		List<IRole> roleList = securityDao.obtainAllRoles();
		
		return new HashSet<>(roleList);
	}

	@Override
	public IRole findRoleByName(String roleName)
	{
		return securityDao.findRoleByName(roleName);
	}

	@Override
	public IPermission findPermissionById(String permissionId)
	{
		return securityDao.findPermissionById(permissionId);
	}

	@Override
	public IPermission findPermissionByText(String permissionText)
	{
		return securityDao.findPermissionByText(permissionText);
	}

	@Override
	public Set<IPermission> obtainPermissionsForApp(String applicationName)
	{
		List<IPermission> permissionList = securityDao.obtainPermissionsForApp(applicationName);
		
		return new HashSet<>(permissionList);
	}

	@Override
	public Set<IPermission> obtainAllPermissions()
	{
		List<IPermission> permissionList = securityDao.obtainAllPermissions();
		
		return new HashSet<>(permissionList);
	}
}
