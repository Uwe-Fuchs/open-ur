package org.openur.remoting.xchange.rest.providers.json;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import org.openur.module.domain.security.authorization.OpenURPermission;

import com.google.gson.Gson;

public class PermissionProvider
	implements MessageBodyWriter<OpenURPermission>
{
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return OpenURPermission.class.isAssignableFrom(type);
	}

	@Override
	public long getSize(OpenURPermission t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public void writeTo(OpenURPermission t, Class<?> type, Type genericType, Annotation[] annotations, 
			MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
		throws IOException, WebApplicationException
	{
		entityStream.write(new Gson().toJson(t).getBytes());		
	}
}
