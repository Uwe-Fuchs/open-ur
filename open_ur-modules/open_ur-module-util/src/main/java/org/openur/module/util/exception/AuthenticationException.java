package org.openur.module.util.exception;

public class AuthenticationException
	extends OpenURSecurityException
{
	private static final long serialVersionUID = -6678694513711861606L;

	public AuthenticationException()
	{
		super();
	}

	public AuthenticationException(String message)
	{
		super(message);
	}

	public AuthenticationException(Throwable cause)
	{
		super(cause);
	}

	public AuthenticationException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
