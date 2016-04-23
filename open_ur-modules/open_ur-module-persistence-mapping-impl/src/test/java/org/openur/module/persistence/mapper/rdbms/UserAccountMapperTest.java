package org.openur.module.persistence.mapper.rdbms;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authentication.UserAccount;
import org.openur.module.domain.security.authentication.UserAccountBuilder;
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
	public static final String SALT_BASE = "MyVerySecretPersonalSalt";	
	public static final String PERSON_UUID_1 = UUID.randomUUID().toString();
	public static final String USER_NAME_1 = "userName_1";
	public static final String PASSWORD_1 = "password_1";
	public static final UserAccount PERSON_1_ACCOUNT;
	public static final String TECH_USER_UUID_2 = UUID.randomUUID().toString();
	public static final String USER_NAME_2 = "userName_2";
	public static final String PASSWORD_2 = "password_2";	
	public static final UserAccount TECH_USER_2_ACCOUNT;
	
	@Inject
	UserAccountMapper userAccountMapper;
	
	static
	{
		PERSON_1_ACCOUNT = new UserAccountBuilder(USER_NAME_1, PASSWORD_1)
				.identifier(TestObjectContainer.PERSON_UUID_1)
				.salt(SALT_BASE)
				.build();

		TECH_USER_2_ACCOUNT = new UserAccountBuilder(USER_NAME_2, PASSWORD_2)
				.identifier(TECH_USER_UUID_2)
				.salt(SALT_BASE)
				.build();
	}
	
	@Test
	public void testMapFromDomainObject()
	{
		UserAccount domainObject = PERSON_1_ACCOUNT;
		UserStructureBase baseDO = TestObjectContainer.PERSON_1;
		
		PUserAccount entity = userAccountMapper.mapFromDomainObject(domainObject, baseDO);
		
		assertTrue(UserAccountMapper.domainObjectEqualsToEntity(domainObject, entity));
		
		domainObject = TECH_USER_2_ACCOUNT;
		baseDO = TestObjectContainer.TECH_USER_2;
		
		entity = userAccountMapper.mapFromDomainObject(domainObject, baseDO);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMapFromDomainObjectWrongType()
	{
		UserAccount domainObject = PERSON_1_ACCOUNT;
		UserStructureBase baseDO = TestObjectContainer.ORG_UNIT_A;
		userAccountMapper.mapFromDomainObject(domainObject, baseDO);
	}

	@Test
	public void testMapFromEntityPUserAccount()
	{
		PUserAccount entity = userAccountMapper.mapFromDomainObject(PERSON_1_ACCOUNT, TestObjectContainer.PERSON_1);
		UserAccount domainObject = userAccountMapper.mapFromEntity(entity);
		
		assertTrue(UserAccountMapper.domainObjectEqualsToEntity(domainObject, entity));
		
		entity = userAccountMapper.mapFromDomainObject(TECH_USER_2_ACCOUNT, TestObjectContainer.TECH_USER_2);
		domainObject = userAccountMapper.mapFromEntity(entity);
		
		assertTrue(UserAccountMapper.domainObjectEqualsToEntity(domainObject, entity));
	}
}
