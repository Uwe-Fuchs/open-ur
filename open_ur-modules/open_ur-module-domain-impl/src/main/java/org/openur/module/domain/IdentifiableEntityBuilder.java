package org.openur.module.domain;

import java.time.LocalDateTime;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.util.DefaultsUtil;

@SuppressWarnings("unchecked")
public abstract class IdentifiableEntityBuilder<T extends IdentifiableEntityBuilder<T>>
{
	// properties:
	private String identifier = DefaultsUtil.getRandomIdentifierByDefaultMechanism();
	private LocalDateTime creationDate = null;
	private LocalDateTime lastModifiedDate = null;
	
	// constructor:
	protected IdentifiableEntityBuilder()
	{
		super();
	}
	
	// builder-methods:
	public T identifier(String identifier)
	{
		Validate.notEmpty(identifier, "identifier must not be empty!");
		
		this.identifier = identifier;	
		
		return (T) this;
	}
	
	public T creationDate(LocalDateTime creationDate)
	{
		this.creationDate = creationDate;	
		
		return (T) this;
	}

	public T lastModifiedDate(LocalDateTime changeDate)
	{
		this.lastModifiedDate = changeDate;			
		
		return (T) this;
	}
	
	// accessors:
	protected String getIdentifier()
	{
		return identifier;
	}
	
	protected LocalDateTime getCreationDate()
	{
		return creationDate;
	}	
	
	protected LocalDateTime getLastModifiedDate()
	{
		return lastModifiedDate;
	}
}
