package org.openur.remoting.xchange.marshalling.json;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.openur.module.integration.security.shiro.UsernamePwAuthenticationInfo;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UsernamePwAuthenticationInfoSerializer
	implements JsonSerializer<UsernamePwAuthenticationInfo>, JsonDeserializer<UsernamePwAuthenticationInfo>
{
	@SuppressWarnings("unchecked")
	@Override
	public JsonElement serialize(UsernamePwAuthenticationInfo src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("credentials", context.serialize(src.getCredentials()));
		jsonObject.add("identifier", context.serialize(src.getIdentifier()));
		
		ByteSource credentialsSalt = src.getCredentialsSalt();
		
		if (credentialsSalt != null)
		{
			byte[] saltBytes = credentialsSalt.getBytes();
			char[] chars = new char[saltBytes.length];
			for (int i = 0; i < saltBytes.length; i++)
			{
				chars[i] = (char) saltBytes[i];
			}
			
			jsonObject.add("credentialsSalt", context.serialize(new String(chars)));			
		}
		
		PrincipalCollection principalColl = src.getPrincipals();
		
		if (principalColl != null)
		{
			Map<String, Set<Object>> realmPrincipals = new LinkedHashMap<>();
			for (String realmName : principalColl.getRealmNames())
			{
				Set<Object> principals = new LinkedHashSet<>();
				principals.addAll(principalColl.fromRealm(realmName));
				realmPrincipals.put(realmName, principals);
			}
			
			jsonObject.add("principals", context.serialize(realmPrincipals));
		}
		
		return jsonObject;
	}

	@Override
	public UsernamePwAuthenticationInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		throws JsonParseException
	{
		JsonObject jsonObject = json.getAsJsonObject();
		
		JsonElement element = jsonObject.get("identifier");
		String identifier = element != null ? element.getAsString() : null;
		UsernamePwAuthenticationInfo authInfo = new UsernamePwAuthenticationInfo(identifier);
		
		element = jsonObject.get("principals");
		
		if (element != null)
		{
			SimplePrincipalCollection principalColl = new SimplePrincipalCollection();
			Map<String, List<Object>> principals = context.deserialize(element, Map.class);
			
			for (String realmName : principals.keySet())
			{
				principalColl.addAll(principals.get(realmName), realmName);
			}
			
			authInfo.setPrincipals(principalColl);
		}
		
		element = jsonObject.get("credentials");
		
		if (element != null)
		{
			List<?> resultList = context.deserialize(element, List.class);
			char[] credentials = new char[resultList.size()];
			
			for (int i = 0; i < resultList.size(); i++)
			{
				credentials[i] = resultList.get(i).toString().toCharArray()[0];
			}
			
			authInfo.setCredentials(credentials);
		}		
		
		element = jsonObject.get("credentialsSalt");
		String credentialsSalt = element != null ? element.getAsString() : null;
		authInfo.setCredentialsSalt(credentialsSalt != null ? ByteSource.Util.bytes(credentialsSalt) : null);
		
		return authInfo;
	}
}
