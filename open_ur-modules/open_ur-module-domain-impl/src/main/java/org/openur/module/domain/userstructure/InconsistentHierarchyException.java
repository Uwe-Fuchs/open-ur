package org.openur.module.domain.userstructure;

import org.openur.module.util.exception.OpenURRuntimeException;

public class InconsistentHierarchyException
	extends OpenURRuntimeException
{
	private static final long serialVersionUID = -3799527630197254939L;

	public InconsistentHierarchyException()
	{
		super();
	}

	public InconsistentHierarchyException(String message)
	{
		super(message);
	}

	public InconsistentHierarchyException(Throwable cause)
	{
		super(cause);
	}

	public InconsistentHierarchyException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
