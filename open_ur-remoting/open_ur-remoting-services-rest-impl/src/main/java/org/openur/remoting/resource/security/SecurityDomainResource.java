package org.openur.remoting.resource.security;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
	@GET
	@Path("/role/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public IRole findRoleById(@PathParam("id") String roleId)
	{
		return securityDomianServices.findRoleById(roleId);
	}

	@Override
	@GET
	@Path("/role/name/{name}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public IRole findRoleByName(@PathParam("name") String roleName)
	{
		return securityDomianServices.findRoleByName(roleName);
	}

	@Override
	@GET
	@Path("/role/all")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Set<IRole> obtainAllRoles()
	{
		return securityDomianServices.obtainAllRoles();
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
	@Path("/permission/text")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public IPermission findPermission(@QueryParam("text") String permissionText, @QueryParam("appName") String applicationName)
	{
		return securityDomianServices.findPermission(permissionText, applicationName);
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