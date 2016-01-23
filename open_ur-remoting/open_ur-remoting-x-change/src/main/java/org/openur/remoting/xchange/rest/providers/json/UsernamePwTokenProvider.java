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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UsernamePwTokenProvider
	implements MessageBodyWriter<UsernamePwTokenProvider>, MessageBodyReader<UsernamePwTokenProvider>
{
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return UsernamePwTokenProvider.class.isAssignableFrom(type);
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return UsernamePwTokenProvider.class.isAssignableFrom(type);
	}

	@Override
	public long getSize(UsernamePwTokenProvider t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public UsernamePwTokenProvider readFrom(Class<UsernamePwTokenProvider> type, Type genericType, Annotation[] annotations, MediaType mediaType, 
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
		throws IOException, WebApplicationException
	{
		String result = AbstractProvider.readFromInputStream(entityStream);
		
		return buildGson().fromJson(result, type);
	}

	@Override
	public void writeTo(UsernamePwTokenProvider t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, 
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
		throws IOException, WebApplicationException
	{
		Gson gson = buildGson();		
		entityStream.write(gson.toJson(t).getBytes());
	}
	
	private Gson buildGson()
	{
		// GsonBuilder gsonBuilder = new GsonBuilder();
		// gsonBuilder.registerTypeAdapter(UsernamePasswordToken.class, new UserNamePwSerializer());
		// return gsonBuilder;
		return new GsonBuilder().create();
	}
}
