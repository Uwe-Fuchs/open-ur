package org.openur.module.domain.userstructure;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.IdentifiableEntityBuilder;
import org.openur.module.util.data.Status;
import org.openur.module.util.processing.DefaultsUtil;

public abstract class UserStructureBaseBuilder<T extends UserStructureBaseBuilder<T>>
	extends IdentifiableEntityBuilder<T>
{
	// properties:
	private String number = null;
	private Status status = DefaultsUtil.getDefaultStatus();

	// constructors:
	protected UserStructureBaseBuilder(String number)
	{
		super();

		this.number = number;
	}
	
	public UserStructureBaseBuilder()
	{
		super();
	}

	// builder-methods:
	@SuppressWarnings("unchecked")
	public T number(String number)
	{
		this.number = number;
		
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T status(Status status)
	{
		Validate.notNull(status, "status must not be null!");
		this.status = status;
		
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
	
	// builder:
	protected UserStructureBase build()
	{
		Validate.notEmpty(getNumber(), "number must not be empty!");
		
		return null;
	}
}
