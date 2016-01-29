package org.openur.module.domain.security.authentication;

import org.apache.shiro.authc.HostAuthenticationToken;

/**
 * creates instances with super-type {@link IAuthenticationToken}.
 * 
 * @author info@uwefuchs.com
 *
 * @param <T> which concrete type of IAuthenticationToken should be created?
 */
public interface IAuthenticationTokenBuilder<T extends IAuthenticationToken<? extends HostAuthenticationToken>>
{	
	/**
	 * set isRememberMe
	 * 
	 * @param rememberMe
	 * 
	 * @return this instance
	 */
	IAuthenticationTokenBuilder<T> rememberMe(boolean rememberMe);
	
	/**
	 * set Host.
	 * 
	 * @param host
	 * 
	 * @return this instance
	 */
	IAuthenticationTokenBuilder<T> host(String host);
	
	/**
	 * creates instance of type {@link IAuthenticationToken}.
	 * 
	 * @return {@link IAuthenticationToken}.
	 */
	T build();
}
