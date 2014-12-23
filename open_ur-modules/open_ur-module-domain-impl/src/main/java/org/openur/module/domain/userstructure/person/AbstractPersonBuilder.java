package org.openur.module.domain.userstructure.person;

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
	private Set<OpenURApplication> applications = new HashSet<OpenURApplication>();
	
  protected AbstractPersonBuilder(String personalNumber)
	{
		super(personalNumber);
	}

	// builder-methods:
	@SuppressWarnings("unchecked")
	public T applications(Collection<OpenURApplication> applications)
	{
		Validate.notEmpty(applications, "applications-list must not be empty!");		
		this.applications.addAll(applications);
		
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T addApplication(OpenURApplication application)
	{
		Validate.notNull(application, "application must not be null!");
		this.getApplications().add(application);
		
		return (T) this;
	}

	// accessors:
	Set<OpenURApplication> getApplications()
	{
		return applications;
	}
}
