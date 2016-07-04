package org.openur.module.util.exception;

public class EntityNotFoundException
	extends OpenURException
{
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException()
	{
		super();
	}

	public EntityNotFoundException(String message)
	{
		super(message);
	}

	public EntityNotFoundException(Throwable cause)
	{
		super(cause);
	}

	public EntityNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public EntityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
