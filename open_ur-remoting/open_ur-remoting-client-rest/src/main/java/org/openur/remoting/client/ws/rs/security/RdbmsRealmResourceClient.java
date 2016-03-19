package org.openur.remoting.client.ws.rs.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.Realm;
import org.openur.remoting.resource.client.AbstractResourceClient;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwAuthenticationInfoProvider;
import org.openur.remoting.xchange.rest.providers.json.UsernamePwTokenProvider;

public class RdbmsRealmResourceClient
	extends AbstractResourceClient
	implements Realm
{
	public RdbmsRealmResourceClient(String baseUrl)
	{
		super(baseUrl, UsernamePwTokenProvider.class, UsernamePwAuthenticationInfoProvider.class);
	}

	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(AuthenticationToken token)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
		throws AuthenticationException
	{
		// TODO Auto-generated method stub
		return null;
	}
}
