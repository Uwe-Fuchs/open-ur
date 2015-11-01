package org.openur.remoting.client.ws.rs.security;

import java.util.Set;

import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.service.security.ISecurityDomainServices;
import org.openur.remoting.client.ws.rs.AbstractResourceClient;

public class SecurityDomainResourceClient
	extends AbstractResourceClient
	implements ISecurityDomainServices
{

	public SecurityDomainResourceClient(String baseUrl)
	{
		super(baseUrl);
		// TODO Auto-generated constructor stub
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
	public Set<IRole> obtainAllRoles()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPermission findPermissionById(String permissionId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPermission findPermission(String permissionText, String applicationName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IPermission> obtainPermissionsForApp(String applicationName)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IPermission> obtainAllPermissions()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
