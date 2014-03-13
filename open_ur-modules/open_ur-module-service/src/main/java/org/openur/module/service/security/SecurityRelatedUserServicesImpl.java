package org.openur.module.service.security;

import java.util.Set;

import javax.inject.Inject;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IRole> obtainAllRoles()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IPermission> obtainPermissionsPerRole(IRole role)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
