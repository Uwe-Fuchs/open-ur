package org.openur.module.util.exception;

public class UnsupportedException
	extends RuntimeException
{
	private static final long serialVersionUID = 8929488577417448422L;

	public UnsupportedException()
	{
	}

	public UnsupportedException(String message)
	{
		super(message);
	}

	public UnsupportedException(Throwable cause)
	{
		super(cause);
	}

	public UnsupportedException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public UnsupportedException(String message, Throwable cause,
		boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
