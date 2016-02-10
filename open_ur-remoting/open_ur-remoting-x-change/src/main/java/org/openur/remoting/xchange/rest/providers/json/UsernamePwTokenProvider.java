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

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UsernamePwTokenProvider
	implements MessageBodyWriter<AuthenticationToken>, MessageBodyReader<AuthenticationToken>
{
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return UsernamePasswordToken.class.isAssignableFrom(type);
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return UsernamePasswordToken.class.isAssignableFrom(type);
	}

	@Override
	public long getSize(AuthenticationToken token, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public UsernamePasswordToken readFrom(Class<AuthenticationToken> type, Type genericType, Annotation[] annotations, MediaType mediaType, 
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
		throws IOException, WebApplicationException
	{
		String result = AbstractProvider.readFromInputStream(entityStream);
		
		return buildGson().fromJson(result, UsernamePasswordToken.class);
	}

	@Override
	public void writeTo(AuthenticationToken token, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, 
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
		throws IOException, WebApplicationException
	{
		Gson gson = buildGson();		
		entityStream.write(gson.toJson(token).getBytes());
	}
	
	private Gson buildGson()
	{
		// GsonBuilder gsonBuilder = new GsonBuilder();
		// gsonBuilder.registerTypeAdapter(UsernamePasswordToken.class, new UserNamePwSerializer());
		// return gsonBuilder;
		return new GsonBuilder().create();
	}
}
