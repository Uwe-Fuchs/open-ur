package org.openur.module.domain.security.authentication;

import org.openur.module.domain.IdentifiableEntityImpl;

public class UserAccount
	extends IdentifiableEntityImpl
	implements IUserAccount
{
	private static final long serialVersionUID = -7714147589038639771L;
	
	// properties:
	private final String userName;
	private final String passWord;
	private final String salt;

	UserAccount(UserAccountBuilder b)
	{
		super(b);

		this.userName = b.getUserName();
		this.passWord = b.getPassWord();
		this.salt = b.getSalt();
	}

	@Override
	public String getUserName()
	{
		return userName;
	}

	@Override
	public String getPassWord()
	{
		return passWord;
	}

	@Override
	public String getSalt()
	{
		return salt;
	}
}
