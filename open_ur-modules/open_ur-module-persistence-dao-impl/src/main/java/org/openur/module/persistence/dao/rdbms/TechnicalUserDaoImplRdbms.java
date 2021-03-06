package org.openur.module.persistence.dao.rdbms;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.openur.module.domain.security.authorization.IAuthorizableTechUser;
import org.openur.module.persistence.dao.ITechnicalUserDao;
import org.openur.module.persistence.mapper.rdbms.IEntityDomainObjectMapper;
import org.openur.module.persistence.rdbms.entity.PTechnicalUser;
import org.openur.module.persistence.rdbms.repository.TechnicalUserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class TechnicalUserDaoImplRdbms
	implements ITechnicalUserDao
{
	@Inject
	private IEntityDomainObjectMapper<PTechnicalUser, ? extends IAuthorizableTechUser> technicalUserMapper;
	
	@Inject
	private TechnicalUserRepository technicalUserRepository;
	
	public TechnicalUserDaoImplRdbms()
	{
		super();
	}

	/**
	 * searches a technical user via it's unique identifier.
	 * 
	 * @param techUserId
	 *          : the unique identifier of the technical user.
	 * 
	 * @return the technical user or null, if no user is found.
	 * 
	 * @throws NumberFormatException
	 *           , if techUserId cannot be casted into a long-value.
	 */
	@Override
	public IAuthorizableTechUser findTechnicalUserById(String techUserId)
		throws NumberFormatException
	{
		long techUserIdL = Long.parseLong(techUserId);

		PTechnicalUser persistable = technicalUserRepository.findOne(techUserIdL);

		if (persistable == null)
		{
			return null;
		}

		return technicalUserMapper.mapFromEntity(persistable);
	}

	@Override
	public IAuthorizableTechUser findTechnicalUserByNumber(String techUserNumber)
	{
		PTechnicalUser persistable = technicalUserRepository.findTechnicalUserByNumber(techUserNumber);

		if (persistable == null)
		{
			return null;
		}

		return technicalUserMapper.mapFromEntity(persistable);
	}

	@Override
	public List<IAuthorizableTechUser> obtainAllTechnicalUsers()
	{
		List<PTechnicalUser> techUsers = technicalUserRepository.findAll();

		return techUsers
				.stream()
				.map(technicalUserMapper::mapFromEntity)
				.collect(Collectors.toList());
	}
}
