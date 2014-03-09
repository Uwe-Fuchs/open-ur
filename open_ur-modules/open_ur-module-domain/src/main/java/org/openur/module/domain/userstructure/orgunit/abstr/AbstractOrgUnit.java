package org.openur.module.domain.userstructure.orgunit.abstr;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.orgunit.IOrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;

/**
 * A basic implementation of {@link IOrganizationalUnit}. If it is sufficient for you,
 * use {@link OrgUnitSimple} as a concrete implementation, otherwise extend this to
 * create a domain-object that meet your needs.
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
	public AbstractOrgUnit(AbstractOrgUnitBuilder<? extends AbstractOrgUnitBuilder<?>> b)
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
      if (id.equals(m.getPersonId()))
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
	public int compareTo(IOrganizationalUnit ou)
	{    
    int comparison = new CompareToBuilder()
														.append(this.getOrgUnitNumber(), ou.getOrgUnitNumber())
														.append(this.getStatus(), ou.getStatus())
														.toComparison();

    return comparison;
	}
}
