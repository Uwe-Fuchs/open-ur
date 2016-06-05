package org.openur.remoting.xchange.rest.providers.json;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.openur.module.domain.userstructure.technicaluser.TechnicalUser;
import org.openur.remoting.xchange.marshalling.json.TechnicalUserSerializer;

import com.google.gson.GsonBuilder;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class TechnicalUserProvider
	extends AbstractProvider<TechnicalUser>
{
	@Override
	protected boolean isProvided(Class<?> type)
	{
		return TechnicalUser.class.isAssignableFrom(type);
	}

	@Override
	protected GsonBuilder createGsonBuilder()
	{
		return new GsonBuilder()
    		.registerTypeAdapter(TechnicalUser.class, new TechnicalUserSerializer());
	}
}
