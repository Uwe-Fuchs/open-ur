package org.openur.module.integration.security.shiro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.HashingPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.jdbc.JdbcRealm.SaltStyle;
import org.apache.shiro.subject.PrincipalCollection;
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
	private static final String SALT_LITERAL = "myVERYSECRETBase64EncodedSalt";
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
		hashedSaltBase64 = new Sha256Hash(SALT_LITERAL).toBase64();
		salt = ByteSource.Util.bytes(hashedSaltBase64);
	}

	@Test
	public void testDoGetAuthenticationInfo_PlainPW()
	{
		Mockito.when(securityDao.findUserAccountByUserName(TestObjectContainer.USER_NAME_1)).thenReturn(TestObjectContainer.PERSON_1_ACCOUNT);

		realm.setCredentialsMatcher(new SimpleCredentialsMatcher());

		AuthenticationInfo authInfo = realm.getAuthenticationInfo(USERNAME_PW_TOKEN);

		PrincipalCollection principalCollection = authInfo.getPrincipals();
		List<?> principals = principalCollection.asList();
		assertEquals(1, principals.size());
		assertTrue(principals.contains(TestObjectContainer.USER_NAME_1));
		String userName = principalCollection.getPrimaryPrincipal().toString();
		assertEquals(TestObjectContainer.USER_NAME_1, userName);
		String passWord = new String((char[]) authInfo.getCredentials());
		assertEquals(TestObjectContainer.PASSWORD_1, passWord);
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

		AuthenticationInfo authInfo = realm.getAuthenticationInfo(USERNAME_PW_TOKEN);

		PrincipalCollection principalCollection = authInfo.getPrincipals();
		List<?> principals = principalCollection.asList();
		assertEquals(1, principals.size());
		assertTrue(principals.contains(TestObjectContainer.USER_NAME_1));
		String userName = authInfo.getPrincipals().getPrimaryPrincipal().toString();
		assertEquals(TestObjectContainer.USER_NAME_1, userName);
		String passWord = new String((char[]) authInfo.getCredentials());
		assertEquals(encryptedPassword, passWord);
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

		AuthenticationInfo authInfo = realm.getAuthenticationInfo(USERNAME_PW_TOKEN);

		PrincipalCollection principalCollection = authInfo.getPrincipals();
		List<?> principals = principalCollection.asList();
		assertEquals(1, principals.size());
		assertTrue(principals.contains(TestObjectContainer.USER_NAME_1));
		String userName = authInfo.getPrincipals().getPrimaryPrincipal().toString();
		assertEquals(TestObjectContainer.USER_NAME_1, userName);
		String passWord = new String((char[]) authInfo.getCredentials());
		assertEquals(encryptedAndSaltedPassword, passWord);
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

		AuthenticationInfo authInfo = realm.getAuthenticationInfo(USERNAME_PW_TOKEN);

		assertEquals(1, authInfo.getPrincipals().asList().size());
		String userName = authInfo.getPrincipals().getPrimaryPrincipal().toString();
		assertEquals(TestObjectContainer.USER_NAME_1, userName);
		String passWord = new String((char[]) authInfo.getCredentials());
		assertEquals(encryptedAndSaltedPassword, passWord);
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

//	@Test
//	public void testDoGetAuthenticationInfo_HashedPW()
//	{
//		String hashedPasswordBase64 = new Sha256Hash(PASSWORD).toBase64();
//		pUserAccount = new PUserAccount(pPerson, USER_NAME, hashedPasswordBase64);
//		Mockito.when(userAccountRepository.findUserAccountByUserName(USER_NAME)).thenReturn(pUserAccount);
//
//		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//		credentialsMatcher.setHashAlgorithmName("SHA-256");
//		credentialsMatcher.setStoredCredentialsHexEncoded(false);
//		realm.setCredentialsMatcher(credentialsMatcher);
//
//		AuthenticationInfo authInfo = realm.getAuthenticationInfo(authToken);
//
//		assertEquals(1, authInfo.getPrincipals().asList().size());
//		String userName = authInfo.getPrincipals().getPrimaryPrincipal().toString();
//		assertEquals(USER_NAME, userName);
//		String passWord = new String((char[]) authInfo.getCredentials());
//		assertEquals(hashedPasswordBase64, passWord);
//	}

//	@Test
//	public void testDoGetAuthenticationInfo_HashedPW_Salted()
//	{
//		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
//		ByteSource salt = rng.nextBytes();
//		String saltStr = salt.toString();
//		
//		saltStr = "myVERYSECRETBase64EncodedSalt";		
//		salt = ByteSource.Util.bytes(saltStr);
//		
//		String hashedPasswordBase64 = new Sha256Hash(PASSWORD, salt, 1024).toBase64();
//		pUserAccount = new PUserAccount(pPerson, USER_NAME, hashedPasswordBase64);
//		pUserAccount.setSalt(saltStr);
//		Mockito.when(userAccountRepository.findUserAccountByUserName(USER_NAME)).thenReturn(pUserAccount);
//
//		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//		credentialsMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
//		credentialsMatcher.setStoredCredentialsHexEncoded(false);
//		credentialsMatcher.setHashIterations(1024);
//		realm.setCredentialsMatcher(credentialsMatcher);
//		realm.setSaltStyle(SaltStyle.COLUMN);
//
//		AuthenticationInfo authInfo = realm.getAuthenticationInfo(authToken);
//
//		assertEquals(1, authInfo.getPrincipals().asList().size());
//		String userName = authInfo.getPrincipals().getPrimaryPrincipal().toString();
//		assertEquals(USER_NAME, userName);
//		String passWord = new String((char[]) authInfo.getCredentials());
//		assertEquals(hashedPasswordBase64, passWord);
//	}

	// @Test
	// public void testDoGetAuthorizationInfoPrincipalCollection()
	// {
	// fail("Not yet implemented");
	// }
	
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
