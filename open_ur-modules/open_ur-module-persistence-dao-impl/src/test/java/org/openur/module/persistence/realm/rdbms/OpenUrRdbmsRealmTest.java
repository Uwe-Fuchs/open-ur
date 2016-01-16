package org.openur.module.persistence.realm.rdbms;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
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
	
	private AuthenticationToken authToken;
	private PPerson pPerson;
	private PUserAccount pUserAccount;
	
	@Inject
	private UserAccountRepository userAccountRepository;
	
	@Inject
	private OpenUrRdbmsRealm realm;
	
	@Before
	public void setUp()
	{
		pPerson = new PPerson("123abc", "Name of Employee");
		authToken = new UsernamePasswordToken(USER_NAME, PASSWORD);	
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
		String hashedPasswordBase64 = new Sha256Hash(PASSWORD).toBase64();
		pUserAccount = new PUserAccount(pPerson, USER_NAME, hashedPasswordBase64);	
		Mockito.when(userAccountRepository.findUserAccountByUserName(USER_NAME)).thenReturn(pUserAccount);
		
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("SHA-256");
		credentialsMatcher.setStoredCredentialsHexEncoded(false);
		realm.setCredentialsMatcher(credentialsMatcher);
		
		AuthenticationInfo authInfo = realm.getAuthenticationInfo(authToken);
		
		assertEquals(1, authInfo.getPrincipals().asList().size());
		String userName = authInfo.getPrincipals().getPrimaryPrincipal().toString();
		assertEquals(USER_NAME, userName);
		String passWord = new String((char[]) authInfo.getCredentials());
		assertEquals(hashedPasswordBase64, passWord);
	}

	@Test
	public void testDoGetAuthenticationInfo_HashedPW_Salted()
	{
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		ByteSource salt = rng.nextBytes();
		String saltStr = salt.toString();
		salt = ByteSource.Util.bytes(saltStr);
		String hashedPasswordBase64 = new Sha256Hash(PASSWORD, salt, 1024).toBase64();
		pUserAccount = new PUserAccount(pPerson, USER_NAME, hashedPasswordBase64);
		pUserAccount.setSalt(saltStr);
		Mockito.when(userAccountRepository.findUserAccountByUserName(USER_NAME)).thenReturn(pUserAccount);
		
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("SHA-256");
		credentialsMatcher.setStoredCredentialsHexEncoded(false);
		credentialsMatcher.setHashIterations(1024);
		realm.setCredentialsMatcher(credentialsMatcher);
		realm.setSaltStyle(SaltStyle.COLUMN);
		
		AuthenticationInfo authInfo = realm.getAuthenticationInfo(authToken);
		
		assertEquals(1, authInfo.getPrincipals().asList().size());
		String userName = authInfo.getPrincipals().getPrimaryPrincipal().toString();
		assertEquals(USER_NAME, userName);
		String passWord = new String((char[]) authInfo.getCredentials());
		assertEquals(hashedPasswordBase64, passWord);
	}

//	@Test
//	public void testDoGetAuthorizationInfoPrincipalCollection()
//	{
//		fail("Not yet implemented");
//	}
}
