package org.openur.remoting.xchange.rest.providers.json;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.openur.module.domain.userstructure.person.Person;
import org.openur.remoting.xchange.marshalling.json.PersonSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class PersonProvider
	extends AbstractProvider<Person>
{
	@Override
	protected boolean isProvided(Class<?> type)
	{
		return Person.class.isAssignableFrom(type);
	}

	@Override
	public void writeTo(Person person, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, 
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
		throws IOException, WebApplicationException
	{
		Gson gson = new Gson();	
		entityStream.write(gson.toJson(person).getBytes());
	}

	@Override
	protected GsonBuilder createGsonBuilder()
	{
		return new GsonBuilder()
				.registerTypeAdapter(Person.class, new PersonSerializer());
	}
}
