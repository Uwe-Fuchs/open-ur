package org.openur.remoting.client.ws.rs.secure_api;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

/**
 * This filter adds username and password to a client-request.
 * 
 * @author info@uwefuchs.com
 * */
public class SecurityClientFilter_UsernamePw
	implements ClientRequestFilter
{
	private final String user;
	private final String password;
	
	public SecurityClientFilter_UsernamePw(String user, String password)
	{
		super();
		
		this.user = user;
		this.password = password;
	}

	@Override
	public void filter(ClientRequestContext requestContext)
		throws IOException
	{
		System.out.print("Hallo");
	}
}
