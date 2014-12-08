package org.openur.module.persistence.rdbms.entity;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.AuthorizableMember.AuthorizableMemberBuilder;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit.AuthorizableOrgUnitBuilder;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.person.Person;

public class OrganizationalUnitMapper
{
	public static POrganizationalUnit mapFromImmutable(
		AuthorizableOrgUnit immutable, POrganizationalUnit rootOu,	POrganizationalUnit superOu)
	{
		POrganizationalUnit persistable = new POrganizationalUnit(immutable.getOrgUnitNumber(), immutable.getName());

		persistable.setStatus(immutable.getStatus());
		persistable.setRootOu(rootOu);
		persistable.setSuperOu(superOu);
		persistable.setShortName(immutable.getShortName());
		persistable.setDescription(immutable.getDescription());
		persistable.setAddress(immutable.getAddress() != null ? AddressMapper
			.mapFromImmutable(immutable.getAddress()) : null);
		persistable.setEmailAddress(immutable.getEmailAddress() != null ? immutable
			.getEmailAddress().getAsPlainEMailAddress() : null);
		
		immutable.getMembers()
			.stream()
			.map(pMember -> OrgUnitMemberMapper.mapFromImmutable(pMember, persistable))
			.forEach(persistable::addMember);

		return persistable;
	}
	
	public static POrganizationalUnit mapRootOuFromImmutable(AuthorizableOrgUnit rootOu)
	{
		return OrganizationalUnitMapper.mapFromImmutable(rootOu, null, null);
	}
	
	public static POrganizationalUnit mapSuperOuFromImmutable(AuthorizableOrgUnit superOu, POrganizationalUnit pRootOu)
	{
		return OrganizationalUnitMapper.mapFromImmutable(superOu, pRootOu, pRootOu);
	}

	public static AuthorizableOrgUnit mapFromEntity(
		POrganizationalUnit persistable, AuthorizableOrgUnit rootOu,	AuthorizableOrgUnit superOu)
	{
		final String IDENTIFIER = StringUtils.isNotEmpty(persistable.getIdentifier()) ? persistable.getIdentifier() : UUID.randomUUID().toString();

		AuthorizableOrgUnitBuilder immutableBuilder = new AuthorizableOrgUnitBuilder(persistable.getOrgUnitNumber(), persistable.getName());

		if (rootOu != null)
		{
			immutableBuilder.rootOrgUnit(rootOu);
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
		
		persistable.getMembers()
			.stream()
			.map(pMember -> OrgUnitMemberMapper.mapFromEntity(pMember, IDENTIFIER))
			.forEach(immutableBuilder::addMember);

		return immutableBuilder.build();
	}
	
	public static AuthorizableOrgUnit mapRootOuFromEntity(POrganizationalUnit pRootOu)
	{
		return OrganizationalUnitMapper.mapFromEntity(pRootOu, null, null);
	}
	
	public static AuthorizableOrgUnit mapSuperOuFromEntity(POrganizationalUnit pSuperOu, AuthorizableOrgUnit rootOu)
	{
		return OrganizationalUnitMapper.mapFromEntity(pSuperOu, rootOu, rootOu);
	}
	
	static class OrgUnitMemberMapper
	{
		public static POrgUnitMember mapFromImmutable(AuthorizableMember immutable, POrganizationalUnit pOrgUnit)
		{
			PPerson pPerson = PersonMapper.mapFromImmutable(immutable.getPerson());
			POrgUnitMember pMember = new POrgUnitMember(pOrgUnit, pPerson);
			
			immutable.getRoles()
				.stream()
				.map(RoleMapper::mapFromImmutable)
				.forEach(pMember::addRole);
			
			return pMember;
		}
		
		public static AuthorizableMember mapFromEntity(POrgUnitMember persistable, String orgUnitId)
		{
			Person person = PersonMapper.mapFromEntity(persistable.getPerson());
			AuthorizableMemberBuilder immutableBuilder = new AuthorizableMemberBuilder(person, orgUnitId);
			
			persistable.getRoles()
				.stream()
				.map(RoleMapper::mapFromEntity)
				.forEach(immutableBuilder::addRole);
			
			return immutableBuilder.build();
		}
	}
}
