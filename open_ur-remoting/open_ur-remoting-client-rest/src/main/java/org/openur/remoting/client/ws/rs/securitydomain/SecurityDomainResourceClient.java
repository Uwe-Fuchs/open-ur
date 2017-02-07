package org.openur.remoting.client.ws.rs.securitydomain;

import static org.openur.remoting.resource.securitydomain.SecurityDomainResource.ALL_PERMISSIONS_RESOURCE_PATH;
import static org.openur.remoting.resource.securitydomain.SecurityDomainResource.ALL_ROLES_RESOURCE_PATH;
import static org.openur.remoting.resource.securitydomain.SecurityDomainResource.PERMISSIONS_PER_APP_RESOURCE_PATH;
import static org.openur.remoting.resource.securitydomain.SecurityDomainResource.PERMISSION_PER_ID_RESOURCE_PATH;
import static org.openur.remoting.resource.securitydomain.SecurityDomainResource.PERMISSION_PER_TEXT_RESOURCE_PATH;
import static org.openur.remoting.resource.securitydomain.SecurityDomainResource.ROLE_PER_ID_RESOURCE_PATH;
import static org.openur.remoting.resource.securitydomain.SecurityDomainResource.ROLE_PER_NAME_RESOURCE_PATH;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.IRole;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.service.securitydomain.ISecurityDomainServices;
import org.openur.remoting.client.ws.rs.secure_api.AbstractResourceClient;
import org.openur.remoting.resource.securitydomain.SecurityDomainResource;
import org.openur.remoting.xchange.rest.providers.json.IdentifiableEntitySetProvider;
import org.openur.remoting.xchange.rest.providers.json.PermissionProvider;
import org.openur.remoting.xchange.rest.providers.json.RoleProvider;

public class SecurityDomainResourceClient
	extends AbstractResourceClient
	implements ISecurityDomainServices
{
	@Inject
	public SecurityDomainResourceClient(String baseUrl)
	{
		super(baseUrl + SecurityDomainResource.SECURITY_DOMAIN_RESOURCE_PATH, 
				PermissionProvider.class, RoleProvider.class, IdentifiableEntitySetProvider.class);
	}

	@Override
	public IRole findRoleById(String roleId)
	{
		String url = new StringBuilder()
				.append(ROLE_PER_ID_RESOURCE_PATH)
				.append(roleId)
				.toString();
		
		return performRestCall_GET(url, MediaType.APPLICATION_JSON, OpenURRole.class);
	}

	@Override
	public IRole findRoleByName(String roleName)
	{
		String url = new StringBuilder()
				.append(ROLE_PER_NAME_RESOURCE_PATH)
				.append(roleName)
				.toString();
		
		return performRestCall_GET(url, MediaType.APPLICATION_JSON, OpenURRole.class);
	}

	@Override
	public Set<IRole> obtainAllRoles()
	{
		return performRestCall_GET(
					ALL_ROLES_RESOURCE_PATH, MediaType.APPLICATION_JSON, 
					new GenericType<Set<IRole>>(new ParameterizedTypeImpl(Set.class, OpenURRole.class)));
	}

	@Override
	public IPermission findPermissionById(String permissionId)
	{
		String url = new StringBuilder()
				.append(PERMISSION_PER_ID_RESOURCE_PATH)
				.append(permissionId)
				.toString();
		
		return performRestCall_GET(url, MediaType.APPLICATION_JSON, OpenURPermission.class);
	}

	@Override
	public IPermission findPermission(String permissionText, String applicationName)
	{
		String url = new StringBuilder()
				.append(PERMISSION_PER_TEXT_RESOURCE_PATH)
				.append("?text=")
				.append(permissionText)
				.append("&appName=")
				.append(applicationName)
				.toString();
		
		return performRestCall_GET(url, MediaType.APPLICATION_JSON, OpenURPermission.class);
	}

	@Override
	public Set<IPermission> obtainPermissionsForApp(String applicationName)
	{
		String url = new StringBuilder()
				.append(PERMISSIONS_PER_APP_RESOURCE_PATH)
				.append(applicationName)
				.toString();
		
		return performRestCall_GET(
				url, MediaType.APPLICATION_JSON, new GenericType<Set<IPermission>>(new ParameterizedTypeImpl(Set.class, OpenURPermission.class)));
	}

	@Override
	public Set<IPermission> obtainAllPermissions()
	{
		return performRestCall_GET(
				ALL_PERMISSIONS_RESOURCE_PATH, MediaType.APPLICATION_JSON, 
				new GenericType<Set<IPermission>>(new ParameterizedTypeImpl(Set.class, OpenURPermission.class)));
	}
}
