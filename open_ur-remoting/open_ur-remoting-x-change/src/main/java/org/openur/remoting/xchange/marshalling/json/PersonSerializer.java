package org.openur.remoting.xchange.marshalling.json;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import org.openur.module.domain.application.OpenURApplication;
import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.person.Name;
import org.openur.module.domain.userstructure.person.Person;
import org.openur.module.domain.userstructure.person.PersonBuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class PersonSerializer
	extends UserStructureBaseSerializer
	implements JsonDeserializer<Person>
{  
	@Override
	public Person deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		throws JsonParseException
	{	  
		JsonObject jsonObject = json.getAsJsonObject();
		PersonBuilder builder = new PersonBuilder();		
		super.deserialize(jsonObject, builder);

		Name name = context.deserialize(jsonObject.get("name").getAsJsonObject(), Name.class);
		JsonElement element = jsonObject.get("phoneNumber");
		String phoneNumber = element != null ? element.getAsString() : null;
		element = jsonObject.get("faxNumber");
		String faxNumber = element != null ? element.getAsString() : null;
		element = jsonObject.get("mobileNumber");
		String mobileNumber = element != null ? element.getAsString() : null;
		element = jsonObject.get("homePhoneNumber");
		String homePhoneNumber = element != null ? element.getAsString() : null;
		element = jsonObject.get("homeAddress");
		Address homeAddress = element != null ? context.deserialize(element.getAsJsonObject(), Address.class) : null;
		element = jsonObject.get("emailAddress");
		EMailAddress emailAddress = element != null ? context.deserialize(element.getAsJsonObject(), EMailAddress.class) : null;
		element = jsonObject.get("homeEmailAddress");
		EMailAddress homeEmailAddress = element != null ? context.deserialize(element.getAsJsonObject(), EMailAddress.class) : null;
		
		JsonArray applicationsArray = jsonObject.get("applications").getAsJsonArray();
		Set<OpenURApplication> applications = new HashSet<>();
		for (JsonElement e : applicationsArray)
		{
			applications.add(context.deserialize(e, OpenURApplication.class));
		}
		
		builder.name(name)
				.phoneNumber(phoneNumber)
				.faxNumber(faxNumber)
				.mobileNumber(mobileNumber)
				.homePhoneNumber(homePhoneNumber)
				.homeAddress(homeAddress)
				.emailAddress(emailAddress)
				.homeEmailAddress(homeEmailAddress)
				.applications(applications);
		
		return builder.build();
	}	
}
