package org.openur.remoting.xchange.marshalling.json;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.openur.module.domain.security.authorization.AuthorizableMember;
import org.openur.module.domain.security.authorization.OpenURRole;
import org.openur.module.domain.userstructure.person.Person;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AuthorizableMemberSerializer
	implements JsonDeserializer<AuthorizableMember>, JsonSerializer<AuthorizableMember>
{
	private String identifier = null;
	private LocalDateTime lastModifiedDate = null;
	private LocalDateTime creationDate = null;
	private Person person = null;
	private String orgUnitId = null;
	private Set<OpenURRole> roles = new HashSet<>();
	
	@Override
	public JsonElement serialize(AuthorizableMember src, Type typeOfSrc, JsonSerializationContext context)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthorizableMember deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		throws JsonParseException
	{
		// TODO Auto-generated method stub
		return null;
	}	
}
