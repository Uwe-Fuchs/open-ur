package org.openur.module.domain.security.authentication;

import java.io.Serializable;

import org.apache.shiro.authc.HostAuthenticationToken;

/**
 * A wrapper around an {@link HostAuthenticationToken} (i.e. a consolidation of an account's principals and supporting
 * credentials submitted by a user during an authentication attempt).
 * 
 * @author info@uwefuchs.com
 *
 * @param <H> the underlying {@link HostAuthenticationToken}-instance.
 */
public interface IAuthenticationToken<H extends HostAuthenticationToken>
	extends Serializable
{
	H getDelegate();
	
	/**
	 * Returns the account identity submitted during the authentication process&nbsp;&nbsp;(e.g. a username).
	 * 
   * @return Object.
	 */
	Object getPrincipal();
	
	/**
	 * Returns the credentials submitted by the user during the authentication process that verifies
   * the submitted {@link #getPrincipal() account identity}&nbsp;&nbsp;(e.g. a password).
	 * 
	 * @return Object.
	 */
	Object getCredentials();
  
	/**
   * Returns the host name of the client from where the
   * authentication attempt originates or if the environment cannot or
   * chooses not to resolve the hostname to improve performance, this method
   * returns the String representation of the client's IP address.
   * <p/>
   * When used in web environments, this value is usually the same as the
   * {@code ServletRequest.getRemoteHost()} value.
	 * 
	 * @return String.
	 */
	String getHost();
	
  /**
   * Returns {@code true} if the submitting user wishes their identity (principal(s)) to be remembered
   * across sessions, {@code false} otherwise.
   *
   * @return boolean.
   */
  boolean isRememberMe();
}
