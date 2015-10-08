package org.openur.remoting.xchange.rest.providers.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.remoting.xchange.marshalling.json.AuthorizableMemberSerializer;
import org.openur.remoting.xchange.marshalling.json.AuthorizableOrgUnitSerializer;
import org.openur.remoting.xchange.marshalling.json.OpenURRoleSerializer;
import org.openur.remoting.xchange.marshalling.json.PersonSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class IdentifiableEntitySetProvider<I extends IdentifiableEntityImpl>
	extends AbstractProvider
	implements MessageBodyWriter<Set<I>>, MessageBodyReader<Set<I>>
{
	@Override
	public long getSize(Set<I> userSet, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return isProvided(type, Set.class);
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return isProvided(type, Set.class);
	}

	@Override
	public Set<I> readFrom(Class<Set<I>> type, Type genericType, Annotation[] annotations, 	MediaType mediaType, 
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
		throws IOException, WebApplicationException
	{
		String result = readFromInputStream(entityStream);

    Gson gson = new GsonBuilder()
    		.registerTypeAdapter(Person.class, new PersonSerializer())
		    .registerTypeAdapter(AuthorizableOrgUnit.class, new AuthorizableOrgUnitSerializer())
		    .registerTypeAdapter(AuthorizableMember.class, new AuthorizableMemberSerializer())
		    .registerTypeAdapter(OpenURRole.class, new OpenURRoleSerializer())
		    .create();
    
    Set<I> resultSet = gson.fromJson(result, genericType);
    
    return resultSet;
	}

	@Override
	public void writeTo(Set<I> userSet, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, 
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
		throws IOException, WebApplicationException
	{
		Gson gson = new GsonBuilder()
		    .registerTypeAdapter(AuthorizableOrgUnit.class, new AuthorizableOrgUnitSerializer())
		    .registerTypeAdapter(AuthorizableMember.class, new AuthorizableMemberSerializer())
		    .registerTypeAdapter(OpenURRole.class, new OpenURRoleSerializer())
		    .create();
    
		entityStream.write(gson.toJson(userSet, type).getBytes());
	}	
}
