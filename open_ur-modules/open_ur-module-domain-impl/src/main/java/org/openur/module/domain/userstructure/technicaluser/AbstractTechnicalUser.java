package org.openur.module.domain.userstructure.technicaluser;

import org.openur.module.domain.userstructure.UserStructureBase;
import org.openur.module.domain.userstructure.UserStructureBaseBuilder;

public abstract class AbstractTechnicalUser
	extends UserStructureBase
	implements ITechnicalUser
{
	private static final long serialVersionUID = 4476135911103120283L;

	// constructor:
	protected AbstractTechnicalUser(AbstractTechnicalUserBuilder<? extends AbstractTechnicalUserBuilder<?>> b)
	{
		super(b);
	}

	// builder:
	public static abstract class AbstractTechnicalUserBuilder<T extends AbstractTechnicalUserBuilder<T>>
		extends UserStructureBaseBuilder<T>
	{
		// constructors:
		public AbstractTechnicalUserBuilder(String techUserNumber)
		{
			super(techUserNumber);
		}

		// builder-method:
		@Override
		protected AbstractTechnicalUser build()
		{
			super.build();

			return null;
		}
	}
}
