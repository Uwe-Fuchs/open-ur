package org.openur.domain.testfixture.dummyimpl;

import java.time.LocalDateTime;

import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;

/**
 * Simple implementation of {@link ITechnicalUser} for test-purposes.
 * 
 * @author info@uwefuchs.de
 */
public class MyTechnicalUser
	implements ITechnicalUser
{
	private String identifier;
	private String number;	
	
	public MyTechnicalUser(String identifier, String number)
	{
		super();
		this.identifier = identifier;
		this.number = number;
	}

	@Override
	public String getIdentifier()
	{
		return this.identifier;
	}
	
	@Override
	public String getNumber()
	{
		return this.number;
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
