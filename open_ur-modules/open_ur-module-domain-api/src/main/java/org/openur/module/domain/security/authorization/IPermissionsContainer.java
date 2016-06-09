package org.openur.module.domain.security.authorization;

import java.util.Map;
import java.util.Set;

import org.openur.module.domain.application.IApplication;

/**
 * marks a domain-object as a container for {@link IPermission}s.
 * 
 * @author info@uwefuchs.com
 *
 * @param <P> : the {@link IPermission}-implementaion for this container.
 */
public interface IPermissionsContainer<A extends IApplication, P extends IPermission>
{
	/**
	 * the permissions assigned to this role, for all applications.
	 * 
	 * @return Map<IApplication, Set<IPermission>>
	 */
	Map<A, Set<P>> getAllPermissions();
	
	/**
	 * the permissions assigned to this role for one special application.
	 * 
	 * @param application : the application whose permissions are queried.
	 * 
	 * @return Set<IPermission>
	 */
	default Set<P> getPermissions(IApplication application)
	{
		return getAllPermissions().get(application);
	}
	
	/**
	 * has this role a certain permission?
	 * 
	 * @param application : the application whose permissions are queried.
	 * @param permission : the permission in question.
	 * 
	 * @return this role has the permission.
	 */
	default boolean containsPermission(IApplication application, IPermission permission)
	{
		Set<P> perms = this.getPermissions(application);
		
		return (perms != null && perms.contains(permission));
	}
}
