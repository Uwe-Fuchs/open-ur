package org.openur.remoting.resource.secure_api;

import java.io.Serializable;
import java.security.Principal;

/**
 * Generic implementation of <strong>java.security.Principal</strong> that is
 * used to represent principals authenticated when calling
 * OpenUR-Remote-Services.
 *
 * @author info@uwefuchs.com
 */
public class OpenUrPrincipal
	implements Principal, Serializable
{
	private static final long serialVersionUID = -7574859388611970776L;
	
	/**
	 * The username of the user represented by this Principal.
	 */
	private String name = null;

	public OpenUrPrincipal(final String name)
	{
		this.name = name;
	}

	@Override
	public String getName()
	{
		return name;
	}

	/**
	 * Return a String representation of this object, which exposes only
	 * information that should be public.
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("OpenUrPrincipal[");
		sb.append(this.name);
		sb.append("]");
		return (sb.toString());
	}
}
