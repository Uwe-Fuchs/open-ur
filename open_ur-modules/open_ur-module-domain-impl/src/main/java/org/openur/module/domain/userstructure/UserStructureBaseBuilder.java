package org.openur.module.domain.userstructure;

import org.openur.module.domain.IdentifiableEntityBuilder;

public abstract class UserStructureBaseBuilder<T extends UserStructureBaseBuilder<T>>
	extends IdentifiableEntityBuilder<T>
{
	// properties:
	private String number = null;
	private Status status = Status.ACTIVE;

	// constructors:
	protected UserStructureBaseBuilder(String identifier)
	{
		super(identifier);
	}
	
	protected UserStructureBaseBuilder()
	{
		super();
	}
	
	// builder-methods:
	@SuppressWarnings("unchecked")
	public T status(Status status)
	{
		this.status = status;
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T number(String number)
	{
		this.number = number;			
		return (T) this;
	}
	
	// accessors:
	String getNumber()
	{
		return number;
	}

	Status getStatus()
	{
		return status;
	}
}
