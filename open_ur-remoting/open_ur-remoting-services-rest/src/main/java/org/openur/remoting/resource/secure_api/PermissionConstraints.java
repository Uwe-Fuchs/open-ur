package org.openur.remoting.resource.secure_api;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURPermissionBuilder;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.security.authorization.OpenURRoleBuilder;
import org.openur.module.util.data.PermissionScope;

/**
 * all possible permission-constraints when securing a service.
 * 
 * @author info@uwefuchs.com
 */
public class PermissionConstraints
{
	public static final String REMOTE_READ = "remoteRead";
	public static final String REMOTE_CREATE = "remoteCreate";
	public static final String REMOTE_UPDATE = "remoteUpdate";
	public static final String REMOTE_DELETE = "remoteDelete";
	public static final String REMOTE_CHECK_PERMISSION = "remoteCheckPermission";
	public static final String REMOTE_CHECK_AUTHENTICATION = "remoteCheckAuthentication";
	
	public static OpenURPermission createRemoteReadPermission(OpenURApplication application)
	{
		return createRemotePermission(REMOTE_READ, application);
	}
	
	public static OpenURRole createRemoteReadRole(OpenURApplication application)
	{
		return createRemoteRole(REMOTE_READ, application);
	}
	
	public static OpenURPermission createRemoteCreatePermission(OpenURApplication application)
	{
		return createRemotePermission(REMOTE_CREATE, application);
	}
	
	public static OpenURRole createRemoteCreateRole(OpenURApplication application)
	{
		return createRemoteRole(REMOTE_CREATE, application);
	}
	
	public static OpenURPermission createRemoteUpdatePermission(OpenURApplication application)
	{
		return createRemotePermission(REMOTE_UPDATE, application);
	}
	
	public static OpenURRole createRemoteUpdateRole(OpenURApplication application)
	{
		return createRemoteRole(REMOTE_UPDATE, application);
	}
	
	public static OpenURPermission createRemoteDeletePermission(OpenURApplication application)
	{
		return createRemotePermission(REMOTE_DELETE, application);
	}
	
	public static OpenURRole createRemoteDeleteRole(OpenURApplication application)
	{
		return createRemoteRole(REMOTE_DELETE, application);
	}
	
	public static OpenURPermission createRemoteCheckPermPermission(OpenURApplication application)
	{
		return createRemotePermission(REMOTE_CHECK_PERMISSION, application);
	}
	
	public static OpenURRole createRemoteCheckPermRole(OpenURApplication application)
	{
		return createRemoteRole(REMOTE_CHECK_PERMISSION, application);
	}
	
	public static OpenURPermission createRemoteCheckAuthenticationPermission(OpenURApplication application)
	{
		return createRemotePermission(REMOTE_CHECK_AUTHENTICATION, application);
	}
	
	public static OpenURRole createRemoteCheckCheckAuthenticationRole(OpenURApplication application)
	{
		return createRemoteRole(REMOTE_CHECK_AUTHENTICATION, application);
	}
	
	private static OpenURPermission createRemotePermission(String permissionText, OpenURApplication application)
	{
		return new OpenURPermissionBuilder(permissionText, application)
				.permissionScope(PermissionScope.SELECTED)
				.creationDate(LocalDateTime.now())
				.build();
	}
	
	private static OpenURRole createRemoteRole(String permissionText, OpenURApplication application)
	{
		OpenURPermission perm = createRemotePermission(permissionText, application);

		OpenURRoleBuilder roleBuilder = new OpenURRoleBuilder(permissionText);
		
		return roleBuilder
				.creationDate(LocalDateTime.now())
				.permissions(new HashSet<OpenURPermission>(Arrays.asList(perm)), roleBuilder)
				.build();
	}
}
