package org.openur.module.domain;

import java.util.Date;

public abstract class IdentifiableEntityImpl
	extends GraphNode
	implements IIdentifiableEntity
{
	private static final long serialVersionUID = 1L;

	private final String identifier;
	private final Date changeDate;
	private final Date creationDate;

	protected IdentifiableEntityImpl(IdentifiableEntityBuilder<? extends IdentifiableEntityBuilder<?>> b)
	{
		super();

		this.identifier = b.getIdentifier();
		this.creationDate = b.getCreationDate();
		this.changeDate = b.getChangeDate();
	}

	@Override
	public String getIdentifier()
	{
		return identifier;
	}

	@Override
	public Date getChangeDate()
	{
		return changeDate;
	}

	@Override
	public Date getCreationDate()
	{
		return creationDate;
	}

	// operations:
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !this.getClass().equals(obj.getClass()))
		{
			return false;
		}
		
		if (this == obj)
		{
			return true;
		}

		IdentifiableEntityImpl ie = (IdentifiableEntityImpl) obj;
		
		return this.getIdentifier().equals(ie.getIdentifier());
	}
}
