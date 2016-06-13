package org.openur.remoting.xchange.marshalling.json;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import org.openur.module.domain.security.authorization.AuthorizableTechUser;
import org.openur.module.domain.security.authorization.AuthorizableTechUserBuilder;
import org.openur.module.domain.security.authorization.OpenURPermission;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class TechnicalUserSerializer
	extends UserStructureBaseSerializer
	implements JsonSerializer<AuthorizableTechUser>, JsonDeserializer<AuthorizableTechUser>
{
	@Override
	public AuthorizableTechUser deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		throws JsonParseException
	{		
		JsonObject jsonObject = json.getAsJsonObject();		
		
		AuthorizableTechUserBuilder builder = new AuthorizableTechUserBuilder(getNumber());
		super.deserialize(jsonObject, builder);
		
		JsonArray permissionsArray = jsonObject.get("permissions").getAsJsonArray();
		Set<OpenURPermission> permissions = new HashSet<>();
		for (JsonElement e : permissionsArray)
		{
			permissions.add(context.deserialize(e, OpenURPermission.class));
		}
		
		builder.permissions(permissions, builder);

		return builder.build();
	}

	@Override
	public JsonElement serialize(AuthorizableTechUser src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject jsonObject = new JsonObject();		
		super.serialize(src, jsonObject, context);
		
		Set<OpenURPermission> permissions = new HashSet<>();
		
		src.getAllPermissions().values()
				.stream()
				.forEach(
					p -> p.stream()
					.forEach(permissions::add));		

//		for (Set<? extends IPermission> p : src.getAllPermissions().values())
//		{
//			for (IPermission ip : p)
//			{
//				permissions.add((OpenURPermission) ip);
//			}
//		}

		jsonObject.add("permissions", context.serialize(permissions));

		return jsonObject;
	}
}
