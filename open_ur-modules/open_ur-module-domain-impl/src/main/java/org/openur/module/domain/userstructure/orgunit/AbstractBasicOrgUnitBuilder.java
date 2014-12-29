package org.openur.module.domain.userstructure.orgunit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.userstructure.InconsistentHierarchyException;
import org.openur.module.domain.userstructure.UserStructureBaseBuilder;
import org.openur.module.util.exception.OpenURRuntimeException;

public abstract class AbstractBasicOrgUnitBuilder<T extends AbstractBasicOrgUnitBuilder<T>>
	extends UserStructureBaseBuilder<T>
{
	// properties:
	private AbstractBasicOrgUnit superOrgUnit = null;
	private AbstractBasicOrgUnit rootOrgUnit = null;
	protected Set<AbstractOrgUnitMember> members = new HashSet<>();

	// constructor:
	protected AbstractBasicOrgUnitBuilder(String orgUnitNumber)
	{
		super(orgUnitNumber);
	}
	
	// builder-methods:
	@SuppressWarnings("unchecked")
	protected T superOrgUnit(AbstractBasicOrgUnit superOrgUnit)
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
	protected T rootOrgUnit(AbstractBasicOrgUnit rootOrgUnit)
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
			if (!(this.getIdentifier().equals(m.getOrgUnitId())))
			{
				throw new OpenURRuntimeException("OrgUnit-ID's must be equal!");
			}
		}

		this.members.addAll(members);
		
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T addMember(AbstractOrgUnitMember member)	
	{
		Validate.notNull(member, "member must not be null!");
		
		if (!(this.getIdentifier().equals(member.getOrgUnitId())))
		{
			throw new OpenURRuntimeException("OrgUnit-ID's must be equal!");
		}
		
		this.members.add(member);
		
		return (T) this;
	}

	// accessors:
	protected AbstractBasicOrgUnit getRootOrgUnit()
	{
		return rootOrgUnit;
	}

	protected AbstractBasicOrgUnit getSuperOrgUnit()
	{
		return superOrgUnit;
	}

	protected Set<? extends AbstractOrgUnitMember> getMembers()
	{
		return members;
	}
	
	// builder:
	protected AbstractBasicOrgUnit build()
	{
		if (getRootOrgUnit() != null && getSuperOrgUnit() == null)
		{
			throw new InconsistentHierarchyException(
				"Org-Unit is not root, hence a super-org-unit must be set!");
		}
		
		return null;
	}
}
