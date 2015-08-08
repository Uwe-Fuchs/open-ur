package org.openur.remoting.xchange.marshalling.json;

import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.userstructure.Status;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;


public class UserStructureBaseSerializer
	extends AbstractJsonSerializer
{
	private String number = null;
	private Status status = null;
	
	protected String getNumber()
	{
		return number;
	}
	
	protected Status getStatus()
	{
		return status;
	}
	
	@Override
	protected void serialize(AuthorizableOrgUnit src, JsonObject jsonObject, JsonSerializationContext context)
	{
		super.serialize(src, jsonObject, context);
		
    jsonObject.addProperty("number", src.getNumber());
    jsonObject.addProperty("status", src.getStatus().toString());
	}
	
	@Override
	protected void deserialize(JsonObject jsonObject)
	{
		super.deserialize(jsonObject);

		number = jsonObject.get("number").getAsString();
		status = new Gson().fromJson(jsonObject.get("status").getAsString(), Status.class);
	}	
}
