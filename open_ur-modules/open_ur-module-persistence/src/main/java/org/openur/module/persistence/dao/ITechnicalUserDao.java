package org.openur.module.persistence.dao;

import java.util.List;

import org.openur.module.domain.userstructure.technicaluser.ITechnicalUser;

public interface ITechnicalUserDao
{
	/**
	 * searches a technical user via it's unique identifier.
	 * 
	 * @param techUserId
	 *          : the unique identifier of the technical user.
	 * 
	 * @return the technical user or null, if no user is found.
	 */
	ITechnicalUser findTechnicalUserById(String techUserId);

	/**
	 * searches a technical user via it's (domain specific) user-number.
	 * 
	 * @param techUserNumber
	 *          : the user-number of the technical user.
	 * 
	 * @return the technical user or null, if no user is found.
	 */
	ITechnicalUser findTechnicalUserByNumber(String techUserNumber);

	/**
	 * returns all stored technical users in a list. If no users are found, the
	 * result-list will be empty (not null).
	 * 
	 * @return List with all technical users.
	 */
	List<ITechnicalUser> obtainAllTechnicalUsers();
}
