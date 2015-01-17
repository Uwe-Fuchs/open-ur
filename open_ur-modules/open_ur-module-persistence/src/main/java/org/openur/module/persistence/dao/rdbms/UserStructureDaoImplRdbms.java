package org.openur.module.persistence.dao.rdbms;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.openur.module.domain.security.authorization.IAuthorizableOrgUnit;
import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;
import org.openur.module.persistence.dao.IUserStructureDao;
import org.openur.module.persistence.mapper.rdbms.PersonMapper;
import org.openur.module.persistence.mapper.rdbms.TechnicalUserMapper;
import org.openur.module.persistence.rdbms.entity.PPerson;
import org.openur.module.persistence.rdbms.entity.PTechnicalUser;
import org.openur.module.persistence.rdbms.repository.PersonRepository;
import org.openur.module.persistence.rdbms.repository.TechnicalUserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class UserStructureDaoImplRdbms
	implements IUserStructureDao
{
	@Inject
	private PersonRepository personRepository;

	@Inject
	private TechnicalUserRepository technicalUserRepository;
	
	public UserStructureDaoImplRdbms()
	{
		super();
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
		List<PPerson> persons = personRepository.findAll();
		
		return persons
			.stream()
			.map(PersonMapper::mapFromEntity)
			.collect(Collectors.toList());
	}

	@Override
	public ITechnicalUser findTechnicalUserById(String techUserId)
	{
		PTechnicalUser persistable = technicalUserRepository.findOne(Long.parseLong(techUserId));
		
		if (persistable == null)
		{
			return null;
		}
		
		return TechnicalUserMapper.mapFromEntity(persistable);
	}

	@Override
	public ITechnicalUser findTechnicalUserByNumber(String techUserNumber)
	{
		PTechnicalUser persistable = technicalUserRepository.findTechnicalUserByNumber(techUserNumber);
		
		if (persistable == null)
		{
			return null;
		}
		
		return TechnicalUserMapper.mapFromEntity(persistable);
	}

	@Override
	public List<ITechnicalUser> obtainAllTechnicalUsers()
	{
		List<PTechnicalUser> techUsers = technicalUserRepository.findAll();
		
		return techUsers
			.stream()
			.map(TechnicalUserMapper::mapFromEntity)
			.collect(Collectors.toList());
	}

	@Override
	public IAuthorizableOrgUnit findOrgUnitById(String orgUnitId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAuthorizableOrgUnit findOrgUnitByNumber(String orgUnitNumber)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IAuthorizableOrgUnit> obtainAllOrgUnits()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IAuthorizableOrgUnit> obtainSubOrgUnitsForOrgUnit(String orgUnitId, boolean inclMembers)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IAuthorizableOrgUnit> obtainRootOrgUnits()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
