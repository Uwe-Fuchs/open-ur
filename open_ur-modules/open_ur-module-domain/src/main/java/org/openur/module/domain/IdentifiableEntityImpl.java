package org.openur.module.domain;

import java.time.LocalDateTime;

public abstract class IdentifiableEntityImpl
	extends GraphNode
	implements IIdentifiableEntity
{
	private static final long serialVersionUID = 1L;

	private final String identifier;
	private final LocalDateTime lastModifiedDate;
	private final LocalDateTime creationDate;

	protected IdentifiableEntityImpl(IdentifiableEntityBuilder<? extends IdentifiableEntityBuilder<?>> b)
	{
		super();

		this.identifier = b.getIdentifier();
		this.creationDate = b.getCreationDate();
		this.lastModifiedDate = b.getLastModifiedDate();
	}

	@Override
	public String getIdentifier()
	{
		return identifier;
	}

	@Override
	public LocalDateTime getLastModifiedDate()
	{
		return lastModifiedDate;
	}

	@Override
	public LocalDateTime getCreationDate()
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
