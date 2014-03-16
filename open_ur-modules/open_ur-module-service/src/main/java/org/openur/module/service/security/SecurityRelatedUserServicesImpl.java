package org.openur.module.service.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
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
		Set<IRole> roleSet = new HashSet<>();
		
		if (CollectionUtils.isNotEmpty(roleList)) {
			roleSet.addAll(roleList);
		}
		
		return roleSet;
	}

	@Override
	public IRole findRoleByName(String roleName)
	{
		return securityDao.findRoleByName(roleName);
	}
}
