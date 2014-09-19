package org.openur.module.domain.userstructure.person;

import org.openur.module.domain.userstructure.person.abstr.AbstractPersonBuilder;


public class PersonSimpleBuilder
	extends AbstractPersonBuilder<PersonSimpleBuilder>
{
	// constructors:
	public PersonSimpleBuilder(String username, String password)
	{
		super(username, password);
	}
	
	public PersonSimpleBuilder(String identifier, String username, String password)
	{
		super(username, password);
	}
	
	public PersonSimple build()
	{
		return new PersonSimple(this);  
	}
}