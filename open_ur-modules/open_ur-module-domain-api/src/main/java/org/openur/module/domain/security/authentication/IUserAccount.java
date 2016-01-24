package org.openur.module.domain.security.authentication;

import org.openur.module.domain.IIdentifiableEntity;

/**
 * Representation of a principal/credentials-combination within the system, in this case username/password.
 * 
 * @author info@uwefuchs.com
 */
public interface IUserAccount
	extends IIdentifiableEntity
{
	/**
	 * the username.
	 * 
	 * @return String
	 */
	String getUserName();
	
	/**
	 * the password.
	 * 
	 * @return String
	 */
	String getPassWord();
	
	/**
	 * the salt-value in case a salt is set and doesn't come from another, external source.
	 *  
	 * @return String
	 */
	String getSalt();
}
