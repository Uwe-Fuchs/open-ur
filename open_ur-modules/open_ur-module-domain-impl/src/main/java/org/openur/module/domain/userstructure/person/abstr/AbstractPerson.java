package org.openur.module.domain.userstructure.person.abstr;

import java.util.Collections;
import java.util.Set;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.person.Person;

/**
 * A basic implementation of {@link IPerson}. Extend this to create a suitable domain-object,
 * in case {@link Person} does't meet your needs.
 * 
 * @author fuchs
 */
public abstract class AbstractPerson
	extends UserStructureBase
	implements IPerson
{
	private static final long serialVersionUID = -5109626819047294963L;
	
	// properties:
	private final Set<OpenURApplication> applications;

	// constructor:
	protected AbstractPerson(AbstractPersonBuilder<? extends AbstractPersonBuilder<?>> b)
	{
		super(b);
		
		this.applications = Collections.unmodifiableSet(b.getApps());
	}

	// accessors:
	@Override
	public Set<OpenURApplication> getApplications()
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
