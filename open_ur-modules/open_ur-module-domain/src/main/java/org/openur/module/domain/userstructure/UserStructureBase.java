package org.openur.module.domain.userstructure;

import org.openur.module.domain.IdentifiableEntityImpl;

public abstract class UserStructureBase
	extends IdentifiableEntityImpl
	implements IUserStructureBase
{
	private static final long serialVersionUID = -5017126374666441590L;
	
	// properties:
	private final String number;
	private final Status status;

	protected UserStructureBase(UserStructureBaseBuilder<? extends UserStructureBaseBuilder<?>> b)
	{
		super(b);
		this.number = b.getNumber();
		this.status = b.getStatus();
	}

	@Override
	public String getNumber()
	{
		return number;
	}

	@Override
	public Status getStatus()
	{
		return status;
	}
}
