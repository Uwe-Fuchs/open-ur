package org.openur.module.domain.userstructure.person.abstr;

import java.util.HashSet;
import java.util.Set;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.userstructure.UserStructureBaseBuilder;

public abstract class AbstractPersonBuilder<T extends AbstractPersonBuilder<T>>
	extends UserStructureBaseBuilder<T>
{
	// properties:
	private Set<OpenURApplication> apps = new HashSet<OpenURApplication>();
	
  // constructors:
  protected AbstractPersonBuilder()
	{
		super();
	}
	
  protected AbstractPersonBuilder(String identifier)
	{
		super(identifier);
	}

	// builder-methods:
	@SuppressWarnings("unchecked")
	public T apps(Set<OpenURApplication> apps)
	{
		this.apps = apps;			
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T addApp(OpenURApplication app)
	{
		this.getApps().add(app);
		return (T) this;
	}

	// accessors:
	Set<OpenURApplication> getApps()
	{
		return apps;
	}
}
