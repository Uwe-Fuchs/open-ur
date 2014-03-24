package org.openur.module.domain.userstructure.user.person;

import org.openur.module.domain.userstructure.user.person.abstr.AbstractPersonSimpleBuilder;


public class PersonSimpleBuilder
	extends AbstractPersonSimpleBuilder<PersonSimpleBuilder>
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
