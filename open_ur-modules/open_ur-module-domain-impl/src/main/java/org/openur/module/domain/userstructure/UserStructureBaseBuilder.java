package org.openur.module.domain.userstructure;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.IdentifiableEntityBuilder;
import org.openur.module.domain.util.DefaultsUtil;

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
		
		Validate.notEmpty(number, "number must not be empty!");
		this.number = number;
	}
	
	// builder-methods:
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
}
