package org.openur.module.domain.userstructure.person.abstr;

import java.util.HashSet;
import java.util.Set;

import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.userstructure.UserStructureBaseBuilder;

public abstract class AbstractPersonBuilder<T extends AbstractPersonBuilder<T>>
	extends UserStructureBaseBuilder<T>
{
	// properties:
	private Set<IApplication> apps = new HashSet<IApplication>();
	
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
}
