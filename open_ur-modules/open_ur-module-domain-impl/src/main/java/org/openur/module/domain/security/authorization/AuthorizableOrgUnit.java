package org.openur.module.domain.security.authorization;

import java.util.Collection;

import org.openur.module.domain.userstructure.Address;
import org.openur.module.domain.userstructure.EMailAddress;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnit;
import org.openur.module.domain.userstructure.orgunit.OrganizationalUnitBuilder;
import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnit;
import org.openur.module.domain.userstructure.orgunit.abstr.AbstractOrgUnitBuilder;

public class AuthorizableOrgUnit
	extends AbstractOrgUnit
	implements IAuthorizableOrgUnit
{
	private static final long serialVersionUID = 4157616065471955134L;
	
	// org-unit delegate:
	private final OrganizationalUnit delegate;

	// constructor:
	private AuthorizableOrgUnit(AuthorizableOrgUnitBuilder b)
	{
		super(b);
		
		this.delegate = b.getDelegate();
	}

	// accessors:
	public String getName()
	{
		return delegate.getName();
	}

	public String getShortName()
	{
		return delegate.getShortName();
	}

	public String getDescription()
	{
		return delegate.getDescription();
	}
	
	public Address getAddress()
	{
		return delegate.getAddress();
	}

	public EMailAddress getEmailAddress()
	{
		return delegate.getEmailAddress();
	}
	
	@Override
	public AuthorizableOrgUnit getRootOrgUnit()
	{
		return (AuthorizableOrgUnit) super.rootOrgUnit;
	}

	@Override
	public AuthorizableOrgUnit getSuperOrgUnit()
	{
		return (AuthorizableOrgUnit) super.superOrgUnit;
	}
	
	public static class AuthorizableOrgUnitBuilder
		extends AbstractOrgUnitBuilder<AuthorizableOrgUnitBuilder>
	{
		// delegates:
		private OrganizationalUnitBuilder builderDelegate = new OrganizationalUnitBuilder();
		private OrganizationalUnit delegate = null;
		
		// constructors:
		public AuthorizableOrgUnitBuilder()
		{
			super();
		}

		public AuthorizableOrgUnitBuilder(String identifier)
		{
			super(identifier);
		}

		public AuthorizableOrgUnitBuilder(OrganizationalUnit rootOrgUnit)
		{
			super(rootOrgUnit);
		}

		public AuthorizableOrgUnitBuilder(String identifier, 	OrganizationalUnit rootOrgUnit)
		{
			super(identifier, rootOrgUnit);
		}

		// builder-methods:
		public AuthorizableOrgUnitBuilder authorizableMembers(Collection<AuthorizableMember> authMembers)
		{
			super.members(authMembers);
			return this;
		}

		public AuthorizableOrgUnitBuilder name(String name)
		{
			builderDelegate.name(name);
			return this;
		}

		public AuthorizableOrgUnitBuilder shortName(String shortName)
		{
			builderDelegate.shortName(shortName);
			return this;
		}

		public AuthorizableOrgUnitBuilder description(String description)
		{
			builderDelegate.description(description);
			return this;
		}

		public AuthorizableOrgUnitBuilder address(Address address)
		{
			builderDelegate.address(address);
			return this;
		}

		public AuthorizableOrgUnitBuilder emailAdress(EMailAddress emailAdress)
		{
			builderDelegate.emailAdress(emailAdress);
			return this;
		}

		public AuthorizableOrgUnitBuilder superOrgUnit(AuthorizableOrgUnit superOrgUnit)
		{
			super.superOrgUnit(superOrgUnit);
			return this;
		}
		
		// accessors:
		OrganizationalUnit getDelegate()
		{
			return delegate;
		}
		
		// builder:
		@Override
		public AuthorizableOrgUnit build()
		{
			super.build();			
			this.delegate = builderDelegate.build();
			
			return new AuthorizableOrgUnit(this);
		}
	}
}
