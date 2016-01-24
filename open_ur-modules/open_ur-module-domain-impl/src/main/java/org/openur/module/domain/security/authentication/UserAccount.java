package org.openur.module.domain.security.authentication;

import org.openur.module.domain.IdentifiableEntityBuilder;
import org.openur.module.domain.IdentifiableEntityImpl;

public class UserAccount
	extends IdentifiableEntityImpl
	implements IUserAccount
{
	private static final long serialVersionUID = -7714147589038639771L;

	public UserAccount(IdentifiableEntityBuilder<? extends IdentifiableEntityBuilder<?>> b)
	{
		super(b);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(IUserAccount o)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getUserName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassWord()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSalt()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
