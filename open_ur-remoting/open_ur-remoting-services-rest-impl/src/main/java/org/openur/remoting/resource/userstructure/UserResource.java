package org.openur.remoting.resource.userstructure;

import java.util.Set;

import javax.inject.Inject;

import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.service.userstructure.IUserServices;

public class UserResource
	implements IUserResource
{
	@Inject
	private IUserServices userServices;

	@Override
	public IPerson findPersonById(String id)
	{
		return userServices.findPersonById(id);
	}

	@Override
	public IPerson findPersonByNumber(String number)
	{
		return userServices.findPersonByNumber(number);
	}

	@Override
	public Set<IPerson> obtainAllPersons()
	{
		return userServices.obtainAllPersons();
	}

	@Override
	public ITechnicalUser findTechnicalUserById(String id)
	{
		return userServices.findTechnicalUserById(id);
	}

	@Override
	public ITechnicalUser findTechnicalUserByNumber(String number)
	{
		return userServices.findTechnicalUserByNumber(number);
	}

	@Override
	public Set<ITechnicalUser> obtainAllTechnicalUsers()
	{
		return userServices.obtainAllTechnicalUsers();
	}
}
