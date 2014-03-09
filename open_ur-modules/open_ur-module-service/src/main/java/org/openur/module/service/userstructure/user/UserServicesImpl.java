package org.openur.module.service.userstructure.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.openur.module.domain.userstructure.user.person.IPerson;
import org.openur.module.domain.userstructure.user.technicaluser.ITechnicalUser;
import org.openur.module.persistence.userstructure.IUserStructureDao;

public class UserServicesImpl
	implements IUserServices
{
	private IUserStructureDao userStructureDao;

	//TODO: inject property directly, not via accessor => equip tests with spring-test-tools.
	@Inject
	public void setUserStructureDao(IUserStructureDao userStructureDao)
	{
		this.userStructureDao = userStructureDao;
	}

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
