package org.openur.module.domain.security.authentication;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * A wrapper around a {@link UsernamePasswordToken}-object.
 * 
 * @author info@uwefuchs.com
 */
public interface IUsernamePasswordToken
	extends IAuthenticationToken<UsernamePasswordToken>
{
	/**
	 * @return the username as String.
	 */
	String getUserName();
	
	/**
	 * @return the password as String.
	 */
	String getPassword();
}
