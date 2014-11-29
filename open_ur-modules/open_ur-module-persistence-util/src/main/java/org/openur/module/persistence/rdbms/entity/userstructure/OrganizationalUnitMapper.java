package org.openur.module.persistence.rdbms.entity.userstructure;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember.OrgUnitMemberBuilder;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnitBuilder;
import org.openur.module.domain.userstructure.person.Person;

public class OrganizationalUnitMapper
{
	public static POrganizationalUnit mapFromImmutable(
		OrganizationalUnit immutable, POrganizationalUnit rootOu,	POrganizationalUnit superOu)
	{
		POrganizationalUnit persistable = new POrganizationalUnit();

		persistable.setOrgUnitNumber(immutable.getOrgUnitNumber());
		persistable.setStatus(immutable.getStatus());
		persistable.setRootOu(rootOu);
		persistable.setSuperOu(superOu);
		persistable.setName(immutable.getName());
		persistable.setShortName(immutable.getShortName());
		persistable.setDescription(immutable.getDescription());
		persistable.setAddress(immutable.getAddress() != null ? AddressMapper
			.mapFromImmutable(immutable.getAddress()) : null);
		persistable.setEmailAddress(immutable.getEmailAddress() != null ? immutable
			.getEmailAddress().getAsPlainEMailAddress() : null);

		for (OrgUnitMember member : immutable.getMembers())
		{
			persistable.getMembers().add(new POrgUnitMember(persistable, PersonMapper.mapFromImmutable(member.getPerson())));
		}

		return persistable;
	}
	
	public static POrganizationalUnit mapRootOuFromImmutable(OrganizationalUnit rootOu)
	{
		return OrganizationalUnitMapper.mapFromImmutable(rootOu, null, null);
	}
	
	public static POrganizationalUnit mapSuperOuFromImmutable(OrganizationalUnit superOu, POrganizationalUnit pRootOu)
	{
		return OrganizationalUnitMapper.mapFromImmutable(superOu, pRootOu, null);
	}

	public static OrganizationalUnit mapFromEntity(
		POrganizationalUnit persistable, OrganizationalUnit rootOu,	OrganizationalUnit superOu)
	{
		final String IDENTIFIER = StringUtils.isNotEmpty(persistable.getIdentifier()) ? persistable.getIdentifier() : UUID.randomUUID().toString();

		OrganizationalUnitBuilder immutableBuilder = new OrganizationalUnitBuilder(persistable.getOrgUnitNumber(), persistable.getName());

		if (rootOu != null)
		{
			immutableBuilder.rootOrgUnit(rootOu);
			
			if (superOu == null)
			{
				immutableBuilder.superOrgUnit(rootOu);
			}
		}

		if (superOu != null)
		{
			immutableBuilder.superOrgUnit(superOu);
		}

		immutableBuilder
			.identifier(IDENTIFIER)
			.status(persistable.getStatus())
			.shortName(persistable.getShortName())
			.description(persistable.getDescription())
			.address(persistable.getAddress() != null ? AddressMapper.mapFromEntity(persistable.getAddress()) : null)
			.emailAddress(StringUtils.isNotEmpty(persistable.getEmailAddress()) ? EMailAddress.create(persistable.getEmailAddress()) : null)
			.creationDate(persistable.getCreationDate())
			.lastModifiedDate(persistable.getLastModifiedDate());

		for (POrgUnitMember pMember : persistable.getMembers())
		{
			Person pPerson = PersonMapper.mapFromEntity(pMember.getPerson());
			immutableBuilder.addMember(new OrgUnitMemberBuilder(pPerson, IDENTIFIER).build());
		}

		return immutableBuilder.build();
	}
	
	public static OrganizationalUnit mapRootOuFromEntity(POrganizationalUnit pRootOu)
	{
		return OrganizationalUnitMapper.mapFromEntity(pRootOu, null, null);
	}
	
	public static OrganizationalUnit mapSuperOuFromEntity(POrganizationalUnit pSuperOu, OrganizationalUnit rootOu)
	{
		return OrganizationalUnitMapper.mapFromEntity(pSuperOu, rootOu, null);
	}
}