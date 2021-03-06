package org.openur.module.service.userstructure;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.openur.module.domain.security.authorization.IAuthorizableTechUser;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.persistence.dao.IPersonDao;
import org.openur.module.persistence.dao.ITechnicalUserDao;

public class UserServicesImpl
	implements IUserServices
{
	@Inject
	private IPersonDao personDao;

	@Inject
	private ITechnicalUserDao technicalUserDao;

	@Override
	public IPerson findPersonById(String personId)
	{
		Validate.notEmpty(personId, "person-id must not be empty!");
		
		IPerson person = personDao.findPersonById(personId);

		return person;
	}

	@Override
	public IPerson findPersonByNumber(String personalNumber)
	{
		Validate.notEmpty(personalNumber, "person-number must not be empty!");
		
		IPerson person = personDao.findPersonByNumber(personalNumber);

		return person;
	}

	@Override
	public Set<IPerson> obtainAllPersons()
	{
		Set<IPerson> personSet = new HashSet<>();

		List<IPerson> personList = personDao.obtainAllPersons();

		if (!CollectionUtils.isEmpty(personList))
		{
			personSet.addAll(personList);
		}

		return personSet;
	}

	@Override
	public IAuthorizableTechUser findTechnicalUserById(String techUserId)
	{
		Validate.notEmpty(techUserId, "user-id must not be empty!");
		
		IAuthorizableTechUser techUser = technicalUserDao.findTechnicalUserById(techUserId);

		return techUser;
	}

	@Override
	public IAuthorizableTechUser findTechnicalUserByNumber(String techUserNumber)
	{
		Validate.notEmpty(techUserNumber, "user-number must not be empty!");
		
		IAuthorizableTechUser techUser = technicalUserDao.findTechnicalUserByNumber(techUserNumber);

		return techUser;
	}

	@Override
	public Set<IAuthorizableTechUser> obtainAllTechnicalUsers()
	{
		Set<IAuthorizableTechUser> techUsersSet = new HashSet<>();

		List<IAuthorizableTechUser> techUsersList = technicalUserDao.obtainAllTechnicalUsers();

		if (!CollectionUtils.isEmpty(techUsersList))
		{
			techUsersSet.addAll(techUsersList);
		}

		return techUsersSet;
	}
}
