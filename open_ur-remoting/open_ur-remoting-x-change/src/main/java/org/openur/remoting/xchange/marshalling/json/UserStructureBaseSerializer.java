package org.openur.remoting.xchange.marshalling.json;

import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.UserStructureBaseBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;


public class UserStructureBaseSerializer
	extends AbstractJsonSerializer
{
	// properties:
	private String number = null;
	private Status status = null;
	
	// accessors:
	protected String getNumber()
	{
		return number;
	}
	
	protected Status getStatus()
	{
		return status;
	}

	// constructors:
	protected <U extends UserStructureBase> void serialize(U src, JsonObject jsonObject, JsonSerializationContext context)
	{
		super.serialize(src, jsonObject, context);
		
    jsonObject.addProperty("number", src.getNumber());
    jsonObject.addProperty("status", src.getStatus().toString());
	}

	protected <UB extends UserStructureBaseBuilder<UB>> UB deserialize(JsonObject jsonObject, UB builder)
	{
		super.deserialize(jsonObject, builder);

		builder.number(jsonObject.get("number").getAsString());
		builder.status(new Gson().fromJson(jsonObject.get("status").getAsString(), Status.class));
		
		return builder;
	}	
}
