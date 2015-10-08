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

import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.remoting.xchange.marshalling.json.OpenURRoleSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RoleProvider
	extends AbstractProvider
	implements MessageBodyWriter<OpenURRole>,MessageBodyReader<OpenURRole>
{
	@Override
	public long getSize(OpenURRole t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return isProvided(type, OpenURRole.class);
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return isProvided(type, OpenURRole.class);
	}

	@Override
	public OpenURRole readFrom(Class<OpenURRole> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
		throws IOException, WebApplicationException
	{
		String result = readFromInputStream(entityStream);
		
		Gson gson = new GsonBuilder()
		    .registerTypeAdapter(OpenURRole.class, new OpenURRoleSerializer())    
		    .create();
		
		return gson.fromJson(result, OpenURRole.class);
	}

	@Override
	public void writeTo(OpenURRole t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, 
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
		throws IOException, WebApplicationException
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(OpenURRole.class, new OpenURRoleSerializer());
    Gson gson = gsonBuilder.create();
		
		entityStream.write(gson.toJson(t).getBytes());		
	}
}
