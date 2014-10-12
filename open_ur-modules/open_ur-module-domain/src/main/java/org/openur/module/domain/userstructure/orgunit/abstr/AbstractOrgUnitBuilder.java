package org.openur.module.domain.userstructure.orgunit.abstr;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.openur.module.domain.userstructure.InconsistentHierarchyException;
import org.openur.module.domain.userstructure.UserStructureBaseBuilder;
import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;
import org.openur.module.util.exception.OpenURRuntimeException;

public abstract class AbstractOrgUnitBuilder<T extends AbstractOrgUnitBuilder<T>>
	extends UserStructureBaseBuilder<T>
{
	// properties:
	private String superOuId = null;
	private AbstractOrgUnit rootOrgUnit = null;
	protected Set<IOrgUnitMember> members = new HashSet<IOrgUnitMember>();

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
	public T superOuId(String superOuId)
	{
		if (this.getRootOrgUnit() == null)
		{
			throw new InconsistentHierarchyException(
				"Org-Unit is marked as root, hence no super-org-unit can be set!");
		}
		
		Validate.notEmpty(superOuId, "super-org-unit-id must not be empty!");
		this.superOuId = superOuId;
		
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public T members(Collection<? extends IOrgUnitMember> members)
	{
		if (members != null)
		{
			for (IOrgUnitMember m : members)
			{
				if (!(this.getIdentifier().equals(m.getOrgUnitId())))
				{
					throw new OpenURRuntimeException("OrgUnit-ID's must be equal!");
				}
			}

			this.members.addAll(members);			
		}
		
		return (T) this;
	}
	
	protected AbstractOrgUnit build()
	{
		if (getRootOrgUnit() != null && StringUtils.isEmpty(getSuperOuId()))
		{
			throw new InconsistentHierarchyException(
				"Org-Unit is not root, hence a super-org-unit must be set!");
		}
		
		return null;
	}

	// accessors:
	String getSuperOuId()
	{
		return superOuId;
	}

	AbstractOrgUnit getRootOrgUnit()
	{
		return rootOrgUnit;
	}

	Set<? extends IOrgUnitMember> getMembers()
	{
		return members;
	}
}
