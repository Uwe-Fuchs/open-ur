package org.openur.remoting.xchange.rest.providers.json;

import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.remoting.xchange.marshalling.json.OpenURRoleSerializer;

import com.google.gson.GsonBuilder;

public class RoleProvider
	extends AbstractProvider<OpenURRole>
{
	@Override
	protected boolean isProvided(Class<?> type)
	{
		return OpenURRole.class.isAssignableFrom(type);
	}

	@Override
	protected GsonBuilder createGsonBuilder()
	{
		return new GsonBuilder()
		    .registerTypeAdapter(OpenURRole.class, new OpenURRoleSerializer());
	}
}
