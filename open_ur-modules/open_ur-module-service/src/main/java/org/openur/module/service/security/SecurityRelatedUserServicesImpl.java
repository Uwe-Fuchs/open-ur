package org.openur.module.service.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.security.IRole;
import org.openur.module.persistence.security.ISecurityDao;

public class SecurityRelatedUserServicesImpl
	implements ISecurityRelatedUserServices
{
	private ISecurityDao securityDao;
	
	@Inject	
	public void setSecurityDao(ISecurityDao securityDao)
	{
		this.securityDao = securityDao;
	}

	@Override
	public IRole findRoleById(String roleId)
	{
		return securityDao.findRoleById(roleId);
	}

	@Override
	public Set<IRole> obtainAllRoles()
	{
		List<IRole> roleList = securityDao.obtainAllRoles();
		
		return buildSetFromList(roleList);
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
	public IPermission findPermissionByName(String permissionName, IApplication application)
	{
		return securityDao.findPermissionByName(permissionName, application);
	}

	@Override
	public Set<IPermission> obtainPermissionsForApp(IApplication application)
	{
		List<IPermission> permissionList = securityDao.obtainPermissionsForApp(application);
		
		return buildSetFromList(permissionList);
	}

	@Override
	public Set<IPermission> obtainAllPermissions()
	{
		List<IPermission> permissionList = securityDao.obtainAllPermissions();
		
		return buildSetFromList(permissionList);
	}
	
	private static <T> Set<T> buildSetFromList(List<T> resultList)
	{
		Set<T> resultSet = new HashSet<>();
		
		if (CollectionUtils.isNotEmpty(resultList)) {
			resultSet.addAll(resultList);
		}
		
		return resultSet;
	}
}
