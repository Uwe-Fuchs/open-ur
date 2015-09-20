package org.openur.module.persistence.mapper.rdbms;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.AuthorizableMember.AuthorizableMemberBuilder;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit.AuthorizableOrgUnitBuilder;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.util.DefaultsUtil;
import org.openur.module.persistence.rdbms.entity.POrgUnitMember;
import org.openur.module.persistence.rdbms.entity.POrganizationalUnit;
import org.openur.module.persistence.rdbms.entity.PPerson;
import org.openur.module.persistence.rdbms.entity.PRole;

public class OrganizationalUnitMapper
	extends UserStructureBaseMapper implements IOrganizationalUnitMapper<AuthorizableOrgUnit>
{
	@Inject
	private AddressMapper addressMapper;
	
	@Inject
	private OrgUnitMemberMapper orgUnitMemberMapper;
	
	@Override
	public POrganizationalUnit mapFromDomainObject(AuthorizableOrgUnit domainObject)
	{
		POrganizationalUnit pRootOu = null;
		POrganizationalUnit pSuperOu = null;
		
		if (domainObject.getRootOrgUnit() != null)
		{
			pRootOu = mapRootOuFromDomainObject(domainObject.getRootOrgUnit());
			pSuperOu = mapSuperOuFromDomainObject(domainObject.getSuperOrgUnit(), pRootOu);
		}
		
		return mapFromDomainObject(domainObject, pRootOu, pSuperOu);
	}
	
	POrganizationalUnit mapFromDomainObject(
		AuthorizableOrgUnit immutable, POrganizationalUnit rootOu,	POrganizationalUnit superOu)
	{
		POrganizationalUnit persistable = new POrganizationalUnit(immutable.getOrgUnitNumber(), immutable.getName());

		persistable.setStatus(immutable.getStatus());
		persistable.setRootOu(rootOu);
		persistable.setSuperOu(superOu);
		persistable.setShortName(immutable.getShortName());
		persistable.setDescription(immutable.getDescription());
		persistable.setAddress(immutable.getAddress() != null ? addressMapper.mapFromDomainObject(immutable.getAddress()) : null);
		persistable.setEmailAddress(immutable.getEmailAddress() != null ? immutable.getEmailAddress().getAsPlainEMailAddress() : null);
		
		immutable.getMembers()
			.stream()
			.map(immutableMember -> orgUnitMemberMapper.mapFromDomainObject(immutableMember, persistable))
			.forEach(persistable::addMember);

		return persistable;
	}
	
	POrganizationalUnit mapRootOuFromDomainObject(AuthorizableOrgUnit rootOu)
	{
		return mapFromDomainObject(rootOu, null, null);
	}
	
	POrganizationalUnit mapSuperOuFromDomainObject(AuthorizableOrgUnit superOu, POrganizationalUnit pRootOu)
	{
		return mapFromDomainObject(superOu, pRootOu, pRootOu);
	}
	
	@Override
	public AuthorizableOrgUnit mapFromEntity(POrganizationalUnit entity, boolean inclMembers, boolean inclRoles)
	{
		Validate.isTrue(!(!inclMembers && inclRoles), "You can't get the roles without the members!");
		
		AuthorizableOrgUnit rootOu = null;
		AuthorizableOrgUnit superOu = null;
		
		if (entity.getRootOu() != null)
		{
			rootOu = mapRootOuFromEntity(entity.getRootOu());
			
			if (entity.getRootOu().equals(entity.getSuperOu()))
			{
				superOu = rootOu;
			} else
			{
				superOu = mapSuperOuFromEntity(entity.getSuperOu(), rootOu);				
			}
		}
		
		return mapFromEntity(entity, rootOu, superOu, inclMembers, inclRoles);
	}

	AuthorizableOrgUnit mapFromEntity(
		POrganizationalUnit persistable, AuthorizableOrgUnit rootOu, AuthorizableOrgUnit superOu, boolean inclMembers, boolean inclRoles)
	{		
		final String IDENTIFIER = persistable.getIdentifier() != null ? 
			persistable.getIdentifier() : DefaultsUtil.getRandomIdentifierByDefaultMechanism();

		AuthorizableOrgUnitBuilder immutableBuilder = new AuthorizableOrgUnitBuilder(persistable.getOrgUnitNumber(), persistable.getName());		
		immutableBuilder = super.mapFromEntity(immutableBuilder, persistable);

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
			.address(persistable.getAddress() != null ? addressMapper.mapFromEntity(persistable.getAddress()) : null)
			.emailAddress(StringUtils.isNotEmpty(persistable.getEmailAddress()) ? EMailAddress.create(persistable.getEmailAddress()) : null);
		
		if (inclMembers)
		{
			persistable.getMembers()
				.stream()
				.map(pMember -> orgUnitMemberMapper.mapFromEntity(pMember, IDENTIFIER, inclRoles))
				.forEach(immutableBuilder::addMember);
		}

		return immutableBuilder.build();
	}
	
	AuthorizableOrgUnit mapRootOuFromEntity(POrganizationalUnit pRootOu)
	{
		return mapFromEntity(pRootOu, null, null, true, true);
	}
	
	AuthorizableOrgUnit mapSuperOuFromEntity(POrganizationalUnit pSuperOu, AuthorizableOrgUnit rootOu)
	{
		return mapFromEntity(pSuperOu, rootOu, rootOu, true, true);
	}
	
	public static class OrgUnitMemberMapper implements IOrgUnitMemberMapper<AuthorizableMember>
	{		
		@Inject
		private PersonMapper personMapper;
		
		@Inject
		private RoleMapper roleMapper;
		
		@Override
		public POrgUnitMember mapFromDomainObject(AuthorizableMember domainObject, POrganizationalUnit pOrgUnit)
		{
			PPerson pPerson = personMapper.mapFromDomainObject(domainObject.getPerson());
			POrgUnitMember pMember = new POrgUnitMember(pOrgUnit, pPerson);
			
			domainObject.getRoles()
				.stream()
				.map(roleMapper::mapFromDomainObject)
				.forEach(pMember::addRole);
			
			return pMember;
		}
		
		@Override
		public AuthorizableMember mapFromEntity(POrgUnitMember entity, String orgUnitId, boolean inclRoles)
		{
			Person person = personMapper.mapFromEntity(entity.getPerson());
			AuthorizableMemberBuilder immutableBuilder = new AuthorizableMemberBuilder(person, orgUnitId);
			immutableBuilder.creationDate(entity.getCreationDate());
			immutableBuilder.lastModifiedDate(entity.getLastModifiedDate());
			
			if (entity.getIdentifier() != null)
			{
				immutableBuilder.identifier(entity.getIdentifier());
			}
			
			if (inclRoles)
			{				
				entity.getRoles()
					.stream()
					.map(roleMapper::mapFromEntity)
					.forEach(immutableBuilder::addRole);				
			}
			
			return immutableBuilder.build();
		}
	}

	public static boolean domainObjectEqualsToEntity(AuthorizableOrgUnit domainObject, POrganizationalUnit entity)
	{
		if (!UserStructureBaseMapper.domainObjectEqualsToEntity(domainObject, entity))
		{
			return false;
		}
		
		if ((domainObject.getRootOrgUnit() != null || entity.getRootOu() != null)
			&& !domainObjectEqualsToEntity(domainObject.getRootOrgUnit(), entity.getRootOu()))
		{
			return false;
		}
		
		if ((domainObject.getSuperOrgUnit() != null || entity.getSuperOu() != null)
			&& !domainObjectEqualsToEntity(domainObject.getSuperOrgUnit(), entity.getSuperOu()))
		{
			return false;
		}
	
		if ((domainObject.getAddress() != null || entity.getAddress() != null)
			&& !AddressMapper.domainObjectEqualsToEntity(domainObject.getAddress(),	entity.getAddress()))
		{
			return false;
		}
	
		for (POrgUnitMember pMember : entity.getMembers())
		{
			AuthorizableMember member = findMemberInDomianObject(pMember, domainObject);
	
			if (member == null || !OrganizationalUnitMapper.domainObjectMemberEqualsToEntityMember(member, pMember))
			{
				return false;
			}
		}
		
		return new EqualsBuilder()
			.append(domainObject.getOrgUnitNumber(), entity.getOrgUnitNumber())
			.append(domainObject.getName(), entity.getName())
			.append(domainObject.getShortName(), entity.getShortName())
			.append(domainObject.getDescription(), entity.getDescription())
			.append(domainObject.getEmailAddress() != null ? domainObject.getEmailAddress()	.getAsPlainEMailAddress() : null, entity.getEmailAddress())
			.isEquals();
	}
	
	public static boolean domainObjectMemberEqualsToEntityMember(AuthorizableMember domainObjectMember, POrgUnitMember entityMember)
	{
		if (!AbstractEntityMapper.domainObjectEqualsToEntity(domainObjectMember, entityMember))
		{
			return false;
		}
		
		if (!PersonMapper.domainObjectEqualsToEntity(domainObjectMember.getPerson(), entityMember.getPerson()))
		{
			return false;
		}
		
		for (PRole pRole : entityMember.getRoles())
		{
			OpenURRole role = findRoleInDomainObjectMember(pRole, domainObjectMember);
			
			if (role == null || !RoleMapper.domainObjectEqualsToEntity(role, pRole))
			{
				return false;
			}
		}
		
		return true;
	}

	private static AuthorizableMember findMemberInDomianObject(POrgUnitMember pMember, AuthorizableOrgUnit openUrOrgUnit)
	{
		for (AuthorizableMember openUrMember : openUrOrgUnit.getMembers())
		{
			if (PersonMapper.domainObjectEqualsToEntity(openUrMember.getPerson(), pMember.getPerson()))
			{
				return openUrMember;
			}
		}
	
		return null;
	}
	
	private static OpenURRole findRoleInDomainObjectMember(PRole pRole, AuthorizableMember openUrMember)
	{
		for (OpenURRole openUrRole : openUrMember.getRoles())
		{
			if (RoleMapper.domainObjectEqualsToEntity(openUrRole, pRole))
			{
				return openUrRole;
			}
		}
		
		return null;
	}
}
