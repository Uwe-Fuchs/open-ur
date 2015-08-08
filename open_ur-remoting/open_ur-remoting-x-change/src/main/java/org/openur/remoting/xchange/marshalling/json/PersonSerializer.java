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
		Set<OpenURApplication> applications = new HashSet<>();
		Name name = null;
	  String phoneNumber = null;
		String faxNumber = null;
		EMailAddress emailAddress = null;
	  String mobileNumber = null;
	  Address homeAddress = null;
	  String homePhoneNumber = null;
	  EMailAddress homeEmailAddress = null;
	  
		JsonObject jsonObject = json.getAsJsonObject();		
		super.deserialize(jsonObject);

		name = context.deserialize(jsonObject.get("name").getAsJsonObject(), Name.class);
		JsonElement element = jsonObject.get("phoneNumber");
		phoneNumber = element != null ? element.getAsString() : null;
		element = jsonObject.get("faxNumber");
		faxNumber = element != null ? element.getAsString() : null;
		element = jsonObject.get("faxNumber");
		faxNumber = element != null ? element.getAsString() : null;
		element = jsonObject.get("mobileNumber");
		mobileNumber = element != null ? element.getAsString() : null;
		element = jsonObject.get("homePhoneNumber");
		homePhoneNumber = element != null ? element.getAsString() : null;
		element = jsonObject.get("homeAddress");
		homeAddress = element != null ? context.deserialize(element.getAsJsonObject(), Address.class) : null;
		element = jsonObject.get("emailAddress");
		emailAddress = element != null ? context.deserialize(element.getAsJsonObject(), EMailAddress.class) : null;
		element = jsonObject.get("homeEmailAddress");
		homeEmailAddress = element != null ? context.deserialize(element.getAsJsonObject(), EMailAddress.class) : null;
		
		JsonArray applicationsArray = jsonObject.get("applications").getAsJsonArray();
		for (JsonElement e : applicationsArray)
		{
			applications.add(context.deserialize(e, OpenURApplication.class));
		}
		
		return new PersonBuilder(getNumber(), name)
				.identifier(getIdentifier())
				.creationDate(getCreationDate())
				.lastModifiedDate(getLastModifiedDate())
				.status(getStatus())
				.phoneNumber(phoneNumber)
				.faxNumber(faxNumber)
				.emailAddress(emailAddress)
				.mobileNumber(mobileNumber)
				.homeAddress(homeAddress)
				.homePhoneNumber(homePhoneNumber)
				.homeEmailAddress(homeEmailAddress)
				.applications(applications)
				.build();
	}	
}
