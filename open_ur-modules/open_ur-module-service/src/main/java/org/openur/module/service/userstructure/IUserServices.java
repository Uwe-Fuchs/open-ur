package org.openur.module.service.userstructure;

import java.util.Set;

import org.openur.module.domain.security.authorization.IAuthorizableTechUser;
import org.openur.module.domain.userstructure.person.IPerson;

public interface IUserServices
{
  /**
   * searches a person via it's unique identifier.
   * 
   * @param personId : the unique identifier of the person.
   * 
   * @return the person or null, if no person is found.
   */
  IPerson findPersonById(String personId);
  
  /**
   * searches a person via it's (domain specific) user-number.
   * 
   * @param personalNumber : the user-number of the person.
   * 
   * @return the person or null, if no person is found.
   */
  IPerson findPersonByNumber(String personalNumber);
	
  /**
   * returns all stored persons in a set.
   * If no persons are found, the result-set will be empty (not null).
   * 
   * @return Set with all persons.
   */
  Set<IPerson> obtainAllPersons();
	
  /**
   * searches a technical user via it's unique identifier.
   * 
   * @param techUserId : the unique identifier of the technical user.
   * 
   * @return the technical user or null, if no user is found.
   */
	IAuthorizableTechUser findTechnicalUserById(String techUserId);
	
  /**
   * searches a technical user via it's (domain specific) user-number.
   * 
   * @param techUserNumber : the user-number of the technical user.
   * 
   * @return the technical user or null, if no user is found.
   */
	IAuthorizableTechUser findTechnicalUserByNumber(String techUserNumber);
	
  /**
   * returns all stored technical users in a set.
   * If no users are found, the result-set will be empty (not null).
   * 
   * @return Set with all technical users.
   */
  Set<IAuthorizableTechUser> obtainAllTechnicalUsers();
}
