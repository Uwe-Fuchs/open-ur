package org.openur.remoting.xchange.providers.json;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import org.openur.module.domain.userstructure.Address;

import com.google.gson.Gson;

public class AdressProvider
	implements MessageBodyWriter<Address>
{
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return Address.class.isAssignableFrom(type);
	}

	@Override
	public long getSize(Address address, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public void writeTo(Address address, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, 
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
		throws IOException, WebApplicationException
	{
		entityStream.write(new Gson().toJson(address).getBytes());
	}
}
