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

import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.openur.remoting.xchange.marshalling.json.SimpleAuthenticationInfoSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SimpleAuthenticationInfoProvider
	implements MessageBodyReader<SimpleAuthenticationInfo>, MessageBodyWriter<SimpleAuthenticationInfo>
{
	@Override
	public long getSize(SimpleAuthenticationInfo t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return isProvided(type);
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return isProvided(type);
	}
	
	protected boolean isProvided(Class<?> type)
	{
		return SimpleAuthenticationInfo.class.getSuperclass().isAssignableFrom(type);
	}

	@Override
	public SimpleAuthenticationInfo readFrom(Class<SimpleAuthenticationInfo> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
		throws IOException, WebApplicationException
	{
		String result = AbstractProvider.readFromInputStream(entityStream);
		Gson gson = buildGson();		
		
		return gson.fromJson(result, SimpleAuthenticationInfo.class);
	}

	@Override
	public void writeTo(SimpleAuthenticationInfo t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
		throws IOException, WebApplicationException
	{
		Gson gson = buildGson();		
		entityStream.write(gson.toJson(t).getBytes());		
	}
	
	private Gson buildGson()
	{
		return new GsonBuilder()
				.registerTypeAdapter(SimpleAuthenticationInfo.class, new SimpleAuthenticationInfoSerializer())
				.create();
	}
}
