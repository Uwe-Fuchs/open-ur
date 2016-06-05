package org.openur.remoting.xchange.rest.providers.json;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.openur.module.domain.IdentifiableEntityImpl;
import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.remoting.xchange.marshalling.json.AuthorizableMemberSerializer;
import org.openur.remoting.xchange.marshalling.json.AuthorizableOrgUnitSerializer;
import org.openur.remoting.xchange.marshalling.json.OpenURRoleSerializer;
import org.openur.remoting.xchange.marshalling.json.PersonSerializer;
import org.openur.remoting.xchange.marshalling.json.TechnicalUserSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class IdentifiableEntitySetProvider<I extends IdentifiableEntityImpl>
	extends AbstractProvider<Set<I>>
{
	@Override
	protected boolean isProvided(Class<?> type)
	{
		return Set.class.isAssignableFrom(type);
	}

	@Override
	public Set<I> readFrom(Class<Set<I>> type, Type genericType, Annotation[] annotations, 	MediaType mediaType, 
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
		throws IOException, WebApplicationException
	{
		String result = readFromInputStream(entityStream);

    Gson gson = createGsonBuilder()
    		.registerTypeAdapter(Person.class, new PersonSerializer())
		    .create();
    
    Set<I> resultSet = gson.fromJson(result, genericType);
    
    return resultSet;
	}

	@Override
	protected GsonBuilder createGsonBuilder()
	{
		return new GsonBuilder()
    		.registerTypeAdapter(TechnicalUser.class, new TechnicalUserSerializer())
		    .registerTypeAdapter(AuthorizableOrgUnit.class, new AuthorizableOrgUnitSerializer())
		    .registerTypeAdapter(AuthorizableMember.class, new AuthorizableMemberSerializer())
		    .registerTypeAdapter(OpenURRole.class, new OpenURRoleSerializer());
	}	
}
