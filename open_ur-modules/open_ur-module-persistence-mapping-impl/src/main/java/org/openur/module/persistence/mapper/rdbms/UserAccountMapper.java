package org.openur.module.persistence.mapper.rdbms;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.openur.module.domain.security.authentication.UserAccount;
import org.openur.module.domain.security.authentication.UserAccountBuilder;
import org.openur.module.domain.security.authorization.AuthorizableTechUser;
import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.persistence.rdbms.entity.PUserAccount;
import org.openur.module.persistence.rdbms.entity.PUserStructureBase;

public class UserAccountMapper
	extends AbstractEntityMapper
	implements IUserAccountMapper<UserAccount, UserStructureBase>
{
	@Inject
	private PersonMapper personMapper;	

	@Inject
	private TechnicalUserMapper technicalUserMapper;
	
	@Override
	public PUserAccount mapFromDomainObject(UserAccount userAccount, UserStructureBase userStructureBase)
	{
		PUserStructureBase pUserStructureBase;
		
		if (userStructureBase instanceof Person)
		{
			Person person = (Person) userStructureBase;
			pUserStructureBase = personMapper.mapFromDomainObject(person);
		} else if (userStructureBase instanceof AuthorizableTechUser)
		{
			AuthorizableTechUser techUser = (AuthorizableTechUser) userStructureBase;
			pUserStructureBase = technicalUserMapper.mapFromDomainObject(techUser);
		} else
		{
			throw new IllegalArgumentException(String.format("Wrong type for userStructureBaseObject: %s", userStructureBase.getClass()));
		}
		
		PUserAccount persistable = new PUserAccount(pUserStructureBase, userAccount.getUserName(), userAccount.getPassWord());
		
		if (StringUtils.isNotBlank(userAccount.getSalt()))
		{
			persistable.setSalt(userAccount.getSalt());
		}
		
		return persistable;
	}

	@Override
	public UserAccount mapFromEntity(PUserAccount entity)
	{
		UserAccountBuilder immutableBuilder = new UserAccountBuilder(entity.getUserName(), entity.getPassWord());

		super.mapFromEntity(immutableBuilder, entity);
		
		if (entity.getUserId() != null)
		{
			immutableBuilder.identifier(entity.getUserId());			
		}			
		
		if (StringUtils.isNotBlank(entity.getSalt()))
		{
			immutableBuilder.salt(entity.getSalt());
		}
		
		return immutableBuilder.build();
	}	
	
	public static boolean domainObjectEqualsToEntity(UserAccount domainObject, PUserAccount entity)
	{
		if (!AbstractEntityMapper.domainObjectEqualsToEntity(domainObject, entity))
		{
			return false;
		}
		
		return new EqualsBuilder()
				.append(domainObject.getUserName(), entity.getUserName())
				.append(domainObject.getPassWord(), entity.getPassWord())
				.append(domainObject.getSalt(), entity.getSalt())
				.isEquals();
	}
}
