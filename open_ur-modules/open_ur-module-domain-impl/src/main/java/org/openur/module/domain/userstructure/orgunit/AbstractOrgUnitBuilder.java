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

public abstract class AbstractOrgUnitBuilder<T extends AbstractOrgUnitBuilder<T>>
	extends UserStructureBaseBuilder<T>
{
	// properties:
	private AbstractOrgUnit superOrgUnit = null;
	private AbstractOrgUnit rootOrgUnit = null;
	protected Set<AbstractOrgUnitMember> members = new HashSet<>();
	private String name = null;
	private String shortName = null;
	private String description = null;
	private Address address = null;
	private EMailAddress emailAddress = null;

	// constructors:
	protected AbstractOrgUnitBuilder(String orgUnitNumber, String name)
	{
		super(orgUnitNumber);

		this.name = name;
	}

	public AbstractOrgUnitBuilder()
	{
		super();
	}

	// builder-methods:
	@SuppressWarnings("unchecked")
	public T orgUnitNumber(String orgUnitNumber)
	{
		super.number(orgUnitNumber);
		
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T name(String name)
	{
		this.name = name;
		
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	protected T superOrgUnit(AbstractOrgUnit superOrgUnit)
	{
		Validate.notNull(superOrgUnit, "super-org-unit must not be null!");
		
		if (this.getRootOrgUnit() == null)
		{
			throw new InconsistentHierarchyException(
				"Org-Unit is marked as root, hence no super-org-unit can be set!");
		}
		
		this.superOrgUnit = superOrgUnit;
		
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	protected T rootOrgUnit(AbstractOrgUnit rootOrgUnit)
	{
		Validate.notNull(rootOrgUnit, "root-orgunit must not be null!");
		
		this.rootOrgUnit = rootOrgUnit;
		
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	protected T members(Collection<? extends AbstractOrgUnitMember> members)
	{
		Validate.notNull(members, "members-list must not be null!");
		
		for (AbstractOrgUnitMember m : members)
		{
			addMember(m);
		}
		
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	protected T addMember(AbstractOrgUnitMember member)	
	{
		Validate.notNull(member, "member must not be null!");
		
		if (!(this.getIdentifier().equals(member.getOrgUnitId())))
		{
			throw new OpenURRuntimeException("OrgUnit-ID's must be equal!");
		}
		
		this.members.add(member);
		
		return (T) this;
	}
	
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
	AbstractOrgUnit getRootOrgUnit()
	{
		return rootOrgUnit;
	}

	AbstractOrgUnit getSuperOrgUnit()
	{
		return superOrgUnit;
	}

	Set<? extends AbstractOrgUnitMember> getMembers()
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
	protected AbstractOrgUnit build()
	{
		super.build();
		
		Validate.notEmpty(name, "name must not be empty!");
		
		if (getRootOrgUnit() != null && getSuperOrgUnit() == null)
		{
			throw new InconsistentHierarchyException(
				"Org-Unit is not root, hence a super-org-unit must be set!");
		}
		
		return null;
	}
}
