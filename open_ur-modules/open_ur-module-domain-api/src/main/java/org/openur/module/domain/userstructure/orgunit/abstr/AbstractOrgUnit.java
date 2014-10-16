package org.openur.module.domain.userstructure.orgunit.abstr;

import java.util.Collections;
import java.util.Set;

import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember;
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
  private final String superOuId;
  private final Set<OrgUnitMember> members;
  private final AbstractOrgUnit rootOrgUnit;
  
	// constructor:
	protected AbstractOrgUnit(AbstractOrgUnitBuilder<? extends AbstractOrgUnitBuilder<?>> b)
	{
		super(b);
		this.superOuId = b.getSuperOuId();
		this.rootOrgUnit = b.getRootOrgUnit();
		this.members = Collections.unmodifiableSet(b.getMembers());
	}

	// accessors:
	@Override
	public String getSuperOuId()
	{
		return superOuId;
	}

	@Override
	public boolean isRootOrgUnit()
	{
		return (getRootOrgUnit() == null);
	}

	@Override
	public AbstractOrgUnit getRootOrgUnit()
	{
		return rootOrgUnit;
	}

	@Override
	public Set<OrgUnitMember> getMembers()
	{
		return members;
	}

	// operations:
	@Override
	public String getOrgUnitNumber()
	{
		return super.getNumber();
	}	

	@Override
	public OrgUnitMember findMember(String id)
	{
    if (id == null)
    {
      return null;
    }

    for (OrgUnitMember m : this.getMembers())
    {
      if (id.equals(m.getPerson().getIdentifier()))
      {
        return m;
      }
    }

    return null;
	}

	@Override
	public boolean isMember(String id)
	{
    if (id == null)
    {
      return false;
    }

    return (this.findMember(id) != null);
	}

	@Override
	public boolean isMember(IPerson person)
	{
		if (person == null)
    {
      return false;
    }

    return (this.findMember(person) != null);
	}

	@Override
	public OrgUnitMember findMember(IPerson person)
	{
		if (person == null)
    {
      return null;
    }

    return findMember(person.getIdentifier());
	}
}