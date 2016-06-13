package org.openur.module.persistence.mapper.rdbms;

import java.util.stream.Collectors;

import javax.inject.Inject;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.security.authorization.AuthorizableTechUser;
import org.openur.module.domain.security.authorization.AuthorizableTechUserBuilder;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.persistence.rdbms.entity.PPermission;
import org.openur.module.persistence.rdbms.entity.PTechnicalUser;

public class TechnicalUserMapper
	extends UserStructureBaseMapper implements IEntityDomainObjectMapper<PTechnicalUser, AuthorizableTechUser>
{
	@Inject
	private PermissionMapper permissionMapper;
	
	@Override
	public PTechnicalUser mapFromDomainObject(AuthorizableTechUser domainObject)
	{
		PTechnicalUser persistable = new PTechnicalUser(domainObject.getTechUserNumber());

		persistable.setStatus(domainObject.getStatus());
		
//	immutable.getAllPermissions().keySet()
//		.stream()
//		.forEach(
//			app -> immutable.getPermissions(app)
//				.stream()
//				.map(permissionMapper::mapFromImmutable)
//				.forEach(pPerm -> persistable.addPermssion(pPerm))
	//		);
	
		for (OpenURApplication app : domainObject.getAllPermissions().keySet())
		{
			domainObject.getPermissions(app)
				.stream()
				.map(permissionMapper::mapFromDomainObject)
				.forEach(p -> persistable.addPermssion(p));
		}		
		
		return persistable;
	}
	
	@Override
	public AuthorizableTechUser mapFromEntity(PTechnicalUser entity)
	{
		AuthorizableTechUserBuilder immutableBuilder = new AuthorizableTechUserBuilder(entity.getTechUserNumber());		
		immutableBuilder = (AuthorizableTechUserBuilder) super.mapFromEntity(immutableBuilder, entity);
		
		immutableBuilder.permissions(
			entity.getPermissions()
				.stream()
				.map(permissionMapper::mapFromEntity)
				.collect(Collectors.toSet()),
			immutableBuilder
		);
		
		return immutableBuilder.build();
	}

	public static boolean domainObjectEqualsToEntity(AuthorizableTechUser domainObject, PTechnicalUser entity)
	{
		if (!UserStructureBaseMapper.domainObjectEqualsToEntity(domainObject, entity))
		{
			return false;
		}
		
		for (PPermission pPerm : entity.getPermissions())
		{
			OpenURPermission permission = findPermissionInDomainObject(pPerm, domainObject);
			
			if (permission == null || !PermissionMapper.domainObjectEqualsToEntity(permission, pPerm))
			{
				return false;
			}
		}
		
		return domainObject.getTechUserNumber().equals(entity.getTechUserNumber());
	}
	
	private static OpenURPermission findPermissionInDomainObject(PPermission pPerm, AuthorizableTechUser techUser)
	{
		for (OpenURApplication openUrApp : techUser.getAllPermissions().keySet())
		{
			for (OpenURPermission openUrPerm : techUser.getPermissions(openUrApp))
			{
				if (PermissionMapper.domainObjectEqualsToEntity(openUrPerm, pPerm))
				{
					return openUrPerm;
				}
			}
		}
		
		return null;
	}
}