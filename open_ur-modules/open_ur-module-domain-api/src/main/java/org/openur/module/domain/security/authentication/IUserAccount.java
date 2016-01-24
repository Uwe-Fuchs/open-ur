package org.openur.module.domain.security.authentication;

import org.openur.module.domain.IIdentifiableEntity;

/**
 * Representation of a principal/credentials-combination.
 * 
 * @author info@uwefuchs.com
 */
public interface IUserAccount
	extends IIdentifiableEntity, Comparable<IUserAccount>
{
	
}
