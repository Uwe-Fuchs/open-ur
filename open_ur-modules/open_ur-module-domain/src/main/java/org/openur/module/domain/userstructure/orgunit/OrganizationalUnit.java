package org.openur.module.domain.userstructure.orgunit;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnit;

public class OrganizationalUnit
	extends AbstractOrgUnit
{
	private static final long serialVersionUID = -1856092517371886423L;
	
	// properties:
  private final String name;
  private final String shortName;
  private final String description;
	private final Address address;
	private final EMailAddress emailAdress;

	// constructor:
	protected OrganizationalUnit(OrganizationalUnitBuilder b)
	{
		super(b);
		this.name = b.getName();
		this.shortName = b.getShortName();
		this.description = b.getDescription();
		this.address = b.getAddress();
		this.emailAdress = b.getEmailAdress();
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

	public EMailAddress getEmailAdress()
	{
		return emailAdress;
	}

	// operations:
	@Override
	public int compareTo(IOrganizationalUnit ou)
	{    
		if (!(ou instanceof OrganizationalUnit))
		{
			return super.compareTo(ou);
		}
		
		OrganizationalUnit orgUnit = (OrganizationalUnit) ou;
		
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
