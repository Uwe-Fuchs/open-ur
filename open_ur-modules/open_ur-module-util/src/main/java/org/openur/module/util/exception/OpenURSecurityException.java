package org.openur.module.util.exception;


/**
 * Exception in security-contexts (auzhentication, authorization).
 * 
 * @author uwe@uwefuchs.com
 */
public class OpenURSecurityException 
	extends OpenURException
{
	private static final long serialVersionUID = 1892827173004108483L;

	public OpenURSecurityException() 
	{
		super();
	}

	public OpenURSecurityException(String message) 
	{
		super(message);
	}

	public OpenURSecurityException(Throwable cause) 
	{
		super(cause);
	}

	public OpenURSecurityException(String message, Throwable cause) 
	{
		super(message, cause);
	}
}
