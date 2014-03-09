package org.openur.module.domain.userstructure.user.person.abstr;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.userstructure.UserStructureBaseBuilder;

public abstract class AbstractPersonSimpleBuilder<T extends AbstractPersonSimpleBuilder<T>>
	extends UserStructureBaseBuilder<T>
{
	// properties:
	private Set<IApplication> apps = new HashSet<IApplication>();
  private String username = null;
  private String password = null;
	
  // constructors:
	public AbstractPersonSimpleBuilder(String username, String password)
	{
		super();

		init(username, password);
	}
	
	public AbstractPersonSimpleBuilder(String identifier, String username, String password)
	{
		super(identifier);

		init(username, password);
	}
	
	private void init(String username, String password)
	{
		Validate.notEmpty(username, "username must not be empty!");
		Validate.notEmpty(password, "password must not be empty!");
		this.username = username;
		this.password = password;
	}

	// builder-methods:
	@SuppressWarnings("unchecked")
	public T apps(Set<IApplication> apps)
	{
		this.apps = apps;			
		return (T) this;
	}

	// accessors:
	Set<IApplication> getApps()
	{
		return apps;
	}
	
	String getUsername()
	{
		return username;
	}

	String getPassword()
	{
		return password;
	}
}
