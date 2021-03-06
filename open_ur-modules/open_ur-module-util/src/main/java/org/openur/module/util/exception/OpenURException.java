package org.openur.module.util.exception;


public class OpenURException
	extends Exception
{
	private static final long serialVersionUID = 1L;

	public OpenURException()
	{
		super();
	}

	public OpenURException(String message)
	{
		super(message);
	}

	public OpenURException(Throwable cause)
	{
		super(cause);
	}

	public OpenURException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public OpenURException(
		String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
