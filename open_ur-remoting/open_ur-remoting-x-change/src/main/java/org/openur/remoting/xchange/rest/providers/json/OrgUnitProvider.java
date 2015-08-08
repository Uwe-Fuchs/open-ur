package org.openur.remoting.xchange.providers.json;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;

import com.google.gson.Gson;

public class OrgUnitProvider
	implements MessageBodyWriter<AuthorizableOrgUnit>
{
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return AuthorizableOrgUnit.class.isAssignableFrom(type);
	}

	@Override
	public long getSize(AuthorizableOrgUnit t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public void writeTo(AuthorizableOrgUnit t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, 
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
		throws IOException, WebApplicationException
	{
		entityStream.write(new Gson().toJson(t).getBytes());		
	}	
}
