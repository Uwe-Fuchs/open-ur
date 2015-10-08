package org.openur.remoting.xchange.rest.providers.json;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.remoting.xchange.marshalling.json.AuthorizableMemberSerializer;
import org.openur.remoting.xchange.marshalling.json.AuthorizableOrgUnitSerializer;
import org.openur.remoting.xchange.marshalling.json.OpenURRoleSerializer;
import org.openur.remoting.xchange.marshalling.json.PersonSerializer;

import com.google.gson.GsonBuilder;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class OrgUnitProvider
	extends AbstractProvider<AuthorizableOrgUnit>
{
	@Override
	protected boolean isProvided(Class<?> type)
	{
		return AuthorizableOrgUnit.class.isAssignableFrom(type);
	}
	
	protected GsonBuilder createGsonBuilder()
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(AuthorizableOrgUnit.class, new AuthorizableOrgUnitSerializer());
    gsonBuilder.registerTypeAdapter(AuthorizableMember.class, new AuthorizableMemberSerializer());
    gsonBuilder.registerTypeAdapter(Person.class, new PersonSerializer());
    gsonBuilder.registerTypeAdapter(OpenURRole.class, new OpenURRoleSerializer());
    
    return gsonBuilder;
	}
}
