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
import org.openur.module.persistence.dao.ISecurityDomainDao;
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
	public static final String USER_NAME_1 = "userName_1";
	public static final String PASSWORD_1 = "password_1";
	public static final String SALT_BASE = "MyVerySecretPersonalSalt";	

	private ByteSource salt;
	private String hashedSaltBase64;

	@Inject
	private ISecurityDomainDao securityDao;

	@Inject
	private OpenUrRdbmsRealm realm;
	
	static
	{
		USERNAME_PW_TOKEN = new UsernamePasswordToken(USER_NAME_1, PASSWORD_1);
	}

	@Before
	public void setUp()
	{
		hashedSaltBase64 = new Sha256Hash(SALT_BASE).toBase64();
		salt = ByteSource.Util.bytes(hashedSaltBase64);
	}

	@Test
	public void testDoGetAuthenticationInfo_PlainPW()
	{
		UserAccount userAccount = new UserAccountBuilder(USER_NAME_1, PASSWORD_1)
				.identifier(TestObjectContainer.PERSON_UUID_1)
				.salt(SALT_BASE)
				.build();
		Mockito.when(securityDao.findUserAccountByUserName(USER_NAME_1)).thenReturn(userAccount);

		realm.setCredentialsMatcher(new SimpleCredentialsMatcher());

		UsernamePwAuthenticationInfo authInfo = realm.getUsernamePwAuthenticationInfo(USERNAME_PW_TOKEN);

		assertEquals(USER_NAME_1, authInfo.getUserName());
		assertEquals(PASSWORD_1, new String(authInfo.getPassWord()));
	}

	@Test
	public void testDoGetAuthenticationInfo_HashedPW()
	{
		HashingPasswordService passwordService = createHashedPasswordService();		
		String encryptedPassword = passwordService.encryptPassword(PASSWORD_1);
		UserAccount userAccount = new UserAccountBuilder(USER_NAME_1, encryptedPassword)
				.identifier(TestObjectContainer.PERSON_UUID_1)
				.build();
		Mockito.when(securityDao.findUserAccountByUserName(USER_NAME_1)).thenReturn(userAccount);
		
		PasswordMatcher credentialsMatcher = new PasswordMatcher();
		credentialsMatcher.setPasswordService(passwordService);
		realm.setCredentialsMatcher(credentialsMatcher);

		UsernamePwAuthenticationInfo authInfo = realm.getUsernamePwAuthenticationInfo(USERNAME_PW_TOKEN);
		
		assertEquals(USER_NAME_1, authInfo.getUserName());
		assertEquals(encryptedPassword, new String(authInfo.getPassWord()));
	}

	@Test
	public void testDoGetAuthenticationInfo_HashedPW_Salted()
	{
		HashingPasswordService passwordService = createSaltedPasswordService();		
		String encryptedAndSaltedPassword = passwordService.encryptPassword(PASSWORD_1);
		UserAccount userAccount = new UserAccountBuilder(USER_NAME_1, encryptedAndSaltedPassword)
				.identifier(TestObjectContainer.PERSON_UUID_1)
				.salt(hashedSaltBase64)
				.build();
		Mockito.when(securityDao.findUserAccountByUserName(USER_NAME_1)).thenReturn(userAccount);
		
		PasswordMatcher credentialsMatcher = new PasswordMatcher();
		credentialsMatcher.setPasswordService(passwordService);
		realm.setCredentialsMatcher(credentialsMatcher);
		realm.setSaltStyle(SaltStyle.COLUMN);

		UsernamePwAuthenticationInfo authInfo = realm.getUsernamePwAuthenticationInfo(USERNAME_PW_TOKEN);
		
		assertEquals(USER_NAME_1, authInfo.getUserName());
		assertEquals(encryptedAndSaltedPassword, new String(authInfo.getPassWord()));
	}

	@Test
	public void testDoGetAuthenticationInfo_HashedPW_Salted_External()
	{		
		HashingPasswordService passwordService = createSaltedPasswordService();		
		String encryptedAndSaltedPassword = passwordService.encryptPassword(PASSWORD_1);
		UserAccount userAccount = new UserAccountBuilder(USER_NAME_1, encryptedAndSaltedPassword)
				.identifier(TestObjectContainer.PERSON_UUID_1)
				.salt(hashedSaltBase64)
				.build();
		Mockito.when(securityDao.findUserAccountByUserName(USER_NAME_1)).thenReturn(userAccount);
		
		PasswordMatcher credentialsMatcher = new PasswordMatcher();
		credentialsMatcher.setPasswordService(passwordService);
		realm.setCredentialsMatcher(credentialsMatcher);		
		realm.addSaltForUser(USER_NAME_1, hashedSaltBase64);
		realm.setSaltStyle(SaltStyle.EXTERNAL);	// is set automatically when adding external salt, only set here for demonstrating-purposes!

		UsernamePwAuthenticationInfo authInfo = realm.getUsernamePwAuthenticationInfo(USERNAME_PW_TOKEN);
		
		assertEquals(USER_NAME_1, authInfo.getUserName());
		assertEquals(encryptedAndSaltedPassword, new String(authInfo.getPassWord()));
	}

	@Test(expected=AuthenticationException.class)
	public void testDoGetAuthenticationInfo_Wrong_PW()
	{
		UserAccount userAccount = new UserAccountBuilder(USER_NAME_1, PASSWORD_1)
				.identifier(TestObjectContainer.PERSON_UUID_1)
				.salt(SALT_BASE)
				.build();
		Mockito.when(securityDao.findUserAccountByUserName(USER_NAME_1)).thenReturn(userAccount);

		realm.setCredentialsMatcher(new SimpleCredentialsMatcher());

		// authenticate user with wrong credentials:
		AuthenticationToken authToken = new UsernamePasswordToken(USER_NAME_1, "someWrongPassword");
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
