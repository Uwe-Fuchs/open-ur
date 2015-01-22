package org.openur.module.persistence.dao.rdbms;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.persistence.dao.ISecurityDao;
import org.openur.module.persistence.mapper.rdbms.PermissionMapper;
import org.openur.module.persistence.mapper.rdbms.RoleMapper;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.openur.module.persistence.rdbms.entity.PRole;
import org.openur.module.persistence.rdbms.repository.PermissionRepository;
import org.openur.module.persistence.rdbms.repository.RoleRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class SecurityDaoImplRdbms
	implements ISecurityDao
{
	@Inject
	private PermissionRepository permissionRepository;
	
	@Inject
	private RoleRepository roleRepository;

	public SecurityDaoImplRdbms()
	{
		super();
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
	public IPermission findPermissionByName(String permissionName)
	{
		PPermission persistable = permissionRepository.findPermissionByPermissionName(permissionName);
		
		if (persistable == null)
		{
			return null;
		}
		
		return PermissionMapper.mapFromEntity(persistable);
	}

	@Override
	public List<IPermission> obtainAllPermissions()
	{
		List<PPermission> permissions = permissionRepository.findAll();
		
		return permissions
			.stream()
			.map(PermissionMapper::mapFromEntity)
			.collect(Collectors.toList());
	}

	@Override
	public List<IPermission> obtainPermissionsForApp(String applicationName)
	{
		List<PPermission> permissions = permissionRepository.findPermissionsByApplicationApplicationName(applicationName);
		
		return permissions
			.stream()
			.map(PermissionMapper::mapFromEntity)
			.collect(Collectors.toList());
	}

	@Override
	public IRole findRoleById(String roleId)
	{
		PRole role = roleRepository.findOne(Long.valueOf(roleId));
		
		if (role == null)
		{
			return null;
		}
		
		return RoleMapper.mapFromEntity(role);
	}

	@Override
	public IRole findRoleByName(String roleName)
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
}
