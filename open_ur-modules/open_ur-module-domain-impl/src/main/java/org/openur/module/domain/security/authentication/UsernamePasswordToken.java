package org.openur.module.domain.security.authentication;

import org.apache.commons.lang3.Validate;

/**
 * A simple username/password authentication token to support the most widely-used authentication mechanism. 
 * 
 * @author info@uwefuchs.com
 */
public class UsernamePasswordToken
	implements IUsernamePasswordToken
{
	private static final long serialVersionUID = -378932735227894282L;
	
	private final org.apache.shiro.authc.UsernamePasswordToken delegate;

	@Override
	public org.apache.shiro.authc.UsernamePasswordToken getDelegate()
	{
		return delegate;
	}

	UsernamePasswordToken(org.apache.shiro.authc.UsernamePasswordToken delegate)
	{
		super();
		Validate.notNull(delegate, "delagate-object must not be null!");
		this.delegate = delegate;
	}

	@Override
	public Object getPrincipal()
	{
		return delegate.getPrincipal();
	}

	@Override
	public Object getCredentials()
	{
		return delegate.getCredentials();
	}

	@Override
	public String getHost()
	{
		return delegate.getHost();
	}
	
	@Override
  public String getUserName()
  {
  	return delegate.getUsername();
  }
	
	@Override
	public String getPassword()
	{
		return delegate.getPassword().toString();
	}

	@Override
	public boolean isRememberMe()
	{
		return delegate.isRememberMe();
	}
}
