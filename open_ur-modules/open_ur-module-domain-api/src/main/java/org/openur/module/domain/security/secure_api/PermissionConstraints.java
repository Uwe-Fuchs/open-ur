package org.openur.module.domain.security.secure_api;

/**
 * all possible permission-constraints when securing a service.
 * 
 * @author info@uwefuchs.com
 */
public interface PermissionConstraints
{
	public static final String REMOTE_READ = "remoteRead";
	public static final String REMOTE_CREATE = "remoteCreate";
	public static final String REMOTE_UPDATE = "remoteUpdate";
	public static final String REMOTE_DELETE = "remoteDelete";
	public static final String REMOTE_CHECK_PERMISSION = "remoteCheckPermission";
	public static final String REMOTE_CHECK_AUTHENTICATION = "remoteCheckAuthentication";	
}
