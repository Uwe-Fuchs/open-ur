package org.openur.module.domain.userstructure.orgunit.abstr;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.userstructure.InconsistentHierarchyException;
import org.openur.module.domain.userstructure.UserStructureBaseBuilder;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember;
import org.openur.module.util.exception.OpenURRuntimeException;

public abstract class AbstractOrgUnitBuilder<T extends AbstractOrgUnitBuilder<T>>
	extends UserStructureBaseBuilder<T>
{
	// properties:
	private AbstractOrgUnit superOrgUnit = null;
	private AbstractOrgUnit rootOrgUnit = null;
	protected Set<OrgUnitMember> members = new HashSet<>();

	// constructors:
	public AbstractOrgUnitBuilder()
	{
		super();
	}

	public AbstractOrgUnitBuilder(String identifier)
	{
		super(identifier);
	}
	
	public AbstractOrgUnitBuilder(AbstractOrgUnit rootOrgUnit)
	{
		super();
		
		Validate.notNull(rootOrgUnit, "root-org-unit must not be null!");
		this.rootOrgUnit = rootOrgUnit;
	}

	public AbstractOrgUnitBuilder(String identifier, AbstractOrgUnit rootOrgUnit)
	{
		super(identifier);
		
		Validate.notNull(rootOrgUnit, "root-org-unit must not be null!");
		this.rootOrgUnit = rootOrgUnit;
	}

	// builder-methods:
	@SuppressWarnings("unchecked")
	public T superOrgUnit(AbstractOrgUnit superOrgUnit)
	{
		if (this.getRootOrgUnit() == null)
		{
			throw new InconsistentHierarchyException(
				"Org-Unit is marked as root, hence no super-org-unit can be set!");
		}
		
		Validate.notNull(superOrgUnit, "super-org-unit must not be null!");
		this.superOrgUnit = superOrgUnit;
		
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T members(Collection<OrgUnitMember> members)
	{
		Validate.notEmpty(members, "members-list must not be empty!");
		
		for (OrgUnitMember m : members)
		{
			if (!(this.getIdentifier().equals(m.getOrgUnitId())))
			{
				throw new OpenURRuntimeException("OrgUnit-ID's must be equal!");
			}
		}

		this.members.addAll(members);
		
		return (T) this;
	}
	
	protected AbstractOrgUnit build()
	{
		if (getRootOrgUnit() != null && getSuperOrgUnit() == null)
		{
			throw new InconsistentHierarchyException(
				"Org-Unit is not root, hence a super-org-unit must be set!");
		}
		
		return null;
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

	Set<OrgUnitMember> getMembers()
	{
		return members;
	}
}
