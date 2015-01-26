package org.openur.module.service.userstructure;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.persistence.dao.IUserStructureDao;

public class UserServicesImpl
	implements IUserServices
{
	@Inject
	private IUserStructureDao userStructureDao;

	@Override
	public IPerson findPersonById(String personId)
	{
		IPerson person = userStructureDao.findPersonById(personId);

		return person;
	}

	@Override
	public IPerson findPersonByNumber(String personalNumber)
	{
		IPerson person = userStructureDao.findPersonByNumber(personalNumber);

		return person;
	}

	@Override
	public Set<IPerson> obtainAllPersons()
	{
		Set<IPerson> personSet = new HashSet<>();

		List<IPerson> personList = userStructureDao.obtainAllPersons();

		if (!CollectionUtils.isEmpty(personList))
		{
			personSet.addAll(personList);
		}

		return personSet;
	}

	@Override
	public ITechnicalUser findTechnicalUserById(String techUserId)
	{
		ITechnicalUser techUser = userStructureDao.findTechnicalUserById(techUserId);

		return techUser;
	}

	@Override
	public ITechnicalUser findTechnicalUserByNumber(String techUserNumber)
	{
		ITechnicalUser techUser = userStructureDao.findTechnicalUserByNumber(techUserNumber);

		return techUser;
	}

	@Override
	public Set<ITechnicalUser> obtainAllTechnicalUsers()
	{
		Set<ITechnicalUser> techUsersSet = new HashSet<>();

		List<ITechnicalUser> techUsersList = userStructureDao.obtainAllTechnicalUsers();

		if (!CollectionUtils.isEmpty(techUsersList))
		{
			techUsersSet.addAll(techUsersList);
		}

		return techUsersSet;
	}
}