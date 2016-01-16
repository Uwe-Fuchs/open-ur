package org.openur.module.persistence.realm.rdbms;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 
 * 
 * @author info@uwefuchs.com
 */
public class OpenUrRdbmsRealm
	extends AuthorizingRealm
{
	
		
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
		throws AuthenticationException
	{
    UsernamePasswordToken upToken = (UsernamePasswordToken) token;
    String username = upToken.getUsername();

    // Null username is invalid
    if (username == null) {
        throw new AccountException("Null usernames are not allowed by this realm.");
    }
    
		return null;
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
