package org.openur.module.persistence.dao;

import java.util.List;

import org.openur.module.domain.userstructure.person.IPerson;

public interface IPersonDao
{
	/**
	 * searches a person via it's unique identifier.
	 * 
	 * @param personId
	 *          : the unique identifier of the person.
	 * 
	 * @return the person or null, if no person is found.
	 */
	IPerson findPersonById(String personId);

	/**
	 * searches a person via it's (domain specific) user-number.
	 * 
	 * @param personalNumber
	 *          : the user-number of the person.
	 * 
	 * @return the person or null, if no person is found.
	 */
	IPerson findPersonByNumber(String personalNumber);

	/**
	 * returns all stored persons in a list. If no persons are found, the
	 * result-list will be empty (not null).
	 * 
	 * @return List with all persons.
	 */
	List<IPerson> obtainAllPersons();
}
