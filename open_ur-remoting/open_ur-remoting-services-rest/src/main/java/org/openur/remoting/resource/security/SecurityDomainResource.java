package org.openur.remoting.resource.security;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.service.security.ISecurityDomainServices;

@Path("/securitydomain")
public class SecurityDomainResource
	implements ISecurityDomainServices
{
	@Inject
	private ISecurityDomainServices securityDomianServices;
	
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
	@GET
	@Path("/permission/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public IPermission findPermissionById(@PathParam("id") String permissionId)
	{
		return securityDomianServices.findPermissionById(permissionId);
	}

	@Override
	@GET
	@Path("/permission/name/{name}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public IPermission findPermissionByName(@PathParam("name") String permissionName)
	{
		return securityDomianServices.findPermissionByName(permissionName);
	}

	@Override
	@GET
	@Path("/permission/app/{app}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Set<IPermission> obtainPermissionsForApp(@PathParam("app") String applicationName)
	{
		return securityDomianServices.obtainPermissionsForApp(applicationName);
	}

	@Override
	@GET
	@Path("/permission/all")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Set<IPermission> obtainAllPermissions()
	{
		return securityDomianServices.obtainAllPermissions();
	}
}
