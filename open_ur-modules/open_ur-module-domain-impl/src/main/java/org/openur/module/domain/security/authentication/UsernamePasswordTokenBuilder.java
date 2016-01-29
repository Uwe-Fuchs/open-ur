package org.openur.module.domain.security.authentication;

import org.apache.commons.lang3.Validate;

public class UsernamePasswordTokenBuilder
	implements IUsernamePasswordTokenBuilder
{
	// properties:
	private org.apache.shiro.authc.UsernamePasswordToken delegate = null;
	
	// constructors:
	public UsernamePasswordTokenBuilder()
	{
		super();
		
		delegate = new org.apache.shiro.authc.UsernamePasswordToken();
	}
	
	public UsernamePasswordTokenBuilder(org.apache.shiro.authc.UsernamePasswordToken usernamePasswordToken)
	{
		super();
		
		Validate.notBlank(usernamePasswordToken.getUsername(), "user-name must not be empty!");
		Validate.notNull(usernamePasswordToken.getPassword(), "password must not be empty!");
		
		delegate = usernamePasswordToken;
	}
	
	// builder-methods:
	public UsernamePasswordTokenBuilder userName(String userName)
	{
		Validate.notBlank(userName, "user-name must not be empty!");
		this.delegate.setUsername(userName);
		
		return this;
	}

	public UsernamePasswordTokenBuilder passWord(String passWord)
	{
		Validate.notBlank(passWord, "password must not be empty!");
		this.delegate.setPassword(passWord.toCharArray());
		
		return this;
	}
	
	public UsernamePasswordTokenBuilder rememberMe(boolean rememberMe)
	{
		this.delegate.setRememberMe(rememberMe);
		
		return this;
	}
	
	public UsernamePasswordTokenBuilder host(String host)
	{
		Validate.notBlank(host, "host must not be empty!");
		this.delegate.setHost(host);
		
		return this;
	}

	// builder:
	@Override
	public UsernamePasswordToken build()
	{
		Validate.notBlank(this.delegate.getUsername(), "user-name must not be empty!");
		Validate.notNull(this.delegate.getPassword(), "password must not be empty!");
		
		return new UsernamePasswordToken(delegate);
	}
}
