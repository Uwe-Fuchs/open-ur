package org.openur.module.domain;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.Validate;

@SuppressWarnings("unchecked")
public abstract class IdentifiableEntityBuilder<T extends IdentifiableEntityBuilder<T>>
{
	// properties:
	private String identifier = null;
	private Date changeDate = null;
	private Date creationDate = null;
	
	// constructors:
	public IdentifiableEntityBuilder()
	{
		super();
		
		this.identifier = createIdentifier();
		this.creationDate = new Date();
	}
	
	public IdentifiableEntityBuilder(String identifier)
	{
		super();

		Validate.notEmpty(identifier, "identifier must not be empty!");
		
		this.identifier = identifier;
		this.creationDate = new Date();
	}
	
	public IdentifiableEntityBuilder(Date creationDate)
	{
		super();
		
		Validate.notNull(creationDate, "creation-date must not be null!");
		
		this.identifier = createIdentifier();
		this.creationDate = creationDate;
	}
	
	public IdentifiableEntityBuilder(String identifier, Date creationDate)
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
	public T creationDate(Date creationDate)
	{
		this.creationDate = creationDate;	
		
		return (T) this;
	}

	public T changeDate(Date changeDate)
	{
		this.changeDate = changeDate;			
		
		return (T) this;
	}
	
	// accessors:
	protected String getIdentifier()
	{
		return identifier;
	}
	
	protected Date getCreationDate()
	{
		return creationDate;
	}	
	
	protected Date getChangeDate()
	{
		return changeDate;
	}	
}
