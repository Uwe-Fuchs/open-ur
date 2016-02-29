package org.openur.remoting.xchange.rest.providers.json;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.shiro.authc.AuthenticationInfo;
import org.openur.module.integration.security.shiro.UsernamePwAuthenticationInfo;
import org.openur.remoting.xchange.marshalling.json.UsernamePwAuthenticationInfoSerializer;

import com.google.gson.GsonBuilder;

public class UsernamePwAuthenticationInfoProvider
	extends AbstractProvider<UsernamePwAuthenticationInfo>
{
	@Override
	protected boolean isProvided(Class<?> type)
	{
		return AuthenticationInfo.class.isAssignableFrom(type);
	}

	@Override
	public UsernamePwAuthenticationInfo readFrom(Class<UsernamePwAuthenticationInfo> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
		throws IOException, WebApplicationException
	{
		String result = readFromInputStream(entityStream);

		return buildGson().fromJson(result, UsernamePwAuthenticationInfo.class);
	}

	@Override
	protected GsonBuilder createGsonBuilder()
	{
		return new GsonBuilder()
				.registerTypeAdapter(UsernamePwAuthenticationInfo.class, new UsernamePwAuthenticationInfoSerializer());
	}
}
