package org.openur.module.domain.userstructure.orgunit.abstr;

import java.util.Collections;
import java.util.Set;

import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;

/**
 * A basic implementation of {@link IOrganizationalUnit}. Extend this to create your own domain-object.
 * 
 * @author fuchs
 */
public abstract class AbstractOrgUnit
	extends UserStructureBase
	implements IOrganizationalUnit
{
	private static final long serialVersionUID = -613869687329884115L;
	
	// properties:
  private final Set<? extends AbstractOrgUnitMember> members;
  protected final AbstractOrgUnit rootOrgUnit;
	protected final AbstractOrgUnit superOrgUnit;
  
	// constructor:
	protected AbstractOrgUnit(AbstractOrgUnitBuilder<? extends AbstractOrgUnitBuilder<?>> b)
	{
		super(b);
		
		this.superOrgUnit = b.getSuperOrgUnit();
		this.rootOrgUnit = b.getRootOrgUnit();
		this.members = Collections.unmodifiableSet(b.getMembers());
	}

	// accessors:
	@Override
	@SuppressWarnings("unchecked")
	public <OU extends IOrganizationalUnit> OU getSuperOrgUnit()
	{
		return (OU) this.superOrgUnit;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <OU extends IOrganizationalUnit> OU getRootOrgUnit()
	{
		return (OU) this.rootOrgUnit;
	}

	@Override
	public Set<? extends IOrgUnitMember> getMembers()
	{
		return this.members;
	}

	// operations:

}
