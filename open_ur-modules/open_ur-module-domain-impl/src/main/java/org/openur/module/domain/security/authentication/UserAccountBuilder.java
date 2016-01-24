package org.openur.module.domain.security.authentication;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.IdentifiableEntityBuilder;

public class UserAccountBuilder
	extends IdentifiableEntityBuilder<UserAccountBuilder>
{
	// properties:
	private String userName;
	private String passWord;
	private String salt;
	
	// constructor:
	public UserAccountBuilder(String userName, String passWord)
	{
		super();

		Validate.notBlank(userName, "username must not be empty!");
		Validate.notBlank(passWord, "password must not be empty!");

		this.userName = userName;
		this.passWord = passWord;		
	}

	// accessors:
	String getUserName()
	{
		return userName;
	}

	String getPassWord()
	{
		return passWord;
	}

	String getSalt()
	{
		return salt;
	}
	
	// builder-methods:
	public UserAccountBuilder salt(String salt)
	{
		Validate.notBlank(salt, "salt must not be empty!");
		this.salt = salt;
		
		return this;
	}
}
