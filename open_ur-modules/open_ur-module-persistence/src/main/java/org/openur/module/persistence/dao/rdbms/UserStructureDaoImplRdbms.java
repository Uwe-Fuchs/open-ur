package org.openur.module.persistence.dao.rdbms;

import java.util.List;

import javax.inject.Inject;

import org.openur.module.domain.userstructure.orgunit.IOrganizationalUnit;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.persistence.dao.IUserStructureDao;
import org.openur.module.persistence.mapper.rdbms.PersonMapper;
import org.openur.module.persistence.rdbms.entity.PPerson;
import org.openur.module.persistence.rdbms.repository.PersonRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class UserStructureDaoImplRdbms
	implements IUserStructureDao
{
	@Inject
	private PersonRepository personRepository;
	
	public UserStructureDaoImplRdbms()
	{
		super();
	}

	public void setPersonRepository(PersonRepository personRepository)
	{
		this.personRepository = personRepository;
	}

	@Override
	public IPerson findPersonById(String personId)
	{		
		PPerson persistable = personRepository.findOne(Long.parseLong(personId));
		
		if (persistable == null)
		{
			return null;
		}
		
		return PersonMapper.mapFromEntity(persistable);
	}

	@Override
	public IPerson findPersonByNumber(String personalNumber)
	{
		PPerson persistable = personRepository.findPersonByNumber(personalNumber);
		
		if (persistable == null)
		{
			return null;
		}
		
		return PersonMapper.mapFromEntity(persistable);
	}

	@Override
	public List<IPerson> obtainAllPersons()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOrganizationalUnit findOrgUnitById(String orgUnitId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOrganizationalUnit findOrgUnitByNumber(String orgUnitNumber)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IOrganizationalUnit> obtainAllOrgUnits()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IOrganizationalUnit> obtainSubOrgUnitsForOrgUnit(
		String orgUnitId, boolean inclMembers)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IOrganizationalUnit> obtainRootOrgUnits()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITechnicalUser findTechnicalUserById(String techUserId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITechnicalUser findTechnicalUserByNumber(String techUserNumber)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ITechnicalUser> obtainAllTechnicalUsers()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
