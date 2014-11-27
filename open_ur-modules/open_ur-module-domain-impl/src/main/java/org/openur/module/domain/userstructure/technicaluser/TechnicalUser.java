package org.openur.module.domain.userstructure.technicaluser;

import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.UserStructureBaseBuilder;

public class TechnicalUser
	extends UserStructureBase
	implements ITechnicalUser
{
	private static final long serialVersionUID = 4476135911103120283L;

	// constructor:
	TechnicalUser(TechnicalUserBuilder b)
	{
		super(b);
	}
	
	// builder:
	public static class TechnicalUserBuilder
		extends UserStructureBaseBuilder<TechnicalUserBuilder>
	{
		// properties:
	  
	  // constructors:
		public TechnicalUserBuilder(String techUserNumber)
		{
			super(techUserNumber);
		}
		
		public TechnicalUser build()
		{
			return new TechnicalUser(this);
		}
	}
}
