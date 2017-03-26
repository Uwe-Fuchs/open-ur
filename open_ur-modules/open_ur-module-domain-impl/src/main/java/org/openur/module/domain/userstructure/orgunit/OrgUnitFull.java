package org.openur.module.domain.userstructure.orgunit;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.person.IPerson;

/**
 * Implementation of {@link IOrganizationalUnit}. All userstructure-related concerns are implemented.
 * 
 * @author info@uwefuchs.com
 */
public class OrgUnitFull
	extends UserStructureBase
	implements IOrganizationalUnit
{
	private static final long serialVersionUID = 2892816957009550354L;
	
	// properties:
  private final Set<OrgUnitMember> members;
  private final OrgUnitFull rootOrgUnit;
  private final OrgUnitFull superOrgUnit;
  private final String name;
  private final String shortName;
  private final String description;
	private final Address address;
	private final EMailAddress emailAddress;
	
	public OrgUnitFull(	OrgUnitFullBuilder b)
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
	public OrgUnitFull getSuperOrgUnit()
	{
		return (OrgUnitFull) this.superOrgUnit;
	}

	@Override
	public OrgUnitFull getRootOrgUnit()
	{
		return (OrgUnitFull) this.rootOrgUnit;
	}

	@Override
	public Set<OrgUnitMember> getMembers()
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
	public OrgUnitMember findMemberByPersonId(String id)
	{
		return (OrgUnitMember) IOrganizationalUnit.super.findMemberByPersonId(id);
	}

	@Override
	public OrgUnitMember findMemberByPerson(IPerson person)
	{
		return (OrgUnitMember) IOrganizationalUnit.super.findMemberByPerson(person);
	}
	
	@Override
	public int compareTo(IOrganizationalUnit ou)
	{    
		if (!(ou instanceof OrgUnitFull))
		{
			return IOrganizationalUnit.super.compareTo(ou);
		}
		
		OrgUnitFull orgUnit = (OrgUnitFull) ou;
		
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
