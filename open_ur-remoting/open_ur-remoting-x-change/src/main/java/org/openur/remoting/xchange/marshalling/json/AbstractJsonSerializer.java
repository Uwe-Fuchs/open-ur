package org.openur.remoting.xchange.marshalling.json;

import java.time.LocalDateTime;

import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public abstract class AbstractJsonSerializer
{
	private String identifier = null;
	private LocalDateTime creationDate = null;
	private LocalDateTime lastModifiedDate = null;

	protected String getIdentifier()
	{
		return identifier;
	}

	protected LocalDateTime getCreationDate()
	{
		return creationDate;
	}

	protected LocalDateTime getLastModifiedDate()
	{
		return lastModifiedDate;
	}

	protected void serialize(AuthorizableOrgUnit src, JsonObject jsonObject, JsonSerializationContext context)
	{
		jsonObject.addProperty("identifier", src.getIdentifier());
    
    if (src.getCreationDate() != null)
    {
    	JsonElement creationDate = context.serialize(src.getCreationDate());
    	jsonObject.add("creationDate", creationDate);
    }
    
    if (src.getLastModifiedDate() != null)
    {
    	JsonElement lastModifiedDate = context.serialize(src.getLastModifiedDate());
    	jsonObject.add("lastModifiedDate", lastModifiedDate);
    }
	}

	protected void deserialize(JsonObject jsonObject)
	{
		identifier = jsonObject.get("identifier").getAsString();
		JsonElement element = jsonObject.get("creationDate");
		creationDate = element != null ? new Gson().fromJson(element.getAsJsonObject(), LocalDateTime.class) : null;
		element = jsonObject.get("lastModifiedDate");
		lastModifiedDate = element != null ? new Gson().fromJson(element.getAsJsonObject(), LocalDateTime.class) : null;
	}
}
