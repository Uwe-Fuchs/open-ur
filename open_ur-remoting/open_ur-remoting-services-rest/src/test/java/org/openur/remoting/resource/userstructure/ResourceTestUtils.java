package org.openur.remoting.resource.userstructure;

import java.util.UUID;

import org.openur.module.domain.userstructure.person.IPerson;

final class ResourceTestUtils
{
	static final String UUID_1 = UUID.randomUUID().toString();
	static final String NO_123 = "123";
	static final IPerson PERSON_1 = new MyPerson(UUID_1, NO_123);
}
