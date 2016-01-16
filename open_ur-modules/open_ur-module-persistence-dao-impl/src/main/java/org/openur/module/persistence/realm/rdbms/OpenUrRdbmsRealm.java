package org.openur.module.persistence.realm.rdbms;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.jdbc.JdbcRealm.SaltStyle;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.openur.module.persistence.rdbms.entity.PUserAccount;
import org.openur.module.persistence.rdbms.repository.UserAccountRepository;

/**
 * 
 * 
 * @author info@uwefuchs.com
 */
public class OpenUrRdbmsRealm
	extends AuthorizingRealm
{
	private SaltStyle saltStyle = SaltStyle.NO_SALT;

	@Inject
	private UserAccountRepository userAccountRepository;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
		throws AuthenticationException
	{
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();

		// Null username is invalid
		if (username == null)
		{
			throw new AccountException("Null usernames are not allowed by this realm.");
		}

		PUserAccount userAccount = userAccountRepository.findUserAccountByUserName(username);

		if (userAccount == null || StringUtils.isBlank(userAccount.getPassWord()))
		{
			throw new UnknownAccountException("No account found for user [" + username + "]");
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, userAccount.getPassWord().toCharArray(), getName());
		
		String salt = null;
		
		if (saltStyle == SaltStyle.COLUMN)
		{
			salt = userAccount.getSalt();
		} else 	if (saltStyle == SaltStyle.EXTERNAL)
		{
			salt = getSaltForUser(username);
			userAccount.setSalt(salt);
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
		return username;
	}
}
