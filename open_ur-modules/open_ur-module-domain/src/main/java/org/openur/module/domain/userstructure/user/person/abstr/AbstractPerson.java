package org.openur.module.domain.userstructure.user.person.abstr;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.security.IApplication;
import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.user.person.IPerson;

public abstract class AbstractPerson
	extends UserStructureBase
	implements IPerson
{
	private static final long serialVersionUID = -5109626819047294963L;
	
	// properties:
	private final Set<IApplication> apps;
	private final String username;
	private final String password;

	// constructor:
	protected AbstractPerson(AbstractPersonBuilder<? extends AbstractPersonBuilder<?>> b)
	{
		super(b);
		this.apps = Collections.unmodifiableSet(b.getApps());
		this.username = b.getUsername();
		this.password = b.getPassword();
	}

	// accessors:
	@Override
	public Set<IApplication> getApps()
	{
		return apps;
	}

	@Override
	public String getUsername()
	{
		return username;
	}

	@Override
	public String getPassword()
	{
		return password;
	}

	// operations:
	@Override
	public String getPersonNumber()
	{
		return getNumber();
	}

	@Override
	public int compareTo(IPerson other)
	{
		int comparison = new CompareToBuilder()
			.append(this.getPersonNumber(), other.getPersonNumber())
			.append(this.getStatus(), other.getStatus()).toComparison();

		return comparison;
	}
}
