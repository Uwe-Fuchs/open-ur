package org.openur.remoting.xchange.rest.providers.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.apache.shiro.authc.AuthenticationInfo;
import org.openur.module.integration.security.shiro.UsernamePwAuthenticationInfo;
import org.openur.remoting.xchange.marshalling.json.UsernamePwAuthenticationInfoSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UsernamePwAuthenticationInfoProvider
	implements MessageBodyReader<UsernamePwAuthenticationInfo>, MessageBodyWriter<UsernamePwAuthenticationInfo>
{
	@Override
	public long getSize(UsernamePwAuthenticationInfo t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return AuthenticationInfo.class.isAssignableFrom(type);
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return UsernamePwAuthenticationInfo.class.isAssignableFrom(type);
	}

	@Override
	public UsernamePwAuthenticationInfo readFrom(Class<UsernamePwAuthenticationInfo> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
		throws IOException, WebApplicationException
	{
		String result = AbstractProvider.readFromInputStream(entityStream);
		Gson gson = buildGson();		
		
		return gson.fromJson(result, UsernamePwAuthenticationInfo.class);
	}

	@Override
	public void writeTo(UsernamePwAuthenticationInfo t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
		throws IOException, WebApplicationException
	{
		Gson gson = buildGson();		
		entityStream.write(gson.toJson(t).getBytes());		
	}
	
	protected Gson buildGson()
	{
		return new GsonBuilder()
				.registerTypeAdapter(UsernamePwAuthenticationInfo.class, new UsernamePwAuthenticationInfoSerializer())
				.create();
	}
}
