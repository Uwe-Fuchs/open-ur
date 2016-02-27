package org.openur.module.integration.security.shiro;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.HashingPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.jdbc.JdbcRealm.SaltStyle;
import org.apache.shiro.util.ByteSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.domain.testfixture.testobjects.TestObjectContainer;
import org.openur.module.domain.security.authentication.UserAccount;
import org.openur.module.domain.security.authentication.UserAccountBuilder;
import org.openur.module.persistence.dao.ISecurityDao;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { RealmSpringConfig.class })
@ActiveProfiles(profiles = { "testRealm" })
@RunWith(SpringJUnit4ClassRunner.class)
public class OpenUrRdbmsRealmTest
{
	private static final int HASH_ITERATIONS = 1024;
	private static final UsernamePasswordToken USERNAME_PW_TOKEN;

	private ByteSource salt;
	private String hashedSaltBase64;

	@Inject
	private ISecurityDao securityDao;

	@Inject
	private OpenUrRdbmsRealm realm;
	
	static
	{
		USERNAME_PW_TOKEN = new UsernamePasswordToken(TestObjectContainer.USER_NAME_1, TestObjectContainer.PASSWORD_1);		
	}

	@Before
	public void setUp()
	{
		hashedSaltBase64 = new Sha256Hash(TestObjectContainer.SALT_BASE).toBase64();
		salt = ByteSource.Util.bytes(hashedSaltBase64);
	}

	@Test
	public void testDoGetAuthenticationInfo_PlainPW()
	{
		Mockito.when(securityDao.findUserAccountByUserName(TestObjectContainer.USER_NAME_1)).thenReturn(TestObjectContainer.PERSON_1_ACCOUNT);

		realm.setCredentialsMatcher(new SimpleCredentialsMatcher());

		UsernamePwAuthenticationInfo authInfo = realm.getUsernamePwAuthenticationInfo(USERNAME_PW_TOKEN);

		assertEquals(TestObjectContainer.USER_NAME_1, authInfo.getUserName());
		assertEquals(TestObjectContainer.PASSWORD_1, new String(authInfo.getPassWord()));
	}

	@Test
	public void testDoGetAuthenticationInfo_HashedPW()
	{
		HashingPasswordService passwordService = createHashedPasswordService();		
		String encryptedPassword = passwordService.encryptPassword(TestObjectContainer.PASSWORD_1);
		UserAccount userAccount = new UserAccountBuilder(TestObjectContainer.USER_NAME_1, encryptedPassword)
				.identifier(TestObjectContainer.PERSON_UUID_1)
				.build();
		Mockito.when(securityDao.findUserAccountByUserName(TestObjectContainer.USER_NAME_1)).thenReturn(userAccount);
		
		PasswordMatcher credentialsMatcher = new PasswordMatcher();
		credentialsMatcher.setPasswordService(passwordService);
		realm.setCredentialsMatcher(credentialsMatcher);

		UsernamePwAuthenticationInfo authInfo = realm.getUsernamePwAuthenticationInfo(USERNAME_PW_TOKEN);
		
		assertEquals(TestObjectContainer.USER_NAME_1, authInfo.getUserName());
		assertEquals(encryptedPassword, new String(authInfo.getPassWord()));
	}

	@Test
	public void testDoGetAuthenticationInfo_HashedPW_Salted()
	{
		HashingPasswordService passwordService = createSaltedPasswordService();		
		String encryptedAndSaltedPassword = passwordService.encryptPassword(TestObjectContainer.PASSWORD_1);
		UserAccount userAccount = new UserAccountBuilder(TestObjectContainer.USER_NAME_1, encryptedAndSaltedPassword)
				.identifier(TestObjectContainer.PERSON_UUID_1)
				.salt(hashedSaltBase64)
				.build();
		Mockito.when(securityDao.findUserAccountByUserName(TestObjectContainer.USER_NAME_1)).thenReturn(userAccount);
		
		PasswordMatcher credentialsMatcher = new PasswordMatcher();
		credentialsMatcher.setPasswordService(passwordService);
		realm.setCredentialsMatcher(credentialsMatcher);
		realm.setSaltStyle(SaltStyle.COLUMN);

		UsernamePwAuthenticationInfo authInfo = realm.getUsernamePwAuthenticationInfo(USERNAME_PW_TOKEN);
		
		assertEquals(TestObjectContainer.USER_NAME_1, authInfo.getUserName());
		assertEquals(encryptedAndSaltedPassword, new String(authInfo.getPassWord()));
	}

	@Test
	public void testDoGetAuthenticationInfo_HashedPW_Salted_External()
	{		
		HashingPasswordService passwordService = createSaltedPasswordService();		
		String encryptedAndSaltedPassword = passwordService.encryptPassword(TestObjectContainer.PASSWORD_1);
		UserAccount userAccount = new UserAccountBuilder(TestObjectContainer.USER_NAME_1, encryptedAndSaltedPassword)
				.identifier(TestObjectContainer.PERSON_UUID_1)
				.salt(hashedSaltBase64)
				.build();
		Mockito.when(securityDao.findUserAccountByUserName(TestObjectContainer.USER_NAME_1)).thenReturn(userAccount);
		
		PasswordMatcher credentialsMatcher = new PasswordMatcher();
		credentialsMatcher.setPasswordService(passwordService);
		realm.setCredentialsMatcher(credentialsMatcher);		
		realm.addSaltForUser(TestObjectContainer.USER_NAME_1, hashedSaltBase64);
		realm.setSaltStyle(SaltStyle.EXTERNAL);	// is set automatically when adding external salt, only set here for demonstrating-purposes!

		UsernamePwAuthenticationInfo authInfo = realm.getUsernamePwAuthenticationInfo(USERNAME_PW_TOKEN);
		
		assertEquals(TestObjectContainer.USER_NAME_1, authInfo.getUserName());
		assertEquals(encryptedAndSaltedPassword, new String(authInfo.getPassWord()));
	}

	@Test(expected=AuthenticationException.class)
	public void testDoGetAuthenticationInfo_Wrong_PW()
	{
		Mockito.when(securityDao.findUserAccountByUserName(TestObjectContainer.USER_NAME_1)).thenReturn(TestObjectContainer.PERSON_1_ACCOUNT);

		realm.setCredentialsMatcher(new SimpleCredentialsMatcher());

		// authenticate user with wrong credentials:
		AuthenticationToken authToken = new UsernamePasswordToken(TestObjectContainer.USER_NAME_1, "someWrongPassword");
		realm.getAuthenticationInfo(authToken);
	}
	
	private HashingPasswordService createHashedPasswordService()
	{
		DefaultHashService hashService = new DefaultHashService();
		hashService.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
		DefaultPasswordService passwordService = new DefaultPasswordService();
		passwordService.setHashService(hashService);
		
		return passwordService;
	}
	
	private HashingPasswordService createSaltedPasswordService()
	{
		DefaultHashService hashService = new DefaultHashService();
		hashService.setHashIterations(HASH_ITERATIONS);
		hashService.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
		hashService.setPrivateSalt(salt);
		hashService.setGeneratePublicSalt(true);
		
		DefaultPasswordService passwordService = new DefaultPasswordService();
		passwordService.setHashService(hashService);
		
		return passwordService;
	}
}
