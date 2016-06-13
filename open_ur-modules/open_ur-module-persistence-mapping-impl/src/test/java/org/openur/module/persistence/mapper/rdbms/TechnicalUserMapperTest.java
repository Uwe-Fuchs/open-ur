package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authorization.AuthorizableTechUser;
import org.openur.module.persistence.rdbms.config.MapperSpringConfig;
import org.openur.module.persistence.rdbms.entity.PTechnicalUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { MapperSpringConfig.class })
@ActiveProfiles(profiles = { "testMappers" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TechnicalUserMapperTest
{
	@Inject
	private TechnicalUserMapper technicalUserMapper;
	
	@Test
	public void testMapFromDomainObject()
	{
		AuthorizableTechUser immutable = TestObjectContainer.TECH_USER_3;		
		PTechnicalUser persistable = technicalUserMapper.mapFromDomainObject(immutable);

		assertTrue(TechnicalUserMapper.domainObjectEqualsToEntity(immutable, persistable));
	}

	@Test
	public void testMapToImmutable()
	{
		PTechnicalUser persistable = technicalUserMapper.mapFromDomainObject(TestObjectContainer.TECH_USER_3);		
		AuthorizableTechUser immutable = technicalUserMapper.mapFromEntity(persistable);

		assertTrue(TechnicalUserMapper.domainObjectEqualsToEntity(immutable, persistable));
	}
}
