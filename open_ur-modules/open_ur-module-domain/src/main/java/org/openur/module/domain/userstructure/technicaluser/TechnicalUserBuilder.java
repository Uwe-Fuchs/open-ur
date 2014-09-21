package org.openur.module.domain.userstructure.technicaluser;

import org.openur.module.domain.userstructure.UserStructureBaseBuilder;

public class TechnicalUserBuilder
	extends UserStructureBaseBuilder<TechnicalUserBuilder>
{
	// properties:
  
  // constructors:
	public TechnicalUserBuilder()
	{
		super();
	}
	
	public TechnicalUserBuilder(String identifier)
	{
		super(identifier);
	}
	
	public TechnicalUser build()
	{
		return new TechnicalUser(this);
	}
}
