package org.openur.module.domain.userstructure.technicaluser;

import org.apache.commons.lang3.Validate;
import org.openur.module.domain.userstructure.UserStructureBaseBuilder;

public class TechnicalUserBuilder
	extends UserStructureBaseBuilder<TechnicalUserBuilder>
{
	// properties:
  private String username = null;
  private String password = null;
  
  // constructors:
	public TechnicalUserBuilder(String username, String password)
	{
		super();

		init(username, password);
	}
	
	public TechnicalUserBuilder(String identifier, String username, String password)
	{
		super(identifier);

		init(username, password);
	}
	
	private void init(String username, String password)
	{
		Validate.notEmpty(username, "username must not be empty!");
		Validate.notEmpty(password, "password must not be empty!");
		this.username = username;
		this.password = password;
	}

	// accessors:
	String getUsername()
	{
		return username;
	}

	String getPassword()
	{
		return password;
	}
	
	public TechnicalUser build()
	{
		return new TechnicalUser(this);
	}
}
