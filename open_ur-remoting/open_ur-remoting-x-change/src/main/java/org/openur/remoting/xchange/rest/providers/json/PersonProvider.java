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

import org.openur.module.domain.userstructure.person.Person;
import org.openur.remoting.xchange.marshalling.json.PersonSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class PersonProvider
	extends AbstractProvider
	implements MessageBodyWriter<Person>, MessageBodyReader<Person>
{
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return isProvided(type, Person.class);
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return isProvided(type, Person.class);
	}

	@Override
	public long getSize(Person person, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public void writeTo(Person person, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, 
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
		throws IOException, WebApplicationException
	{
		entityStream.write(new Gson().toJson(person).getBytes());
	}

	@Override
	public Person readFrom(Class<Person> type, Type genericType, Annotation[] annotations, MediaType mediaType, 
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
		throws IOException, WebApplicationException
	{
		String result = readFromInputStream(entityStream);

		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Person.class, new PersonSerializer())
				.create();

		return gson.fromJson(result, type);
	}
}
