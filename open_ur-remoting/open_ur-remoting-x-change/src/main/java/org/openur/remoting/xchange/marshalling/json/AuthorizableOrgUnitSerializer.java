package org.openur.remoting.xchange.marshalling.json;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.AuthorizableOrgUnit;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.Status;
import org.openur.module.domain.userstructure.orgunit.AbstractOrgUnit;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class AuthorizableOrgUnitSerializer
	implements JsonDeserializer<AuthorizableOrgUnit>
{
	@Override
	public AuthorizableOrgUnit deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		throws JsonParseException
	{
		JsonObject jsonObject = json.getAsJsonObject();

		String identifier = jsonObject.get("identifier").getAsString();
		String name = jsonObject.get("name").getAsString();
		String number = jsonObject.get("number").getAsString();
		Status status = new Gson().fromJson(jsonObject.get("status").getAsString(), Status.class);
		
		JsonElement element = jsonObject.get("shortName");
		String shortName = element != null ? element.getAsString() : null;
		element = jsonObject.get("description");
		String description = element != null ? element.getAsString() : null;
		element = jsonObject.get("address");
		Address address = element != null ? new Gson().fromJson(element.getAsJsonObject(), Address.class) : null;
		element = jsonObject.get("emailAddress");
		EMailAddress emailAddress = element != null ? new Gson().fromJson(element.getAsJsonObject(), EMailAddress.class) : null;
		element = jsonObject.get("creationDate");
		LocalDateTime creationDate = element != null ? new Gson().fromJson(element.getAsJsonObject(), LocalDateTime.class) : null;
		element = jsonObject.get("lastModifiedDate");
		LocalDateTime lastModifiedDate = element != null ? new Gson().fromJson(element.getAsJsonObject(), LocalDateTime.class) : null;

		JsonArray jsonMembersArray = jsonObject.get("members").getAsJsonArray();
		Set<AuthorizableMember> members = new HashSet<>(jsonMembersArray.size());
		for (int i = 0; i < jsonMembersArray.size(); i++)
		{
			element = jsonMembersArray.get(i);
			//AuthorizableMember m = new Gson().fromJson(element.getAsJsonObject(), AuthorizableMember.class);
			AuthorizableMember m = context.deserialize(element, AuthorizableMember.class);
			members.add(m);
		}
		
		AbstractOrgUnit rootOrgUnit;
		AbstractOrgUnit superOrgUnit;
		
		return null;
	}
}
