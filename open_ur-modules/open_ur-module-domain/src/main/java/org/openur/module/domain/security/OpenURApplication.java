package org.openur.module.domain.security;

import org.openur.module.domain.IPrincipalUser;
import org.openur.module.domain.IdentifiableEntityImpl;

public class OpenURApplication
	extends IdentifiableEntityImpl
	implements IApplication, IPrincipalUser
{
	private static final long serialVersionUID = -7587130077162770445L;
	
	// properties:
	private final String applicationName;
	private final String userName;
  private final String password;

	// constructor:
	OpenURApplication(OpenURApplicationBuilder b)
	{
		super(b);

		this.applicationName = b.getApplicationName();
		this.userName = b.getUserName();
		this.password = b.getPassword();
	}

	// accessors:
	@Override
	public String getApplicationName()
	{
		return applicationName;
	}

	@Override
	public String getUsername()
	{
		return userName;
	}

	@Override
	public String getPassword()
	{
		return password;
	}

	// operations:
	@Override
	public int compareTo(IApplication o)
	{
		return this.getApplicationName().compareToIgnoreCase(o.getApplicationName());
	}
}
