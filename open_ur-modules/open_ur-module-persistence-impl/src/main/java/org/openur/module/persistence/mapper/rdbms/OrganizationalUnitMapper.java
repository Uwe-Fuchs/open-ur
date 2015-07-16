package org.openur.module.persistence.mapper.rdbms;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.security.authorization.AuthorizableMember.AuthorizableMemberBuilder;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit.AuthorizableOrgUnitBuilder;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.util.DefaultsUtil;
import org.openur.module.persistence.rdbms.entity.POrgUnitMember;
import org.openur.module.persistence.rdbms.entity.POrganizationalUnit;
import org.openur.module.persistence.rdbms.entity.PPerson;
import org.openur.module.persistence.rdbms.entity.PRole;

public class OrganizationalUnitMapper
{
	public static POrganizationalUnit mapFromImmutable(AuthorizableOrgUnit immutable)
	{
		POrganizationalUnit pRootOu = null;
		POrganizationalUnit pSuperOu = null;
		
		if (immutable.getRootOrgUnit() != null)
		{
			pRootOu = mapRootOuFromImmutable(immutable.getRootOrgUnit());
			pSuperOu = mapSuperOuFromImmutable(immutable.getSuperOrgUnit(), pRootOu);
		}
		
		return mapFromImmutable(immutable, pRootOu, pSuperOu);
	}
	
	static POrganizationalUnit mapFromImmutable(
		AuthorizableOrgUnit immutable, POrganizationalUnit rootOu,	POrganizationalUnit superOu)
	{
		POrganizationalUnit persistable = new POrganizationalUnit(immutable.getOrgUnitNumber(), immutable.getName());

		persistable.setStatus(immutable.getStatus());
		persistable.setRootOu(rootOu);
		persistable.setSuperOu(superOu);
		persistable.setShortName(immutable.getShortName());
		persistable.setDescription(immutable.getDescription());
		persistable.setAddress(immutable.getAddress() != null ? AddressMapper.mapFromImmutable(immutable.getAddress()) : null);
		persistable.setEmailAddress(immutable.getEmailAddress() != null ? immutable.getEmailAddress().getAsPlainEMailAddress() : null);
		
		immutable.getMembers()
			.stream()
			.map(immutableMember -> OrgUnitMemberMapper.mapFromImmutable(immutableMember, persistable))
			.forEach(persistable::addMember);

		return persistable;
	}
	
	static POrganizationalUnit mapRootOuFromImmutable(AuthorizableOrgUnit rootOu)
	{
		return OrganizationalUnitMapper.mapFromImmutable(rootOu, null, null);
	}
	
	static POrganizationalUnit mapSuperOuFromImmutable(AuthorizableOrgUnit superOu, POrganizationalUnit pRootOu)
	{
		return OrganizationalUnitMapper.mapFromImmutable(superOu, pRootOu, pRootOu);
	}
	
	public static AuthorizableOrgUnit mapFromEntity(POrganizationalUnit persistable, boolean inclMembers, boolean inclRoles)
	{
		Validate.isTrue(!(!inclMembers && inclRoles), "You can't get the roles without the members!");
		
		AuthorizableOrgUnit rootOu = null;
		AuthorizableOrgUnit superOu = null;
		
		if (persistable.getRootOu() != null)
		{
			rootOu = mapRootOuFromEntity(persistable.getRootOu());
			
			if (persistable.getRootOu().equals(persistable.getSuperOu()))
			{
				superOu = rootOu;
			} else
			{
				superOu = mapSuperOuFromEntity(persistable.getSuperOu(), rootOu);				
			}
		}
		
		return mapFromEntity(persistable, rootOu, superOu, inclMembers, inclRoles);
	}

	static AuthorizableOrgUnit mapFromEntity(
		POrganizationalUnit persistable, AuthorizableOrgUnit rootOu, AuthorizableOrgUnit superOu, boolean inclMembers, boolean inclRoles)
	{		
		final String IDENTIFIER = DefaultsUtil.getRandomIdentifierByDefaultMechanism();

		AuthorizableOrgUnitBuilder immutableBuilder = new AuthorizableOrgUnitBuilder(persistable.getOrgUnitNumber(), persistable.getName());		
		immutableBuilder = UserStructureBaseMapper.buildImmutable(immutableBuilder, persistable);

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
			.shortName(persistable.getShortName())
			.description(persistable.getDescription())
			.address(persistable.getAddress() != null ? AddressMapper.mapFromEntity(persistable.getAddress()) : null)
			.emailAddress(StringUtils.isNotEmpty(persistable.getEmailAddress()) ? EMailAddress.create(persistable.getEmailAddress()) : null);
		
		if (inclMembers)
		{
			persistable.getMembers()
				.stream()
				.map(pMember -> OrgUnitMemberMapper.mapFromEntity(pMember, IDENTIFIER, inclRoles))
				.forEach(immutableBuilder::addMember);
		}

		return immutableBuilder.build();
	}
	
