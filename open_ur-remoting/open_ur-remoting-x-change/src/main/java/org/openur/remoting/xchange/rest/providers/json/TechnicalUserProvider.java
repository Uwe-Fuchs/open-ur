package org.openur.remoting.xchange.rest.providers.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;

import com.google.gson.Gson;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class TechnicalUserProvider
	extends AbstractProvider
	implements MessageBodyWriter<TechnicalUser>, MessageBodyReader<TechnicalUser>
{
	@Override
	public long getSize(TechnicalUser techUser, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return isProvided(type, TechnicalUser.class);
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return isProvided(type, TechnicalUser.class);
	}

	@Override
	public TechnicalUser readFrom(Class<TechnicalUser> type, Type genericType, Annotation[] annotations, MediaType mediaType, 
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
		throws IOException, WebApplicationException
	{
		String result = readFromInputStream(entityStream);		
		TechnicalUser tu = new Gson().fromJson(result, TechnicalUser.class);
		
		return tu;
	}

	@Override
	public void writeTo(TechnicalUser techUser, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, 
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
		throws IOException, WebApplicationException
	{
		entityStream.write(new Gson().toJson(techUser).getBytes());
	}
}
