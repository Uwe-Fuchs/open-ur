package org.openur.module.domain.userstructure.user.person;

import org.openur.module.domain.userstructure.user.person.abstr.AbstractPerson;

public class PersonSimple
	extends AbstractPerson
{
	private static final long serialVersionUID = 5562036553718902426L;

  // constructor:
	public PersonSimple(PersonSimpleBuilder b)
	{
		super(b);
	}
}
