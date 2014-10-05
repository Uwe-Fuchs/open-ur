package org.openur.module.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import org.apache.commons.lang3.Validate;

@SuppressWarnings("unchecked")
public abstract class IdentifiableEntityBuilder<T extends IdentifiableEntityBuilder<T>>
{
	// properties:
	private String identifier = null;
	private LocalDateTime lastModifiedDate = null;
	private LocalDateTime creationDate = null;
	
	// constructors:
	protected IdentifiableEntityBuilder()
	{
		super();
		
		this.identifier = createIdentifier();
		this.creationDate = LocalDateTime.now(ZoneId.systemDefault());
	}
	
	protected IdentifiableEntityBuilder(String identifier)
	{
		super();

		Validate.notEmpty(identifier, "identifier must not be empty!");
		
		this.identifier = identifier;
		this.creationDate = LocalDateTime.now(ZoneId.systemDefault());
	}
	
	protected IdentifiableEntityBuilder(LocalDateTime creationDate)
	{
		super();
		
		Validate.notNull(creationDate, "creation-date must not be null!");
		
		this.identifier = createIdentifier();
		this.creationDate = creationDate;
	}
	
	protected IdentifiableEntityBuilder(String identifier, LocalDateTime creationDate)
	{
		super();
		
		Validate.notEmpty(identifier, "identifier must nor be empty!");
		Validate.notNull(creationDate, "creation-date must not be null!");
		
		this.identifier = identifier;
		this.creationDate = creationDate;
	}
	
	private String createIdentifier()
	{
		return UUID.randomUUID().toString();
	}
	
	// builder-methods:
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
