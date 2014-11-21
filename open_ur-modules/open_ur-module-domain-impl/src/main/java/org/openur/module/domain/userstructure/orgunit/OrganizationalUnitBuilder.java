package org.openur.module.domain.userstructure.orgunit;

import java.util.Collection;

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

	// constructors:
	public OrganizationalUnitBuilder()
	{
		super();
	}

	public OrganizationalUnitBuilder(String identifier)
	{
		super(identifier);
	}

	public OrganizationalUnitBuilder(OrganizationalUnit rootOrgUnit)
	{
		super(rootOrgUnit);
	}

	public OrganizationalUnitBuilder(String identifier, OrganizationalUnit rootOrgUnit)
	{
		super(identifier, rootOrgUnit);
	}

	// builder-methods:
	public OrganizationalUnitBuilder name(String name)
	{
		this.name = name;
		return this;
	}

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

	// builder:
	@Override
	public OrganizationalUnit build()
	{
		super.build();
		
		return new OrganizationalUnit(this);
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
