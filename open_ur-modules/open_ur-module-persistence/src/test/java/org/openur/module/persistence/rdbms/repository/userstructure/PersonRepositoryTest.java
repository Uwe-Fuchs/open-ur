package org.openur.module.persistence.rdbms.repository.userstructure;

import static org.junit.Assert.*;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.module.persistence.rdbms.config.RepositoryConfig;
import org.openur.module.persistence.rdbms.repository.userstructure.PersonRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { RepositoryConfig.class })
@Transactional
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonRepositoryTest
{
	@Inject
	private PersonRepository personRepository;

	@Test
	public void testRegisterPerson()
	{
		assertTrue(true);
	}
}
