package org.openur.module.domain.userstructure.technicaluser;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.openur.module.domain.userstructure.UserStructureBase;

public class TechnicalUser
	extends UserStructureBase
	implements ITechnicalUser
{
	private static final long serialVersionUID = 4476135911103120283L;
	
	// properties:
  private final String username;
  private final String password;

	TechnicalUser(TechnicalUserBuilder b)
	{
		super(b);
		this.username = b.getUsername();
		this.password = b.getPassword();
	}

	@Override
	public String getUsername()
	{
		return username;
	}

	@Override
	public String getPassword()
	{
		return password;
	}

	@Override
	public int compareTo(ITechnicalUser other)
	{
		int comparison = new CompareToBuilder()
												.append(this.getNumber(), other.getNumber())
												.append(this.getStatus(), other.getStatus())
												.toComparison();
		
		return comparison;
	}
}
