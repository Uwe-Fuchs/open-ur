package org.openur.module.domain.userstructure.orgunit;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.EMailAddress;

public abstract class AbstractExtendedOrgUnit
	extends AbstractBasicOrgUnit
{
	private static final long serialVersionUID = 2892816957009550354L;
	
	// properties:
  private final String name;
  private final String shortName;
  private final String description;
	private final Address address;
	private final EMailAddress emailAddress;
	
	public AbstractExtendedOrgUnit(	AbstractExtendedOrgUnitBuilder<? extends AbstractExtendedOrgUnitBuilder<?>> b)
	{
		super(b);
		
		this.name = b.getName();
		this.shortName = b.getShortName();
		this.description = b.getDescription();
		this.address = b.getAddress();
		this.emailAddress = b.getEmailAddress();
	}

	// accessors:
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
		if (!(ou instanceof AbstractExtendedOrgUnit))
		{
			return super.compareTo(ou);
		}
		
		AbstractExtendedOrgUnit orgUnit = (AbstractExtendedOrgUnit) ou;
		
    int comparison = new CompareToBuilder()
														.append(this.getName(), orgUnit.getName())
														.append(this.getShortName(), orgUnit.getShortName())
														.toComparison();

    if (comparison != 0)
    {
    	return comparison;      
    }
    
    return super.compareTo(ou);
	}
}
