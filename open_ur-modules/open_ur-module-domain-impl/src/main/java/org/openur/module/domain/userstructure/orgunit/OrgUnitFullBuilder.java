package org.openur.module.domain.userstructure.orgunit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.InconsistentHierarchyException;
import org.openur.module.domain.userstructure.UserStructureBaseBuilder;
import org.openur.module.util.exception.OpenURRuntimeException;

public class OrgUnitFullBuilder
	extends UserStructureBaseBuilder<OrgUnitFullBuilder>
{
	// properties:
	private OrgUnitFull superOrgUnit = null;
	private OrgUnitFull rootOrgUnit = null;
	private Set<OrgUnitMember> members = new HashSet<>();
	private String name = null;
	private String shortName = null;
	private String description = null;
	private Address address = null;
	private EMailAddress emailAddress = null;

	// constructors:
	public OrgUnitFullBuilder(String orgUnitNumber, String name)
	{
		super(orgUnitNumber);

		this.name = name;
	}

	public OrgUnitFullBuilder()
	{
		super();
	}

	// builder-methods:
	public OrgUnitFullBuilder orgUnitNumber(String orgUnitNumber)
	{
		super.number(orgUnitNumber);
		
		return (OrgUnitFullBuilder) this;
	}

	public OrgUnitFullBuilder name(String name)
	{
		this.name = name;
		
		return (OrgUnitFullBuilder) this;
	}
	
	public OrgUnitFullBuilder superOrgUnit(OrgUnitFull superOrgUnit)
	{
		Validate.notNull(superOrgUnit, "super-org-unit must not be null!");
		
		if (this.getRootOrgUnit() == null)
		{
			throw new InconsistentHierarchyException(
				"Org-Unit is marked as root, hence no super-org-unit can be set!");
		}
		
		this.superOrgUnit = superOrgUnit;
		
		return this;
	}
	
	public OrgUnitFullBuilder rootOrgUnit(OrgUnitFull rootOrgUnit)
	{
		Validate.notNull(rootOrgUnit, "root-orgunit must not be null!");
		
		this.rootOrgUnit = rootOrgUnit;
		
		return this;
	}

	public OrgUnitFullBuilder members(Collection<OrgUnitMember> members)
	{
		Validate.notNull(members, "members-list must not be null!");
		
		for (OrgUnitMember m : members)
		{
			addMember(m);
		}
		
		return this;
	}
	
	public OrgUnitFullBuilder addMember(OrgUnitMember member)	
	{
		Validate.notNull(member, "member must not be null!");
		
		if (!(this.getIdentifier().equals(member.getOrgUnitId())))
		{
			throw new OpenURRuntimeException("OrgUnit-ID's must be equal!");
		}
		
		this.members.add(member);
		
		return this;
	}
	
	public OrgUnitFullBuilder shortName(String shortName)
	{
		this.shortName = shortName;	
		
		return this;
	}

	public OrgUnitFullBuilder description(String description)
	{
		this.description = description;
		
		return this;
	}

	public OrgUnitFullBuilder address(Address address)
	{
		this.address = address;
		
		return this;
	}

	public OrgUnitFullBuilder emailAddress(EMailAddress emailAddress)
	{
		this.emailAddress = emailAddress;
		
		return this;
	}

	// accessors:
	OrgUnitFull getRootOrgUnit()
	{
		return rootOrgUnit;
	}

	OrgUnitFull getSuperOrgUnit()
	{
		return superOrgUnit;
	}

	Set<? extends OrgUnitMember> getMembers()
	{
		return members;
	}
	
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
	public OrgUnitFull build()
	{
		super.build();
		
		Validate.notEmpty(name, "name must not be empty!");
		
		if (getRootOrgUnit() != null && getSuperOrgUnit() == null)
		{
			throw new InconsistentHierarchyException(
				"Org-Unit is not root, hence a super-org-unit must be set!");
		}
		
		return new OrgUnitFull(this);
	}
}
