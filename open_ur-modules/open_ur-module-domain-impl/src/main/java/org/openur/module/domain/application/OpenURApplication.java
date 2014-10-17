package org.openur.module.domain.application;

import org.openur.module.domain.IdentifiableEntityImpl;

public class OpenURApplication
	extends IdentifiableEntityImpl
	implements IApplication
{
	private static final long serialVersionUID = -7587130077162770445L;
	
	// properties:
	private final String applicationName;

	// constructor:
	OpenURApplication(OpenURApplicationBuilder b)
	{
		super(b);

		this.applicationName = b.getApplicationName();
	}

	// accessors:
	@Override
	public String getApplicationName()
	{
		return applicationName;
	}

	// operations:
	
}
