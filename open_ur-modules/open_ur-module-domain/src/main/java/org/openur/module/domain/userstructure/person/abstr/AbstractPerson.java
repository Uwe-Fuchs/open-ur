package org.openur.module.domain.userstructure.person.abstr;

import java.util.Collections;
import java.util.Set;

import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.person.IPerson;

public abstract class AbstractPerson
	extends UserStructureBase
	implements IPerson
{
	private static final long serialVersionUID = -5109626819047294963L;
	
	// properties:
	private final Set<IApplication> applications;

	// constructor:
	protected AbstractPerson(AbstractPersonBuilder<? extends AbstractPersonBuilder<?>> b)
	{
		super(b);
		
		this.applications = Collections.unmodifiableSet(b.getApps());
	}

	// accessors:
	@Override
	public Set<IApplication> getApplications()
	{
		return applications;
	}

	// operations:
	@Override
	public String getPersonNumber()
	{
		return getNumber();
	}
}
