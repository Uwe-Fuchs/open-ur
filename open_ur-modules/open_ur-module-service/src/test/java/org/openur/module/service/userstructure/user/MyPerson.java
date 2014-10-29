package org.openur.module.service.userstructure.user;

import java.time.LocalDateTime;
import java.util.Set;

import org.openur.module.domain.application.IApplication;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.person.IPerson;

/**
 * Simple implementation of {@link IPerson} for test-purposes.
 * 
 * @author info@uwefuchs.de
 */
public class MyPerson
	implements IPerson
{
	private String identifier;
	private String number;

	public MyPerson(String identifier, String number)
	{
		super();
		
		this.identifier = identifier;
		this.number = number;
	}

	@Override
	public String getIdentifier()
	{
		return identifier;
	}

	@Override
	public String getNumber()
	{
		return number;
	}

	@Override
	public String getPersonNumber()
	{
		return getNumber();
	}

	@Override
	public Status getStatus()
	{
		return null;
	}

	@Override
	public LocalDateTime getLastModifiedDate()
	{
		return null;
	}

	@Override
	public LocalDateTime getCreationDate()
	{
		return null;
	}

	@Override
	public Set<? extends IApplication> getApplications()
	{
		return null;
	}
}