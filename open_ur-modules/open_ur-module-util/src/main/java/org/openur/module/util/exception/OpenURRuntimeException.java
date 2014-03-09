package org.openur.module.util.exception;

public class OpenURRuntimeException
	extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public OpenURRuntimeException()
	{
	}

	public OpenURRuntimeException(String message)
	{
		super(message);
	}

	public OpenURRuntimeException(Throwable cause)
	{
		super(cause);
	}

	public OpenURRuntimeException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public OpenURRuntimeException(
		String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
