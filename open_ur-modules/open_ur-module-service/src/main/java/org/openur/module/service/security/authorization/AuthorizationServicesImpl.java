package org.openur.module.service.security.authorization;

import java.util.Set;

import org.openur.module.domain.IPrincipalUser;
import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.security.IPermission;
import org.openur.module.domain.security.IRole;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;

public class AuthorizationServicesImpl
	implements IAuthorizationServices
{

	@Override
	public Set<IRole> obtainAllRoles()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRole findRolePerId(String roleId)
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

	@Override
	public Boolean hasPermission(IPrincipalUser user, IApplication app,
		IOrganizationalUnit orgUnit, IPermission permission)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
