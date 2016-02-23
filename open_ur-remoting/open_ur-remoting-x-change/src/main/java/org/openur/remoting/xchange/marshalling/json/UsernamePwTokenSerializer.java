package org.openur.remoting.xchange.marshalling.json;

import java.lang.reflect.Type;

import org.openur.module.domain.security.authentication.UsernamePasswordToken;
import org.openur.module.domain.security.authentication.UsernamePasswordTokenBuilder;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UsernamePwTokenSerializer
	implements JsonDeserializer<UsernamePasswordToken>, JsonSerializer<UsernamePasswordToken>	
{
	@Override
	public JsonElement serialize(UsernamePasswordToken src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("delegate", context.serialize(src.getDelegate()));
		
		return jsonObject;
	}

	@Override
	public UsernamePasswordToken deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		throws JsonParseException
	{
		JsonObject jsonObject = json.getAsJsonObject();
		
		JsonElement element = jsonObject.get("delegate");		
		org.apache.shiro.authc.UsernamePasswordToken shiroToken = 
				context.deserialize(element.getAsJsonObject(), org.apache.shiro.authc.UsernamePasswordToken.class);
		
		return new UsernamePasswordTokenBuilder(shiroToken).build();
	}
}
