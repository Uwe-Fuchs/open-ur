package org.openur.module.domain.userstructure.orgunit;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnitBuilder;

public abstract class AbstractExtendedOrgUnitBuilder<T extends AbstractExtendedOrgUnitBuilder<T>>
	extends AbstractOrgUnitBuilder<T>
{
	// properties:
	private String name = null;
	private String shortName = null;
	private String description = null;
	private Address address = null;
	private EMailAddress emailAddress = null;

	protected AbstractExtendedOrgUnitBuilder(String orgUnitNumber, String name)
	{
		super(orgUnitNumber);
		
		Validate.notEmpty(name, "name must not be empty!");
		this.name = name;
	}

	// builder-methods:
	@SuppressWarnings("unchecked")
	public T shortName(String shortName)
	{
		this.shortName = shortName;		
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T description(String description)
	{
		this.description = description;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T address(Address address)
	{
		this.address = address;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T emailAddress(EMailAddress emailAddress)
	{
		this.emailAddress = emailAddress;
		return (T) this;
	}

	// accessors:
	String getName()
	{
		return name;
	}

	String getShortName()
	{
		return shortName;
	}

	String getDescription()
	{
		return description;
	}

	Address getAddress()
	{
		return address;
	}

	EMailAddress getEmailAddress()
	{
		return emailAddress;
	}
}
