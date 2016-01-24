package org.openur.module.persistence.dao.rdbms;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.persistence.dao.ISecurityDao;
import org.openur.module.persistence.mapper.rdbms.IEntityDomainObjectMapper;
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
	private IEntityDomainObjectMapper<PPermission, ? extends IPermission> permissionMapper;
	
	@Inject
	private IEntityDomainObjectMapper<PRole, ? extends IRole> roleMapper;
	
	@Inject
	private PermissionRepository permissionRepository;
	
	@Inject
	private RoleRepository roleRepository;

	public SecurityDaoImplRdbms()
	{
		super();
	}

	/**
	 * searches a permission-object with the given id.
	 * 
	 * @param permissionId : id of the permission.
	 * 
	 * @return the permission with the given id or null, if no permission is found.
   * 
   * @throws NumberFormatException, if permissionId cannot be casted into a long-value.
	 */
	@Override
	public IPermission findPermissionById(String permissionId)
		throws NumberFormatException
	{
		long permissionIdL = Long.parseLong(permissionId);
		
		PPermission persistable = permissionRepository.findOne(permissionIdL);
		
		if (persistable == null)
		{
			return null;
		}
		
		return permissionMapper.mapFromEntity(persistable);
	}

	@Override
	public IPermission findPermission(String permissionText, String applicationName)
	{
		PPermission persistable = permissionRepository.findPermission(permissionText, applicationName);
		
		if (persistable == null)
		{
			return null;
		}
		
		return permissionMapper.mapFromEntity(persistable);
	}

	@Override
	public List<IPermission> obtainAllPermissions()
	{
		List<PPermission> permissions = permissionRepository.findAll();
		
		return permissions
			.stream()
			.map(permissionMapper::mapFromEntity)
			.collect(Collectors.toList());
	}

	@Override
	public List<IPermission> obtainPermissionsForApp(String applicationName)
	{
		List<PPermission> permissions = permissionRepository.findPermissionsByApplicationApplicationName(applicationName);
		
		return permissions
			.stream()
			.map(permissionMapper::mapFromEntity)
			.collect(Collectors.toList());
	}

	/**
	 * searches a role-object with the given id.
	 * 
	 * @param roleId : id of the role.
	 * 
	 * @return the role with the given id or null, if no role is found.
   * 
   * @throws NumberFormatException, if roleId cannot be casted into a long-value.
	 */
	@Override
	public IRole findRoleById(String roleId)
		throws NumberFormatException
	{
		long roleIdL = Long.parseLong(roleId);
		
		PRole role = roleRepository.findOne(roleIdL);
		
		if (role == null)
		{
			return null;
		}
		
		return roleMapper.mapFromEntity(role);
	}

	@Override
	public IRole findRoleByName(String roleName)
	{
		PRole role = roleRepository.findRoleByRoleName(roleName);
		
		if (role == null)
		{
			return null;
		}
		
		return roleMapper.mapFromEntity(role);
	}

	@Override
	public List<IRole> obtainAllRoles()
	{
		List<PRole> allRoles = roleRepository.findAll();
		
		return allRoles
				.stream()
				.map(roleMapper::mapFromEntity)
				.collect(Collectors.toList());
	}
}
