package org.openur.module.persistence.realm.rdbms;

import static org.junit.Assert.assertEquals;

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
import org.apache.shiro.util.ByteSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openur.module.persistence.rdbms.config.RealmSpringConfig;
import org.openur.module.persistence.rdbms.entity.PPerson;
import org.openur.module.persistence.rdbms.entity.PUserAccount;
import org.openur.module.persistence.rdbms.repository.UserAccountRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = { RealmSpringConfig.class })
@ActiveProfiles(profiles = { "testRealm" })
@RunWith(SpringJUnit4ClassRunner.class)
public class OpenUrRdbmsRealmTest
{
	private static final String USER_NAME = "testUser";
	private static final String PASSWORD = "secret";
	private static final String SALT_LITERAL = "myVERYSECRETBase64EncodedSalt";
	private static final int HASH_ITERATIONS = 1024;

	private AuthenticationToken authToken;
	private PPerson pPerson;
	private PUserAccount pUserAccount;
	private ByteSource salt;
	private String hashedSaltBase64;

	@Inject
	private UserAccountRepository userAccountRepository;

	@Inject
	private OpenUrRdbmsRealm realm;

	@Before
	public void setUp()
	{
		pPerson = new PPerson("123abc", "Name of Employee");
		authToken = new UsernamePasswordToken(USER_NAME, PASSWORD);
		hashedSaltBase64 = new Sha256Hash(SALT_LITERAL).toBase64();
		salt = ByteSource.Util.bytes(hashedSaltBase64);
	}

	@Test
	public void testDoGetAuthenticationInfo_PlainPW()
	{
		pUserAccount = new PUserAccount(pPerson, USER_NAME, PASSWORD);
		Mockito.when(userAccountRepository.findUserAccountByUserName(USER_NAME)).thenReturn(pUserAccount);

		realm.setCredentialsMatcher(new SimpleCredentialsMatcher());

		AuthenticationInfo authInfo = realm.getAuthenticationInfo(authToken);

		assertEquals(1, authInfo.getPrincipals().asList().size());
		String userName = authInfo.getPrincipals().getPrimaryPrincipal().toString();
		assertEquals(USER_NAME, userName);
		String passWord = new String((char[]) authInfo.getCredentials());
		assertEquals(PASSWORD, passWord);
	}

	@Test
	public void testDoGetAuthenticationInfo_HashedPW()
	{
		HashingPasswordService passwordService = createHashedPasswordService();		
		String encryptedPassword = passwordService.encryptPassword(PASSWORD);
		pUserAccount = new PUserAccount(pPerson, USER_NAME, encryptedPassword);
		Mockito.when(userAccountRepository.findUserAccountByUserName(USER_NAME)).thenReturn(pUserAccount);
		
		PasswordMatcher credentialsMatcher = new PasswordMatcher();
		credentialsMatcher.setPasswordService(passwordService);
		realm.setCredentialsMatcher(credentialsMatcher);

		AuthenticationInfo authInfo = realm.getAuthenticationInfo(authToken);

		assertEquals(1, authInfo.getPrincipals().asList().size());
		String userName = authInfo.getPrincipals().getPrimaryPrincipal().toString();
		assertEquals(USER_NAME, userName);
		String passWord = new String((char[]) authInfo.getCredentials());
		assertEquals(encryptedPassword, passWord);
	}

	@Test
	public void testDoGetAuthenticationInfo_HashedPW_Salted()
	{
		HashingPasswordService passwordService = createSaltedPasswordService();		
		String encryptedAndSaltedPassword = passwordService.encryptPassword(PASSWORD);
		pUserAccount = new PUserAccount(pPerson, USER_NAME, encryptedAndSaltedPassword);
		pUserAccount.setSalt(hashedSaltBase64);
		Mockito.when(userAccountRepository.findUserAccountByUserName(USER_NAME)).thenReturn(pUserAccount);
		
		PasswordMatcher credentialsMatcher = new PasswordMatcher();
		credentialsMatcher.setPasswordService(passwordService);
		realm.setCredentialsMatcher(credentialsMatcher);
		realm.setSaltStyle(SaltStyle.COLUMN);

		AuthenticationInfo authInfo = realm.getAuthenticationInfo(authToken);

		assertEquals(1, authInfo.getPrincipals().asList().size());
		String userName = authInfo.getPrincipals().getPrimaryPrincipal().toString();
		assertEquals(USER_NAME, userName);
		String passWord = new String((char[]) authInfo.getCredentials());
		assertEquals(encryptedAndSaltedPassword, passWord);
	}

	@Test
	public void testDoGetAuthenticationInfo_HashedPW_Salted_External()
	{		
		HashingPasswordService passwordService = createSaltedPasswordService();		
		String encryptedAndSaltedPassword = passwordService.encryptPassword(PASSWORD);
		pUserAccount = new PUserAccount(pPerson, USER_NAME, encryptedAndSaltedPassword);
		Mockito.when(userAccountRepository.findUserAccountByUserName(USER_NAME)).thenReturn(pUserAccount);
		
		PasswordMatcher credentialsMatcher = new PasswordMatcher();
		credentialsMatcher.setPasswordService(passwordService);
		realm.setCredentialsMatcher(credentialsMatcher);		
		realm.addSaltForUser(USER_NAME, hashedSaltBase64);
		realm.setSaltStyle(SaltStyle.EXTERNAL);	// is set automatically when adding external salt, only set here for demonstrating-purposes!

		AuthenticationInfo authInfo = realm.getAuthenticationInfo(authToken);

		assertEquals(1, authInfo.getPrincipals().asList().size());
		String userName = authInfo.getPrincipals().getPrimaryPrincipal().toString();
		assertEquals(USER_NAME, userName);
		String passWord = new String((char[]) authInfo.getCredentials());
		assertEquals(encryptedAndSaltedPassword, passWord);
	}

	@Test(expected=AuthenticationException.class)
	public void testDoGetAuthenticationInfo_Wrong_PW()
	{
		pUserAccount = new PUserAccount(pPerson, USER_NAME, PASSWORD);
		Mockito.when(userAccountRepository.findUserAccountByUserName(USER_NAME)).thenReturn(pUserAccount);

		realm.setCredentialsMatcher(new SimpleCredentialsMatcher());

		// authenticate user with wrong credentials:
		AuthenticationToken authToken = new UsernamePasswordToken(USER_NAME, "someWrongPassword");
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
