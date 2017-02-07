package org.openur.module.integration.security.shiro;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.jdbc.JdbcRealm.SaltStyle;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.openur.module.domain.security.authentication.IUserAccount;
import org.openur.module.persistence.dao.ISecurityDomainDao;

/**
 * OpenUR-specific, Username-PW-based implementation of {@link AuthorizingRealm}-class.
 * 
 * @author info@uwefuchs.com
 */
public class OpenUrRdbmsRealm
	extends AuthorizingRealm
{
	private SaltStyle saltStyle = SaltStyle.NO_SALT;
	private Map<String, String> userSalts = new ConcurrentHashMap<>();

	@Inject
	private ISecurityDomainDao securityDao;
	
	public UsernamePwAuthenticationInfo getUsernamePwAuthenticationInfo(UsernamePasswordToken token)
		throws AuthenticationException
	{
		return (UsernamePwAuthenticationInfo) getAuthenticationInfo(token);
	}

	@Override
	protected UsernamePwAuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
		throws AuthenticationException
	{
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();

		// Null username is invalid
		if (username == null)
		{
			throw new AccountException("Null usernames are not allowed by this realm.");
		}

		IUserAccount userAccount = securityDao.findUserAccountByUserName(username);

		if (userAccount == null || StringUtils.isBlank(userAccount.getPassWord()))
		{
			throw new UnknownAccountException("No account found for user [" + username + "]");
		}

		UsernamePwAuthenticationInfo info = new UsernamePwAuthenticationInfo(
				username, userAccount.getPassWord().toCharArray(), getName(), userAccount.getIdentifier());
		
		String salt = null;
		
		if (saltStyle == SaltStyle.COLUMN)
		{
			salt = userAccount.getSalt();
		} else 	if (saltStyle == SaltStyle.EXTERNAL)
		{
			salt = getSaltForUser(username);
		}
		
		if (salt != null)
		{
			info.setCredentialsSalt(ByteSource.Util.bytes(salt));
		}

    return info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void setSaltStyle(SaltStyle saltStyle)
	{
		this.saltStyle = saltStyle;
	}

	private String getSaltForUser(String username)
	{
		return this.userSalts.get(username);
	}
	
	public void addSaltForUser(String username, String salt)
	{
		this.userSalts.put(username, salt);
		this.saltStyle = SaltStyle.EXTERNAL;
	}
}
