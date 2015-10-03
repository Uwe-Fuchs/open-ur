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

import org.openur.module.domain.security.authorization.OpenURPermission;

import com.google.gson.Gson;

public class PermissionProvider
	extends AbstractProvider
	implements MessageBodyWriter<OpenURPermission>, MessageBodyReader<OpenURPermission>
{
	@Override
	public long getSize(OpenURPermission t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return isProvided(type, OpenURPermission.class);
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return isProvided(type, OpenURPermission.class);
	}

	@Override
	public OpenURPermission readFrom(Class<OpenURPermission> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
		throws IOException, WebApplicationException
	{
		String result = readFromInputStream(entityStream);
		
		return new Gson().fromJson(result, OpenURPermission.class);
	}

	@Override
	public void writeTo(OpenURPermission t, Class<?> type, Type genericType, Annotation[] annotations, 
			MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
		throws IOException, WebApplicationException
	{
		entityStream.write(new Gson().toJson(t).getBytes());		
	}
}
