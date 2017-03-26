package org.openur.remoting.xchange.marshalling.json;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.orgunit.OrgUnitFull;
import org.openur.module.domain.userstructure.orgunit.OrgUnitFullBuilder;
import org.openur.module.domain.userstructure.orgunit.OrgUnitMember;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class OrgUnitSerializer
	extends UserStructureBaseSerializer
	implements JsonSerializer<OrgUnitFull>, JsonDeserializer<OrgUnitFull>
{	
	@Override
	public OrgUnitFull deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		throws JsonParseException
	{
		JsonObject jsonObject = json.getAsJsonObject();
		OrgUnitFullBuilder orgUnitBuilder = new OrgUnitFullBuilder();		
		super.deserialize(jsonObject, orgUnitBuilder);

		String name = jsonObject.get("name").getAsString();
		JsonElement element = jsonObject.get("shortName");
		String shortName = element != null ? element.getAsString() : null;
		element = jsonObject.get("description");
		String description = element != null ? element.getAsString() : null;
		element = jsonObject.get("address");
		Address address = element != null ? context.deserialize(element.getAsJsonObject(), Address.class) : null;
		element = jsonObject.get("emailAddress");
		EMailAddress emailAddress = element != null ? context.deserialize(element.getAsJsonObject(), EMailAddress.class) : null;
		
		JsonArray applicationsArray = jsonObject.get("members").getAsJsonArray();
		Set<OrgUnitMember> members = new HashSet<>();
		for (JsonElement e : applicationsArray)
		{
			members.add(context.deserialize(e, OrgUnitMember.class));
		}
		
		element = jsonObject.get("rootOrgUnit");
		OrgUnitFull rootOrgUnit = element != null ? context.deserialize(element.getAsJsonObject(), OrgUnitFull.class) : null;
		
		element = jsonObject.get("superOrgUnit");
		OrgUnitFull superOrgUnit = element != null ? context.deserialize(element.getAsJsonObject(), OrgUnitFull.class) : null;
		
		orgUnitBuilder
				.name(name)
				.shortName(shortName)
				.description(description)
				.address(address)
				.emailAddress(emailAddress)
				.members(members);
		
		if (rootOrgUnit != null)
		{
			orgUnitBuilder.rootOrgUnit(rootOrgUnit);
		}
		
		if (superOrgUnit != null)
		{
			orgUnitBuilder.superOrgUnit(superOrgUnit);
		}
		
		return orgUnitBuilder.build();
	}

	@Override
	public JsonElement serialize(OrgUnitFull src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject jsonObject = new JsonObject();		
		super.serialize(src, jsonObject, context);

    jsonObject.addProperty("name", src.getName());
    
    if (StringUtils.isNotEmpty(src.getShortName()))
    {
      jsonObject.addProperty("shortName", src.getShortName());    	
    }
    
    if (StringUtils.isNotEmpty(src.getDescription()))
    {
      jsonObject.addProperty("description", src.getDescription());    	
    }
    
    if (src.getAddress() != null)
    {
    	jsonObject.add("address", context.serialize(src.getAddress()));
    }
    
    if (src.getEmailAddress() != null)
    {
    	jsonObject.add("emailAddress", context.serialize(src.getEmailAddress()));
    }
    
    if (src.getMembers() != null)
    {
    	jsonObject.add("members", context.serialize(src.getMembers()));
    }
    
    if (src.getRootOrgUnit() != null)
    {
    	jsonObject.add("rootOrgUnit", context.serialize(src.getRootOrgUnit()));
    }
    
    if (src.getSuperOrgUnit() != null)
    {
    	jsonObject.add("superOrgUnit", context.serialize(src.getSuperOrgUnit()));
    }
		
		return jsonObject;
	}
}
