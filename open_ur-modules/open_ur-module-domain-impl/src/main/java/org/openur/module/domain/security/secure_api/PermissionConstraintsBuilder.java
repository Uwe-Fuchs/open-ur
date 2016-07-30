package org.openur.module.domain.security.secure_api;

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
 * Convenience-class delivering permissions and roles for accessing remote-services.
 * 
 * @author info@uwefuchs.com
 */
public class PermissionConstraintsBuilder
{
	public static OpenURPermission createRemoteReadPermission(OpenURApplication application)
	{
		return createRemotePermission(PermissionConstraints.REMOTE_READ, application);
	}
	
	public static OpenURRole createRemoteReadRole(OpenURApplication application)
	{
		return createRemoteRole(PermissionConstraints.REMOTE_READ, application);
	}
	
	public static OpenURPermission createRemoteCreatePermission(OpenURApplication application)
	{
		return createRemotePermission(PermissionConstraints.REMOTE_CREATE, application);
	}
	
	public static OpenURRole createRemoteCreateRole(OpenURApplication application)
	{
		return createRemoteRole(PermissionConstraints.REMOTE_CREATE, application);
	}
	
	public static OpenURPermission createRemoteUpdatePermission(OpenURApplication application)
	{
		return createRemotePermission(PermissionConstraints.REMOTE_UPDATE, application);
	}
	
	public static OpenURRole createRemoteUpdateRole(OpenURApplication application)
	{
		return createRemoteRole(PermissionConstraints.REMOTE_UPDATE, application);
	}
	
	public static OpenURPermission createRemoteDeletePermission(OpenURApplication application)
	{
		return createRemotePermission(PermissionConstraints.REMOTE_DELETE, application);
	}
	
	public static OpenURRole createRemoteDeleteRole(OpenURApplication application)
	{
		return createRemoteRole(PermissionConstraints.REMOTE_DELETE, application);
	}
	
	public static OpenURPermission createRemoteCheckPermPermission(OpenURApplication application)
	{
		return createRemotePermission(PermissionConstraints.REMOTE_CHECK_PERMISSION, application);
	}
	
	public static OpenURRole createRemoteCheckPermRole(OpenURApplication application)
	{
		return createRemoteRole(PermissionConstraints.REMOTE_CHECK_PERMISSION, application);
	}
	
	public static OpenURPermission createRemoteCheckAuthenticationPermission(OpenURApplication application)
	{
		return createRemotePermission(PermissionConstraints.REMOTE_CHECK_AUTHENTICATION, application);
	}
	
	public static OpenURRole createRemoteCheckCheckAuthenticationRole(OpenURApplication application)
	{
		return createRemoteRole(PermissionConstraints.REMOTE_CHECK_AUTHENTICATION, application);
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
