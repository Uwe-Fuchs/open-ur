package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authentication.UserAccount;
import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.persistence.rdbms.config.MapperSpringConfig;
import org.openur.module.persistence.rdbms.entity.PUserAccount;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { MapperSpringConfig.class })
@ActiveProfiles(profiles = { "testMappers" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserAccountMapperTest
{
	@Inject
	UserAccountMapper userAccountMapper;
	
	@Test
	public void testMapFromDomainObject()
	{
		UserAccount domainObject = TestObjectContainer.PERSON_1_ACCOUNT;
		UserStructureBase baseDO = TestObjectContainer.PERSON_1;
		
		PUserAccount entity = userAccountMapper.mapFromDomainObject(domainObject, baseDO);
		
		assertTrue(UserAccountMapper.domainObjectEqualsToEntity(domainObject, entity));
		
		domainObject = TestObjectContainer.TECH_USER_2_ACCOUNT;
		baseDO = TestObjectContainer.TECH_USER_2;
		
		entity = userAccountMapper.mapFromDomainObject(domainObject, baseDO);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMapFromDomainObjectWrongType()
	{
		UserAccount domainObject = TestObjectContainer.PERSON_1_ACCOUNT;
		UserStructureBase baseDO = TestObjectContainer.ORG_UNIT_A;
		userAccountMapper.mapFromDomainObject(domainObject, baseDO);
	}

	@Test
	public void testMapFromEntityPUserAccount()
	{
		PUserAccount entity = userAccountMapper.mapFromDomainObject(TestObjectContainer.PERSON_1_ACCOUNT, TestObjectContainer.PERSON_1);
		UserAccount domainObject = userAccountMapper.mapFromEntity(entity);
		
		assertTrue(UserAccountMapper.domainObjectEqualsToEntity(domainObject, entity));
		
		entity = userAccountMapper.mapFromDomainObject(TestObjectContainer.TECH_USER_2_ACCOUNT, TestObjectContainer.TECH_USER_2);
		domainObject = userAccountMapper.mapFromEntity(entity);
		
		assertTrue(UserAccountMapper.domainObjectEqualsToEntity(domainObject, entity));
	}
}
