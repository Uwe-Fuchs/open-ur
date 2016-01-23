package org.openur.module.domain.security.authentication;

import org.apache.commons.lang3.Validate;

/**
 * A simple username/password authentication token to support the most widely-used authentication mechanism. 
 * 
 * @author info@uwefuchs.com
 */
public class UsernamePasswordToken
	implements IAuthenticationToken<org.apache.shiro.authc.UsernamePasswordToken>
{
	private static final long serialVersionUID = -378932735227894282L;
	
	private final org.apache.shiro.authc.UsernamePasswordToken delegate;

	@Override
	public org.apache.shiro.authc.UsernamePasswordToken getDelegate()
	{
		return delegate;
	}

	public UsernamePasswordToken(final String username, final String password)
	{
		super();
		Validate.notBlank(username, "user-name must not be empty!");
		Validate.notBlank(password, "password must not be empty!");
		delegate = new org.apache.shiro.authc.UsernamePasswordToken(username, password);
	}

	public UsernamePasswordToken(final String username, final String password, final boolean rememberMe)
	{
		this(username, password);
		delegate.setRememberMe(rememberMe);
	}

	public UsernamePasswordToken(final String username, final String password,
    final boolean rememberMe, final String host)
	{
		this(username, password, host);
		delegate.setRememberMe(rememberMe);
	}

	public UsernamePasswordToken(final String username, final String password, final String host)
	{
		this(username, password);
		Validate.notBlank(host, "host must not be empty!");
		delegate.setHost(host);
	}

	public UsernamePasswordToken(org.apache.shiro.authc.UsernamePasswordToken delegate)
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
	
  public String getUsername()
  {
  	return delegate.getUsername();
  }
	
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
