package org.openur.module.domain.userstructure.orgunit;

import java.util.Collections;
import java.util.Set;

import org.openur.module.domain.userstructure.UserStructureBase;

/**
 * A basic implementation of {@link IOrganizationalUnit}. Extend this to create your own domain-object.
 * 
 * @author fuchs
 */
public abstract class AbstractBasicOrgUnit
	extends UserStructureBase
	implements IOrganizationalUnit
{
	private static final long serialVersionUID = -613869687329884115L;
	
	// properties:
  private final Set<? extends AbstractOrgUnitMember> members;
  protected final AbstractBasicOrgUnit rootOrgUnit;
	protected final AbstractBasicOrgUnit superOrgUnit;
  
	// constructor:
	protected AbstractBasicOrgUnit(AbstractBasicOrgUnitBuilder<? extends AbstractBasicOrgUnitBuilder<?>> b)
	{
		super(b);
		
		this.superOrgUnit = b.getSuperOrgUnit();
		this.rootOrgUnit = b.getRootOrgUnit();
		this.members = Collections.unmodifiableSet(b.getMembers());
	}

	// accessors:
	@Override
	public AbstractBasicOrgUnit getSuperOrgUnit()
	{
		return (AbstractBasicOrgUnit) this.superOrgUnit;
	}

	@Override
	public AbstractBasicOrgUnit getRootOrgUnit()
	{
		return (AbstractBasicOrgUnit) this.rootOrgUnit;
	}

	@Override
	public Set<? extends AbstractOrgUnitMember> getMembers()
	{
		return this.members;
	}

	// operations:

}
