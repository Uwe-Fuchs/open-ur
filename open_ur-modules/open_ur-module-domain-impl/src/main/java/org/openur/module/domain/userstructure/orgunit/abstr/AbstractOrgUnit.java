package org.openur.module.domain.userstructure.orgunit.abstr;

import java.util.Collections;
import java.util.Set;

import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrgUnitSimple;
import org.openur.module.domain.userstructure.person.IPerson;

/**
 * A basic implementation of {@link IOrganizationalUnit}. If this meets your needs,
 * use {@link OrgUnitSimple} as a concrete implementation, otherwise extend this to
 * create a suitable domain-object.
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
	
	@Override
	public String getOrgUnitNumber()
	{
		return super.getNumber();
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
