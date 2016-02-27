package org.openur.remoting.xchange.marshalling.json;

import java.time.LocalDateTime;

import org.openur.module.domain.IdentifiableEntityBuilder;
import org.openur.module.domain.IdentifiableEntityImpl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public abstract class AbstractJsonSerializer
{
	protected <I extends IdentifiableEntityImpl> void serialize(I src, JsonObject jsonObject, JsonSerializationContext context)
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

	protected <IB extends IdentifiableEntityBuilder<IB>> IB deserialize(JsonObject jsonObject, IB builder)
	{
		builder.identifier(jsonObject.get("identifier").getAsString());
		JsonElement element = jsonObject.get("creationDate");
		builder.creationDate(element != null ? new Gson().fromJson(element.getAsJsonObject(), LocalDateTime.class) : null);
		element = jsonObject.get("lastModifiedDate");
		builder.lastModifiedDate(element != null ? new Gson().fromJson(element.getAsJsonObject(), LocalDateTime.class) : null);
		
		return builder;
	}
}
