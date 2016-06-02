package org.openur.module.persistence.rdbms.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;

@Entity(name="USER_ACCOUNT")
@Table(uniqueConstraints={@UniqueConstraint(name="UNQ_USERNAME", columnNames={"USERNAME"}), @UniqueConstraint(name="UNQ_USER_ID", columnNames={"USER_ID"})})
public class PUserAccount
	extends AbstractOpenUrPersistable
{
	private static final long serialVersionUID = -2236145073084159919L;

	// properties:
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinColumn(name="USER_ID", referencedColumnName="ID", nullable=false)
	private PUserStructureBase userStructureBase;
	
	@Column(name="USERNAME", length=50, nullable=false)
  private String userName;

	@Column(name="PASSWORD", length=50, nullable=false)
  private String passWord;
	
	@Column(name="SALT")
	private String salt;

	// accessors:	
	public String getSalt()
	{
		return salt;
	}

	public void setSalt(String salt)
	{
		this.salt = salt;
	}

	public String getUserName()
	{
		return userName;
	}

	public String getPassWord()
	{
		return passWord;
	}

	// constructors:
	public PUserAccount(PUserStructureBase userStructureBase, String userName, String passWord)
	{
		this();

		Validate.notNull(userStructureBase, "ID must not be null!");
		Validate.notBlank(userName, "username must not be empty!");
		Validate.notBlank(passWord, "password must not be empty!");
		
		this.userStructureBase = userStructureBase;
		this.userName = userName;
		this.passWord = passWord;
	}

	private PUserAccount()
	{
		// JPA
		super();
	}
}
