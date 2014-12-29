package org.openur.module.domain.userstructure.orgunit;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.UserStructureBase;

/**
 * Implementation of {@link IOrganizationalUnit}. All userstructure-related concerns are implemented. Anyway the
 * security-related concerns are still missing, so the final (and concrete) implementation is found under the 
 * {@link org.openur.module.domain.security.authorization}-package.
 * 
 * @author info@uwefuchs.com
 */
public abstract class AbstractOrgUnit
	extends UserStructureBase
	implements IOrganizationalUnit
{
	private static final long serialVersionUID = 2892816957009550354L;
	
	// properties:
  private final Set<? extends AbstractOrgUnitMember> members;
  private final AbstractOrgUnit rootOrgUnit;
  private final AbstractOrgUnit superOrgUnit;
  private final String name;
  private final String shortName;
  private final String description;
	private final Address address;
	private final EMailAddress emailAddress;
	
	public AbstractOrgUnit(	AbstractOrgUnitBuilder<? extends AbstractOrgUnitBuilder<?>> b)
	{
		super(b);

		this.superOrgUnit = b.getSuperOrgUnit();
		this.rootOrgUnit = b.getRootOrgUnit();
		this.members = Collections.unmodifiableSet(b.getMembers());
		this.name = b.getName();
		this.shortName = b.getShortName();
		this.description = b.getDescription();
		this.address = b.getAddress();
		this.emailAddress = b.getEmailAddress();
	}

	// accessors:
	@Override
	public AbstractOrgUnit getSuperOrgUnit()
	{
		return (AbstractOrgUnit) this.superOrgUnit;
	}

	@Override
	public AbstractOrgUnit getRootOrgUnit()
	{
		return (AbstractOrgUnit) this.rootOrgUnit;
	}

	@Override
	public Set<? extends AbstractOrgUnitMember> getMembers()
	{
		return this.members;
	}

	public String getName()
	{
		return name;
	}

	public String getShortName()
	{
		return shortName;
	}

	public String getDescription()
	{
		return description;
	}
	
	public Address getAddress()
	{
		return address;
	}

	public EMailAddress getEmailAddress()
	{
		return emailAddress;
	}

	// operations:
	@Override
	public int compareTo(IOrganizationalUnit ou)
	{    
		if (!(ou instanceof AbstractOrgUnit))
		{
			return IOrganizationalUnit.super.compareTo(ou);
		}
		
		AbstractOrgUnit orgUnit = (AbstractOrgUnit) ou;
		
    int comparison = new CompareToBuilder()
														.append(this.getName(), orgUnit.getName())
														.append(this.getShortName(), orgUnit.getShortName())
														.toComparison();

    if (comparison != 0)
    {
    	return comparison;      
    }
    
    return IOrganizationalUnit.super.compareTo(ou);
	}
}
