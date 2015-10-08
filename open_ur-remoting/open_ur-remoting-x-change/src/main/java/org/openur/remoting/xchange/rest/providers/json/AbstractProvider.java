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

import org.openur.module.domain.IdentifiableEntityImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class AbstractProvider<I extends IdentifiableEntityImpl>
	implements MessageBodyWriter<I>, MessageBodyReader<I>
{	
	public static String readFromInputStream(InputStream entityStream)
		throws IOException
	{
		StringBuilder result = new StringBuilder();

		while(true)
		{
			int i = entityStream.read();
			
			if (i < 0)
			{
				break;
			} else
			{
				result.append((char) i);
			}
		}
		
		entityStream.close();
		
		return result.toString();
	}
	
	@Override
	public long getSize(I t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
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

	@Override
	public I readFrom(Class<I> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, 
			InputStream entityStream)
		throws IOException, WebApplicationException
	{
		String result = readFromInputStream(entityStream);	
		
		return buildGson().fromJson(result, type);
	}

	@Override
	public void writeTo(I t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, 
			OutputStream entityStream)
		throws IOException, WebApplicationException
	{
		Gson gson = buildGson();		
		entityStream.write(gson.toJson(t).getBytes());
	}
	
	protected Gson buildGson()
	{
		return createGsonBuilder().create();
	}
	
	protected GsonBuilder createGsonBuilder()
	{
		return new GsonBuilder();
	}
	
	protected abstract boolean isProvided(Class<?> type);
}
