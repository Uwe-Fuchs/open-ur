package org.openur.remoting.resource.security;

import static org.openur.remoting.resource.secure_api.PermissionConstraints.REMOTE_READ;

import java.util.Set;

import javax.annotation.security.RolesAllowed;
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

@Path(SecurityDomainResource.SECURITY_DOMAIN_RESOURCE_PATH)
public class SecurityDomainResource
	implements ISecurityDomainServices
{
	public static final String SECURITY_DOMAIN_RESOURCE_PATH = "securitydomain/";
	public static final String PERMISSION_PER_ID_RESOURCE_PATH = "permission/id/";
	public static final String PERMISSION_PER_TEXT_RESOURCE_PATH = "permission/text/";
	public static final String ALL_PERMISSIONS_RESOURCE_PATH = "permission/all";
	public static final String PERMISSIONS_PER_APP_RESOURCE_PATH = "permission/app/";
	public static final String ROLE_PER_ID_RESOURCE_PATH = "role/id/";
	public static final String ROLE_PER_NAME_RESOURCE_PATH = "role/name/";
	public static final String ALL_ROLES_RESOURCE_PATH = "role/all";
	
	@Inject
	private ISecurityDomainServices securityDomainServices;
	
	@Override
	@GET
	@Path(ROLE_PER_ID_RESOURCE_PATH + "{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed(REMOTE_READ)
	public IRole findRoleById(@PathParam("id") String roleId)
	{
		return securityDomainServices.findRoleById(roleId);
	}

	@Override
	@GET
	@Path(ROLE_PER_NAME_RESOURCE_PATH + "{name}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed(REMOTE_READ)
	public IRole findRoleByName(@PathParam("name") String roleName)
	{
		return securityDomainServices.findRoleByName(roleName);
	}

	@Override
	@GET
	@Path(ALL_ROLES_RESOURCE_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed(REMOTE_READ)
	public Set<IRole> obtainAllRoles()
	{
		return securityDomainServices.obtainAllRoles();
	}

	@Override
	@GET
	@Path(PERMISSION_PER_ID_RESOURCE_PATH + "{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed(REMOTE_READ)
	public IPermission findPermissionById(@PathParam("id") String permissionId)
	{
		return securityDomainServices.findPermissionById(permissionId);
	}

	@Override
	@GET
	@Path(PERMISSION_PER_TEXT_RESOURCE_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed(REMOTE_READ)
	public IPermission findPermission(@QueryParam("text") String permissionText, @QueryParam("appName") String applicationName)
	{
		return securityDomainServices.findPermission(permissionText, applicationName);
	}

	@Override
	@GET
	@Path(PERMISSIONS_PER_APP_RESOURCE_PATH + "{app}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed(REMOTE_READ)
	public Set<IPermission> obtainPermissionsForApp(@PathParam("app") String applicationName)
	{
		return securityDomainServices.obtainPermissionsForApp(applicationName);
	}

	@Override
	@GET
	@Path(ALL_PERMISSIONS_RESOURCE_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed(REMOTE_READ)
	public Set<IPermission> obtainAllPermissions()
	{
		return securityDomainServices.obtainAllPermissions();
	}
}
