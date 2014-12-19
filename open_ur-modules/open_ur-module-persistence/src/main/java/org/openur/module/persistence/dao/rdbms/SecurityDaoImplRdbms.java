package org.openur.module.persistence.dao.rdbms;

import java.util.List;

import javax.inject.Inject;

import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.persistence.dao.ISecurityDao;
import org.openur.module.persistence.mapper.rdbms.PermissionMapper;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.openur.module.persistence.rdbms.repository.PermissionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class SecurityDaoImplRdbms
	implements ISecurityDao
{
	private PermissionRepository permissionRepository;

	public SecurityDaoImplRdbms()
	{
		super();
	}

	@Inject
	public void setPermissionRepository(PermissionRepository permissionRepository)
	{
		this.permissionRepository = permissionRepository;
	}

	@Override
	public IPermission findPermissionById(String permissionId)
	{
		PPermission persistable = permissionRepository.findOne(Long.valueOf(permissionId));
		
		if (persistable == null)
		{
			return null;
		}
		
		return PermissionMapper.mapFromEntity(persistable);
	}

	@Override
	public IPermission findPermissionByName(String permissionName, String applicationName)
	{
		PPermission persistable = permissionRepository.findPermissionByPermissionNameAndApp(permissionName, applicationName);
		
		if (persistable == null)
		{
			return null;
		}
		
		return PermissionMapper.mapFromEntity(persistable);
	}

	@Override
	public List<IPermission> obtainPermissionsForApp(IApplication application)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IPermission> obtainAllPermissions()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IRole> obtainAllRoles()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRole findRoleById(String roleId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRole findRoleByName(String roleName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAuthorizableOrgUnit findAuthOrgUnitById(String orgUnitId)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
