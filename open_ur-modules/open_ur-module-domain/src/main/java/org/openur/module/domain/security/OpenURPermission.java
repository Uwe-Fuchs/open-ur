package org.openur.module.domain.security;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.IdentifiableEntityImpl;

public class OpenURPermission
	extends IdentifiableEntityImpl
	implements IPermission
{
	private static final long serialVersionUID = -7018258281730222661L;
	
	// properties:
	private final IApplication app;
	private final String permissionName;
	private final String description;
	private final PermissionScope permissionScope;

	// constructor:
	public OpenURPermission(OpenURPermissionBuilder b)
	{
		super(b);

		this.app = b.getApp();
		this.permissionName = b.getPermissionName();
		this.description = b.getDescription();
		this.permissionScope = b.getPermissionScope();
	}

	@Override
	public PermissionScope getPermissionScope()
	{
		return permissionScope;
	}

	// accessors:
	public String getPermissionName()
	{
		return permissionName;
	}

	public String getDescription()
	{
		return description;
	}

	@Override
	public String getPermission()
	{
		return getPermissionName();
	}

	@Override
	public IApplication getApp()
	{
		return this.app;
	}

	// operations:
	@Override
	public int compareTo(IPermission o)
	{
		int comparison = new CompareToBuilder()
												.append(this.getPermission(), o.getPermission())
												.append(this.getApp(), o.getApp())
												.toComparison();
		
		return comparison;
	}
}
