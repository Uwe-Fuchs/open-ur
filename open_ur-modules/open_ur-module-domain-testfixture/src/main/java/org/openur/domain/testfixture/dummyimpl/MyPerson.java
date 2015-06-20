package org.openur.domain.testfixture.dummyimpl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
	private Set<MyApplicationImpl> applications = new HashSet<>();

	public MyPerson(String identifier, String employeeNumber)
	{
		super();
		
		this.identifier = identifier;
		this.number = employeeNumber;
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
	public Set<MyApplicationImpl> getApplications()
	{
		return this.applications;
	}
	
	public void addApplication(MyApplicationImpl app)
	{
		this.getApplications().add(app);
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
}