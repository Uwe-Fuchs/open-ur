package org.openur.module.domain.userstructure.orgunit;

import java.util.Collection;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnitBuilder;

public class OrganizationalUnitBuilder
	extends AbstractOrgUnitBuilder<OrganizationalUnitBuilder>
{
	// properties:
	private String name = null;
	private String shortName = null;
	private String description = null;
	private Address address = null;
	private EMailAddress emailAddress = null;

	// constructor:
	public OrganizationalUnitBuilder(String orgUnitNumber, String name)
	{
		super(orgUnitNumber);
		
		Validate.notEmpty(name, "name must not be empty!");
		this.name = name;
	}

	// builder-methods:
	public OrganizationalUnitBuilder shortName(String shortName)
	{
		this.shortName = shortName;
		return this;
	}

	public OrganizationalUnitBuilder description(String description)
	{
		this.description = description;
		return this;
	}

	public OrganizationalUnitBuilder address(Address address)
	{
		this.address = address;
		return this;
	}

	public OrganizationalUnitBuilder emailAddress(EMailAddress emailAddress)
	{
		this.emailAddress = emailAddress;
		return this;
	}
	
	public OrganizationalUnitBuilder superOrgUnit(OrganizationalUnit superOrgUnit)
	{
		super.superOrgUnit(superOrgUnit);
		return this;
	}

	public OrganizationalUnitBuilder rootOrgUnit(OrganizationalUnit rootOrgUnit)
	{
		super.rootOrgUnit(rootOrgUnit);
		return this;
	}

	public OrganizationalUnitBuilder orgUnitMembers(Collection<OrgUnitMember> orgUnitMembers)
	{
		super.members(orgUnitMembers);
		return this;
	}

	public OrganizationalUnitBuilder addMember(OrgUnitMember member)
	{
		super.addMember(member);
		return this;
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

	// builder:
	@Override
	public OrganizationalUnit build()
	{
		super.build();
		
		return new OrganizationalUnit(this);
	}
}
