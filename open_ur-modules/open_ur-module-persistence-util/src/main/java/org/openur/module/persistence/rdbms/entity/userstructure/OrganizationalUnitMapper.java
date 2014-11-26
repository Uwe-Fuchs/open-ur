package org.openur.module.persistence.rdbms.entity.userstructure;

import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember.OrgUnitMemberBuilder;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnitBuilder;
import org.openur.module.domain.userstructure.person.Person;

public class OrganizationalUnitMapper
{
	public static POrganizationalUnit mapFromImmutable(
		OrganizationalUnit immutable, POrganizationalUnit rootOu,
		POrganizationalUnit superOu)
	{
		POrganizationalUnit persistable = new POrganizationalUnit();

		persistable.setNumber(immutable.getNumber());
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

	public static OrganizationalUnit mapFromEntity(
		POrganizationalUnit persistable, OrganizationalUnit rootOu,
		OrganizationalUnit superOu)
	{
		final String IDENTIFIER = StringUtils.isNotEmpty(persistable.getIdentifier()) ? persistable.getIdentifier() : UUID.randomUUID().toString();

		OrganizationalUnitBuilder immutableBuilder = new OrganizationalUnitBuilder(persistable.getNumber(), persistable.getName());

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

	public static boolean immutableEqualsToEntity(OrganizationalUnit immutable,
		POrganizationalUnit persistable)
	{
		if (!UserStructureBaseMapper.immutableEqualsToEntity(immutable, persistable))
		{
			return false;
		}

		if ((immutable.getAddress() != null || persistable.getAddress() != null)
			&& !AddressMapper.immutableEqualsToEntity(immutable.getAddress(),	persistable.getAddress()))
		{
			return false;
		}

		boolean isEqual = new EqualsBuilder()
			.append(immutable.getName(), persistable.getName())
			.append(immutable.getShortName(), persistable.getShortName())
			.append(immutable.getDescription(), persistable.getDescription())
			.append(immutable.getEmailAddress() != null ? immutable.getEmailAddress()
					.getAsPlainEMailAddress() : null, persistable.getEmailAddress())
			.isEquals();

		if (!isEqual)
		{
			return false;
		}

		for (POrgUnitMember pMember : persistable.getMembers())
		{
			OrgUnitMember member = findMemberInImmutable(pMember,	immutable.getMembers());

			if (member == null
				|| member.getPerson() == null
				|| pMember.getPerson() == null
				|| !PersonMapper.immutableEqualsToEntity(member.getPerson(), pMember.getPerson()))
			{
				return false;
			}
		}

		return true;
	}

	private static OrgUnitMember findMemberInImmutable(POrgUnitMember pMember, Set<OrgUnitMember> members)
	{
		for (OrgUnitMember member : members)
		{
			if (PersonMapper.immutableEqualsToEntity(member.getPerson(), pMember.getPerson()))
			{
				return member;
			}
		}

		return null;
	}
}
