package org.openur.module.persistence.rdbms.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;

@Entity(name="USER_ACCOUNT")
@Table(uniqueConstraints={@UniqueConstraint(name="UNQ_USERNAME", columnNames={"USERNAME"})})
public class PUserAccount
	implements Serializable
{
	private static final long serialVersionUID = -2236145073084159919L;

	// properties:
	@Id
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinColumn(name="ID", referencedColumnName="ID")
	private PUserStructureBase userStructureBase;
	
	@Column(name="USERNAME", length=50, nullable=false)
  private String userName;

	@Column(name="PASSWORD", length=50, nullable=false)
  private String passWord;

	@Column(name="CREATED", nullable=false)
	private LocalDateTime creationDate;

	@Column(name="MODIFIED")
	private LocalDateTime lastModifiedDate;
	
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

	public LocalDateTime getCreationDate()
	{
		return creationDate;
	}

	public LocalDateTime getLastModifiedDate()
	{
		return lastModifiedDate;
	}

	@PrePersist
	void setCreationDate()
	{
		this.creationDate = LocalDateTime.now();
	}

	@PreUpdate
	void setLastModifiedDate()
	{
		this.lastModifiedDate = LocalDateTime.now();
	}

	// constructors:
	public PUserAccount(PUserStructureBase userStructureBase, String userName, String passWord)
	{
		this();

		Validate.notNull(userStructureBase, "ID must not be null!");
		Validate.notBlank(userName, "user_name must not be empty!");
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
