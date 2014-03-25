package org.openur.module.domain.userstructure.orgunit.abstr;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.user.person.IPerson;

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
  private final Set<IOrgUnitMember> members;
  
	// constructor:
	protected AbstractOrgUnit(AbstractOrgUnitBuilder<? extends AbstractOrgUnitBuilder<?>> b)
	{
		super(b);
		this.superOuId = b.getSuperOuId();
		this.members = Collections.unmodifiableSet(b.getMembers());
	}

	// accessors:
	@Override
	public String getSuperOuId()
	{
		return superOuId;
	}

	@Override
	public Set<IOrgUnitMember> getMembers()
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
	public IOrgUnitMember findMember(String id)
	{
    if (id == null)
    {
      return null;
    }

    for (IOrgUnitMember m : this.getMembers())
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
	public IOrgUnitMember findMember(IPerson person)
	{
		if (person == null)
    {
      return null;
    }

    return findMember(person.getIdentifier());
	}
	
	@Override
	public int compareTo(IOrganizationalUnit ou)
	{    
    int comparison = new CompareToBuilder()
														.append(this.getOrgUnitNumber(), ou.getOrgUnitNumber())
														.append(this.getStatus(), ou.getStatus())
														.toComparison();

    return comparison;
	}
}
