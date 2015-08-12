package org.openur.remoting.xchange.rest.providers.json;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.openur.module.domain.userstructure.UserStructureBase;

import com.google.gson.Gson;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class UserSetProvider
	implements MessageBodyWriter<Set<UserStructureBase>>
{
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return true;
	}

	@Override
	public long getSize(Set<UserStructureBase> userSet, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public void writeTo(Set<UserStructureBase> userSet, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, 
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
		throws IOException, WebApplicationException
	{
		entityStream.write(new Gson().toJson(userSet, Set.class).getBytes());
	}	
}