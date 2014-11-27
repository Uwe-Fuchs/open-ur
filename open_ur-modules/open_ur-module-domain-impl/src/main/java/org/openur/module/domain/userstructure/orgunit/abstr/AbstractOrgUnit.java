package org.openur.module.domain.userstructure.orgunit.abstr;

import java.util.Collections;
import java.util.Set;

import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnit;
import org.openur.module.domain.userstructure.person.IPerson;

/**
 * A basic implementation of {@link IOrganizationalUnit}. Extend this to create a suitable domain-object,
 * in case {@link OrganizationalUnit} does't meet your needs.
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
	public Set<? extends AbstractOrgUnitMember> getMembers()
	{
		return members;
	}

	// operations:
	public AbstractOrgUnitMember findMember(String id)
	{
		return (AbstractOrgUnitMember) IOrganizationalUnit.super.findMember(id);
	}

	@Override
	public AbstractOrgUnitMember findMember(IPerson person)
	{
		return (AbstractOrgUnitMember) IOrganizationalUnit.super.findMember(person);
	}	
}
