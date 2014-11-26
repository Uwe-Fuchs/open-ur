package org.openur.module.domain.userstructure.person.abstr;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.userstructure.UserStructureBaseBuilder;

public abstract class AbstractPersonBuilder<T extends AbstractPersonBuilder<T>>
	extends UserStructureBaseBuilder<T>
{
	// properties:
	private Set<OpenURApplication> apps = new HashSet<OpenURApplication>();
	
  protected AbstractPersonBuilder(String number)
	{
		super(number);
	}

	// builder-methods:
	@SuppressWarnings("unchecked")
	public T apps(Collection<OpenURApplication> apps)
	{
		Validate.notEmpty(apps, "apps-list must not be empty!");		
		this.apps.addAll(apps);
		
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T addApp(OpenURApplication app)
	{
		Validate.notNull(app, "app must not be null!");
		this.getApps().add(app);
		
		return (T) this;
	}

	// accessors:
	Set<OpenURApplication> getApps()
	{
		return apps;
	}
}