	static AuthorizableOrgUnit mapRootOuFromEntity(POrganizationalUnit pRootOu)
	{
		return OrganizationalUnitMapper.mapFromEntity(pRootOu, null, null, true, true);
	}
	
	static AuthorizableOrgUnit mapSuperOuFromEntity(POrganizationalUnit pSuperOu, AuthorizableOrgUnit rootOu)
	{
		return OrganizationalUnitMapper.mapFromEntity(pSuperOu, rootOu, rootOu, true, true);
	}
	
	public static class OrgUnitMemberMapper
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
		
		public static AuthorizableMember mapFromEntity(POrgUnitMember persistable, String orgUnitId, boolean inclRoles)
		{
			Person person = PersonMapper.mapFromEntity(persistable.getPerson());
			AuthorizableMemberBuilder immutableBuilder = new AuthorizableMemberBuilder(person, orgUnitId);
			immutableBuilder.creationDate(persistable.getCreationDate());
			immutableBuilder.lastModifiedDate(persistable.getLastModifiedDate());
			
			if (persistable.getIdentifier() != null)
			{
				immutableBuilder.identifier(persistable.getIdentifier());
			}
			
			if (inclRoles)
			{
				persistable.getRoles()
					.stream()
					.map(RoleMapper::mapFromEntity)
					.forEach(immutableBuilder::addRole);				
			}
			
			return immutableBuilder.build();
		}
	}

	public static boolean immutableEqualsToEntity(AuthorizableOrgUnit immutable, POrganizationalUnit persistable)
	{
		if (!UserStructureBaseMapper.immutableEqualsToEntity(immutable, persistable))
		{
			return false;
		}
		
		if ((immutable.getRootOrgUnit() != null || persistable.getRootOu() != null)
			&& !immutableEqualsToEntity(immutable.getRootOrgUnit(), persistable.getRootOu()))
		{
			return false;
		}
		
		if ((immutable.getSuperOrgUnit() != null || persistable.getSuperOu() != null)
			&& !immutableEqualsToEntity(immutable.getSuperOrgUnit(), persistable.getSuperOu()))
		{
			return false;
		}
	
		if ((immutable.getAddress() != null || persistable.getAddress() != null)
			&& !AddressMapper.immutableEqualsToEntity(immutable.getAddress(),	persistable.getAddress()))
		{
			return false;
		}
	
		for (POrgUnitMember pMember : persistable.getMembers())
		{
			AuthorizableMember member = findMemberInImmutable(pMember, immutable);
	
			if (member == null || !OrganizationalUnitMapper.immutableMemberEqualsToEntityMember(member, pMember))
			{
				return false;
			}
		}
		
		return new EqualsBuilder()
			.append(immutable.getOrgUnitNumber(), persistable.getOrgUnitNumber())
			.append(immutable.getName(), persistable.getName())
			.append(immutable.getShortName(), persistable.getShortName())
			.append(immutable.getDescription(), persistable.getDescription())
			.append(immutable.getEmailAddress() != null ? immutable.getEmailAddress()	.getAsPlainEMailAddress() : null, persistable.getEmailAddress())
			.isEquals();
	}
	
	public static boolean immutableMemberEqualsToEntityMember(AuthorizableMember immutableMember, POrgUnitMember persistableMember)
	{
		if (!AbstractEntityMapper.immutableEqualsToEntity(immutableMember, persistableMember))
		{
			return false;
		}
		
		if (!PersonMapper.immutableEqualsToEntity(immutableMember.getPerson(), persistableMember.getPerson()))
		{
			return false;
		}
		
		for (PRole pRole : persistableMember.getRoles())
		{
			OpenURRole role = findRoleInImmutableMember(pRole, immutableMember);
			
			if (role == null || !RoleMapper.immutableEqualsToEntity(role, pRole))
			{
				return false;
			}
		}
		
		return true;
	}

	private static AuthorizableMember findMemberInImmutable(POrgUnitMember pMember, AuthorizableOrgUnit immutable)
	{
		for (AuthorizableMember member : immutable.getMembers())
		{
			if (PersonMapper.immutableEqualsToEntity(member.getPerson(), pMember.getPerson()))
			{
				return member;
			}
		}
	
		return null;
	}
	
	private static OpenURRole findRoleInImmutableMember(PRole pRole, AuthorizableMember immutableMember)
	{
		for (OpenURRole role : immutableMember.getRoles())
		{
			if (RoleMapper.immutableEqualsToEntity(role, pRole))
			{
				return role;
			}
		}
		
		return null;
	}
}
