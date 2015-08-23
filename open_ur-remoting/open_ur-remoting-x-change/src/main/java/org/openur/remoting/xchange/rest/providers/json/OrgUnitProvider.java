package org.openur.remoting.xchange.rest.providers.json;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

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
		GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(AuthorizableOrgUnit.class, new AuthorizableOrgUnitSerializer());
    gsonBuilder.registerTypeAdapter(AuthorizableMember.class, new AuthorizableMemberSerializer());
    gsonBuilder.registerTypeAdapter(OpenURRole.class, new OpenURRoleSerializer());
    gsonBuilder.registerTypeAdapter(Person.class, new PersonSerializer());
    Gson gson = gsonBuilder.create();
		
		entityStream.write(gson.toJson(t).getBytes());		
	}	
}
