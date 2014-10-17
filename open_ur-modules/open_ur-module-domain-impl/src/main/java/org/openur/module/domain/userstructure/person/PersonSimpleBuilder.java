package org.openur.module.domain.userstructure.person;

import org.openur.module.domain.userstructure.person.abstr.AbstractPersonBuilder;


public class PersonSimpleBuilder
	extends AbstractPersonBuilder<PersonSimpleBuilder>
{
	// constructors:
	public PersonSimpleBuilder()
	{
		super();
	}
	
	public PersonSimpleBuilder(String identifier)
	{
		super(identifier);
	}
	
	public PersonSimple build()
	{
		return new PersonSimple(this);  
	}
}
