package org.openur.module.service.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.persistence.dao.ISecurityDao;

public class SecurityDomainServicesImpl
	implements ISecurityDomainServices
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
	public IPermission findPermissionByName(String permissionName, IApplication application)
	{
		return securityDao.findPermissionByName(permissionName, application.getApplicationName());
	}

	@Override
	public Set<IPermission> obtainPermissionsForApp(IApplication application)
	{
		List<IPermission> permissionList = securityDao.obtainPermissionsForApp(application);
		
		return new HashSet<>(permissionList);
	}

	@Override
	public Set<IPermission> obtainAllPermissions()
	{
		List<IPermission> permissionList = securityDao.obtainAllPermissions();
		
		return new HashSet<>(permissionList);
	}

	@Override
	public IAuthorizableOrgUnit findAuthOrgUnitById(String orgUnitId)
	{
		return securityDao.findAuthOrgUnitById(orgUnitId); 
	}
	
//	private static <T> Set<T> buildSetFromList(List<T> resultList)
//	{
//		Set<T> resultSet = new HashSet<>(resultList);
//		
//		if (CollectionUtils.isNotEmpty(resultList))
//		{
//			resultSet.addAll(resultList);
//		}
//		
//		return new HashSet<>(resultList);
//	}
}
