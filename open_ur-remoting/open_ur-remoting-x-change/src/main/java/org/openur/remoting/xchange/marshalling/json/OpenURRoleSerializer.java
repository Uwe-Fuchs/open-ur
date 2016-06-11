package org.openur.remoting.xchange.marshalling.json;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openur.module.domain.security.authorization.IPermission;
import org.openur.module.domain.security.authorization.OpenURPermission;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.security.authorization.OpenURRoleBuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class OpenURRoleSerializer
	extends AbstractIdentifiableEntitySerializer
	implements JsonDeserializer<OpenURRole>, JsonSerializer<OpenURRole>
{
	@Override
	public JsonElement serialize(OpenURRole src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject jsonObject = new JsonObject();
		super.serialize(src, jsonObject, context);

		jsonObject.addProperty("roleName", src.getRoleName());

		if (StringUtils.isNotEmpty(src.getDescription()))
		{
			jsonObject.addProperty("description", src.getDescription());
		}

		Set<OpenURPermission> permissions = new HashSet<>();

		for (Set<? extends IPermission> p : src.getAllPermissions().values())
		{
			for (IPermission ip : p)
			{
				permissions.add((OpenURPermission) ip);
			}
		}

		jsonObject.add("permissions", context.serialize(permissions));

		return jsonObject;
	}

	@Override
	public OpenURRole deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		throws JsonParseException
	{
		JsonObject jsonObject = json.getAsJsonObject();		

		OpenURRoleBuilder builder = new OpenURRoleBuilder(jsonObject.get("roleName").getAsString());
		super.deserialize(jsonObject, builder);
		
		JsonElement element = jsonObject.get("description");
		builder.description(element != null ? element.getAsString() : null);
		
		JsonArray permissionsArray = jsonObject.get("permissions").getAsJsonArray();
		Set<OpenURPermission> permissions = new HashSet<>();
		for (JsonElement e : permissionsArray)
		{
			permissions.add(context.deserialize(e, OpenURPermission.class));
		}
		
		builder.permissions(permissions, builder);

		return builder.build();
	}
}
