package org.openur.remoting.xchange.rest.providers.json;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.userstructure.orgunit.OrgUnitFull;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.remoting.xchange.marshalling.json.OrgUnitMemberSerializer;
import org.openur.remoting.xchange.marshalling.json.OrgUnitSerializer;
import org.openur.remoting.xchange.marshalling.json.OpenURRoleSerializer;
import org.openur.remoting.xchange.marshalling.json.PersonSerializer;

import com.google.gson.GsonBuilder;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class OrgUnitProvider
	extends AbstractProvider<OrgUnitFull>
{
	@Override
	protected boolean isProvided(Class<?> type)
	{
		return OrgUnitFull.class.isAssignableFrom(type);
	}
	
	protected GsonBuilder createGsonBuilder()
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(OrgUnitFull.class, new OrgUnitSerializer());
    gsonBuilder.registerTypeAdapter(OrgUnitMember.class, new OrgUnitMemberSerializer());
    gsonBuilder.registerTypeAdapter(Person.class, new PersonSerializer());
    gsonBuilder.registerTypeAdapter(OpenURRole.class, new OpenURRoleSerializer());
    
    return gsonBuilder;
	}
}
