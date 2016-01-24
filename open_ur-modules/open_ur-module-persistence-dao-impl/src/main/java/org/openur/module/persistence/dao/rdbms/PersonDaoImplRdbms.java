package org.openur.module.persistence.dao.rdbms;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.openur.module.domain.userstructure.person.IPerson;
import org.openur.module.persistence.dao.IPersonDao;
import org.openur.module.persistence.mapper.rdbms.IEntityDomainObjectMapper;
import org.openur.module.persistence.rdbms.entity.PPerson;
import org.openur.module.persistence.rdbms.repository.PersonRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class PersonDaoImplRdbms
	implements IPersonDao
{
	@Inject
	private IEntityDomainObjectMapper<PPerson, ? extends IPerson> personMapper;
	
	@Inject
	private PersonRepository personRepository;
	
	public PersonDaoImplRdbms()
	{
		super();
	}
	/**
	 * searches a person via it's unique identifier.
	 * 
	 * @param personId
	 *          : the unique identifier of the person.
	 * 
	 * @return the person or null, if no person is found.
	 * 
	 * @throws NumberFormatException
	 *           , if personId cannot be casted into a long-value.
	 */
	@Override
	public IPerson findPersonById(String personId)
		throws NumberFormatException
	{
		long personIdL = Long.parseLong(personId);

		PPerson persistable = personRepository.findOne(personIdL);

		if (persistable == null)
		{
			return null;
		}

		return personMapper.mapFromEntity(persistable);
	}

	@Override
	public IPerson findPersonByNumber(String personalNumber)
	{
		PPerson persistable = personRepository.findPersonByNumber(personalNumber);

		if (persistable == null)
		{
			return null;
		}

		return personMapper.mapFromEntity(persistable);
	}

	@Override
	public List<IPerson> obtainAllPersons()
	{
		List<PPerson> persons = personRepository.findAll();

		return persons
				.stream()
				.map(personMapper::mapFromEntity)
				.collect(Collectors.toList());
	}
}
