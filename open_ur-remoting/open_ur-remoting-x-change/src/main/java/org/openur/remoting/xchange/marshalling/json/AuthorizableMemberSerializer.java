package org.openur.remoting.xchange.marshalling.json;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.AuthorizableMember.AuthorizableMemberBuilder;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.userstructure.person.Person;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AuthorizableMemberSerializer
	extends AbstractJsonSerializer
	implements JsonDeserializer<AuthorizableMember>, JsonSerializer<AuthorizableMember>
{	
	@Override
	public JsonElement serialize(AuthorizableMember src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject jsonObject = new JsonObject();		
		super.serialize(src, jsonObject, context);

    jsonObject.addProperty("orgUnitId", src.getOrgUnitId());
  	jsonObject.add("person", context.serialize(src.getPerson()));
    
    if (src.getRoles() != null)
    {
    	jsonObject.add("roles", context.serialize(src.getRoles()));
    }
		
		return jsonObject;
	}

	@Override
	public AuthorizableMember deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		throws JsonParseException
	{
		JsonObject jsonObject = json.getAsJsonObject();
		
		String orgUnitId = jsonObject.get("orgUnitId").getAsString();		
		JsonElement element = jsonObject.get("person");
		Person person = context.deserialize(element.getAsJsonObject(), Person.class);
		
		AuthorizableMemberBuilder builder = new AuthorizableMemberBuilder(person, orgUnitId);
		super.deserialize(jsonObject, builder);
		
		JsonArray rolesArray = jsonObject.get("roles").getAsJsonArray();
		Set<OpenURRole> roles = new HashSet<>();
		for (JsonElement e : rolesArray)
		{
			roles.add(context.deserialize(e, OpenURRole.class));
		}
		
		builder.roles(roles);
		
		return builder.build();
	}	
}
